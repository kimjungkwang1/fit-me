package site.chachacha.fitme.domain.auth.controller;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static site.chachacha.fitme.domain.auth.service.JwtService.ACCESS_TOKEN_HEADER;
import static site.chachacha.fitme.domain.auth.service.JwtService.BEARER;
import static site.chachacha.fitme.domain.auth.service.JwtService.REFRESH_TOKEN_HEADER;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.domain.auth.dto.KakaoOAuthResponse;
import site.chachacha.fitme.domain.auth.exception.InvalidRefreshTokenException;
import site.chachacha.fitme.domain.auth.exception.NoSuchRefreshTokenException;
import site.chachacha.fitme.domain.auth.service.AuthService;
import site.chachacha.fitme.domain.auth.service.OAuthService;
import site.chachacha.fitme.domain.member.dto.MemberResponse;
import site.chachacha.fitme.domain.member.exception.NoSuchMemberException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
@RestController
public class AuthRestController {

    private final AuthService authService;
    private final OAuthService oauthService;

    @Value("${KAKAO.CLIENT.ID}")
    private String kakaoClientId;

    @Value("${FRONTEND}")
    private String frontend;

    @Value("${FRONTEND.PORT}")
    private String frontendPort;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping(value = "/login/oauth2/authorization/{provider}")
    public void oAuth2AuthorizationV1(@PathVariable(name = "provider") String provider,
        HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = getHttpAndDomain(request);
        log.info("requestUrl: " + requestUrl);

        response.sendRedirect(
            "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId
                + "&redirect_uri=" + requestUrl + "/auth/login/oauth2/code/" + provider);
    }

    @GetMapping(value = "/login/oauth2/code/{provider}")
    public ResponseEntity<?> oAuth2CodeV1(@PathVariable(name = "provider") String provider,
        @RequestParam(name = "code") String code, HttpServletRequest request,
        HttpServletResponse response) throws IOException, NoSuchMemberException {
        log.info("code: " + code);

        // 요청한 url을 가져온다.
        String requestUrl = getHttpAndDomain(request);
        log.info("requestUrl: " + requestUrl);

        // Kakao에 Access Token을 요청한다.
        KakaoOAuthResponse kakaoOAuthResponse = oauthService.requestKakao(provider, code,
            requestUrl);

        // DB에 사용자 정보, Access Token, Refresh Token 저장
        MemberResponse memberResponse = authService.signIn(kakaoOAuthResponse, response);

        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping(value = "/reissue/v1")
    public ResponseEntity<?> reissueJwtsV1(HttpServletRequest request, HttpServletResponse response)
        throws IllegalArgumentException, JWTVerificationException, IOException {
//        , NoSuchDeviceTokenException
        String[] jwts = authService.reissueJwts(request);

        // access token, refresh token을 헤더에 실어서 보낸다.
        response.setHeader(ACCESS_TOKEN_HEADER, BEARER + jwts[0]);
        response.setHeader(REFRESH_TOKEN_HEADER, BEARER + jwts[1]);

        return ResponseEntity.ok()
            .build();
    }

    // Refresh Token이 유효하지 않으면 || Refresh Token이 DB에 없으면 || Device Token이 DB에 없으면
    @ExceptionHandler({JWTVerificationException.class, NoSuchRefreshTokenException.class,
        InvalidRefreshTokenException.class, IOException.class})
//    , NoSuchDeviceTokenException.class
    public ResponseEntity<?> handleUnauthorized(Exception e, HttpServletResponse response) {
        // ToDo: null 해도 되는지 테스트
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader(ACCESS_TOKEN_HEADER, null);
        response.setHeader(REFRESH_TOKEN_HEADER, null);

        return ResponseEntity.status(SC_UNAUTHORIZED)
            .body(e.getMessage());
    }

    @ExceptionHandler({NoSuchMemberException.class})
    public ResponseEntity<?> handle410Exception(Exception e, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader(ACCESS_TOKEN_HEADER, null);
        response.setHeader(REFRESH_TOKEN_HEADER, null);

        // 410 Gone
        return ResponseEntity.status(GONE)
            .body(e.getMessage());
    }

    private String getHttpAndDomain(HttpServletRequest request) {
        // request의 domain을 가져온다.
        String xForwardedHost = request.getHeader("x-forwarded-host");
        log.info("x-forwarded-host: " + xForwardedHost);

        String origin = request.getHeader(HttpHeaders.ORIGIN);
        log.info("origin: " + origin);

        // x-forwarded-host와 origin이 모두 null이면
        if (xForwardedHost == null && origin == null) {
            // 프론트가 로컬이라는 뜻
            return "http://localhost:8080";
        }
        // x-forwarded-host가 null이 아니면
        else {
            // x-forwarded-host를 반환
            return "https://" + xForwardedHost;
        }
    }
}
