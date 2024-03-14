package site.chachacha.fitme.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import site.chachacha.fitme.auth.service.JwtService;
import site.chachacha.fitme.member.dto.MemberResponse;
import site.chachacha.fitme.member.entity.Member;
import site.chachacha.fitme.member.exception.NoSuchMemberException;
import site.chachacha.fitme.member.repository.MemberRepository;

import static jakarta.servlet.http.HttpServletResponse.SC_GONE;
import static site.chachacha.fitme.auth.service.JwtService.ACCESS_TOKEN_HEADER;
import static site.chachacha.fitme.auth.service.JwtService.BEARER;
import static site.chachacha.fitme.auth.service.JwtService.REFRESH_TOKEN_HEADER;

@Component
@RequiredArgsConstructor
public class MemberLogInSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    private final MemberRepository memberRepository;

    private final ObjectMapper objectMapper;

    /**
     * 로그인 성공 시, JwtEntity를 생성하고 AccessToken과 RefreshToken을 Cookie에 담아 보낸다.
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     * the authentication process.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        PrincipalUserDetails principal = (PrincipalUserDetails) authentication.getPrincipal();
//
//        String email = principal.getMember().getEmail();
        Long memberId = (Long) request.getAttribute("memberId");

        try {
            Member member = memberRepository.findById(memberId).orElseThrow(
                NoSuchMemberException::new);

            // access token, refresh token을 헤더에 실어서 보낸다.
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setHeader(ACCESS_TOKEN_HEADER, BEARER + request.getAttribute("accessToken"));
            response.setHeader(REFRESH_TOKEN_HEADER, BEARER + request.getAttribute("refreshToken"));

            response.getWriter().write(objectMapper.writeValueAsString(MemberResponse.from(member)));
        }
        // 회원을 찾을 수 없으면
        catch (NoSuchMemberException e) {
            response.setStatus(SC_GONE);
        }
    }
}
