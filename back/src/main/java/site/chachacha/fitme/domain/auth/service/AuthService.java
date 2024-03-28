package site.chachacha.fitme.domain.auth.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.advice.exception.BadRequestException;
import site.chachacha.fitme.domain.auth.dto.KakaoOAuthResponse;
import site.chachacha.fitme.domain.auth.entity.Token;
import site.chachacha.fitme.domain.auth.exception.InvalidRefreshTokenException;
import site.chachacha.fitme.domain.auth.exception.NoSuchRefreshTokenException;
import site.chachacha.fitme.domain.auth.repository.TokenRepository;
import site.chachacha.fitme.domain.member.dto.MemberResponse;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.exception.NoSuchMemberException;
import site.chachacha.fitme.domain.member.repository.MemberRepository;

@Service
@Slf4j
// Transactional 붙이지 마
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final OAuthService oauthService;

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    @Transactional
    public MemberResponse signIn(KakaoOAuthResponse kakaoOAuthResponse,
        HttpServletResponse response) throws NoSuchMemberException {
        // Member가 이미 있는지 확인한다.
        try {
            // providerId로 Member를 Token과 함께 찾는다.
            Member member = memberRepository.findNotDeletedByProviderIdWithToken(
                    kakaoOAuthResponse.getProviderId())
                .orElseThrow(NoSuchMemberException::new);

            String[] jwts = jwtService.issueJwts(member.getId());

            // ToDo: token 있으면 업데이트, 없으면 생성하는거로 바꾸기
            // Token이 이미 있으면
            if (member.getToken() != null) {
                Token token = member.getToken();
                token.updateResourceTokens(kakaoOAuthResponse.getResourceAccessToken(),
                    kakaoOAuthResponse.getResourceRefreshToken());

                tokenRepository.save(token);
            }
            // Token이 없으면
            else {
                Token token = Token.builder()
                    .resourceAccessToken(kakaoOAuthResponse.getResourceAccessToken())
                    .resourceRefreshToken(kakaoOAuthResponse.getResourceRefreshToken())
                    .member(member)
                    .build();

                tokenRepository.save(token);
            }

            setHeader(response, jwts);

            // Redis에 RefreshToken 저장
            jwtService.saveRefreshTokenToRedis(jwts[1], member.getId());

            // member를 리턴한다.
            return MemberResponse.from(member, false);
        }
        // Member가 없다는건, 처음 로그인하는 회원이라는 뜻이다.
        catch (NoSuchMemberException e) {
            // Member를 생성한다.
            Member member = Member.builder()
                .providerId(kakaoOAuthResponse.getProviderId())
                .build();

            // Member를 저장한다.
            Member newMember = memberRepository.save(member);

            String[] jwts = jwtService.issueJwts(newMember.getId());

            Token token = Token.builder()
                .resourceAccessToken(kakaoOAuthResponse.getResourceAccessToken())
                .resourceRefreshToken(kakaoOAuthResponse.getResourceRefreshToken())
                .member(newMember)
                .build();

            // Token을 저장한다.
            tokenRepository.save(token);

            setHeader(response, jwts);

            // Redis에 RefreshToken 저장
            jwtService.saveRefreshTokenToRedis(jwts[1], newMember.getId());

            // member를 리턴한다.
            return MemberResponse.from(newMember, true);
        }
    }

    // Transactional 필요 없음
    public String[] reissueJwts(HttpServletRequest request)
        throws NoSuchMemberException, IllegalArgumentException, NoSuchRefreshTokenException, JWTVerificationException {
//        , NoSuchDeviceTokenException
//        // Header에서 deviceToken 추출
//        String deviceToken = deviceTokenService.extractDeviceTokenFromHeader(request);

        // ToDo: 테스트 할 때만 끔 // 추출한 deviceToken이 유효한지 확인한다.
//        deviceTokenService.validateAndExtractDeviceToken(deviceToken);

        // Header에서 refreshToken 추출
        String refreshToken = jwtService.extractRefreshToken(request);
        Long memberIdFromRefreshToken = jwtService.validateAndExtractMemberIdFromRefreshToken(
            refreshToken);

        // token들을 재발급한다.
        String[] jwts = jwtService.reissueJwts(memberIdFromRefreshToken, refreshToken);
//        , deviceToken);

        // Redis에 RefreshToken 저장
        jwtService.saveRefreshTokenToRedis(jwts[1], memberIdFromRefreshToken);

        return jwts;
    }

    @Transactional
    public void signOut(HttpServletRequest request, HttpServletResponse response)
        throws BadRequestException, InvalidRefreshTokenException, NoSuchMemberException, IOException {
        // Header에서 Refresh Token 추출
        String refreshToken = jwtService.extractRefreshToken(request);

        Long memberId = jwtService.validateAndExtractMemberIdFromRefreshToken(refreshToken);

        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(NoSuchMemberException::new);

        Token token = member.getToken();

        // Token Entity가 있으면
        if (token != null) {
            try {
                // 로그아웃 요청 보내고
                oauthService.requestLogOut(token);
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            // Token을 지운다.
            member.setToken(null);
        }

        // RefreshToken을 Redis에서 삭제
        jwtService.deleteRefreshTokenFromRedis(refreshToken);

        // Header에서 Access Token 삭제
        response.setHeader(JwtService.ACCESS_TOKEN_HEADER, null);
        // Header에서 Refresh Token 삭제
        response.setHeader(JwtService.REFRESH_TOKEN_HEADER, null);
    }

    private void setHeader(HttpServletResponse response, String[] jwts) {
        response.setHeader(JwtService.ACCESS_TOKEN_HEADER, JwtService.BEARER + jwts[0]);
        response.setHeader(JwtService.REFRESH_TOKEN_HEADER, JwtService.BEARER + jwts[1]);
    }
}
