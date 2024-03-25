package site.chachacha.fitme.domain.auth.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import site.chachacha.fitme.domain.auth.dto.KakaoOAuthResponse;
import site.chachacha.fitme.domain.auth.exception.InvalidRefreshTokenException;
import site.chachacha.fitme.domain.auth.exception.NoSuchRefreshTokenException;
import site.chachacha.fitme.domain.auth.service.AuthService;
import site.chachacha.fitme.domain.auth.service.OAuthService;
import site.chachacha.fitme.domain.member.dto.MemberResponse;
import site.chachacha.fitme.domain.member.exception.NoSuchMemberException;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static site.chachacha.fitme.domain.auth.service.JwtService.ACCESS_TOKEN_HEADER;
import static site.chachacha.fitme.domain.auth.service.JwtService.BEARER;
import static site.chachacha.fitme.domain.auth.service.JwtService.REFRESH_TOKEN_HEADER;

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
    public void oAuth2AuthorizationV1(@PathVariable(name = "provider") String provider, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = getHttpAndDomain(request);

        response.sendRedirect("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId + "&redirect_uri=" + requestUrl + "/api/auth/login/oauth2/code/" + provider);
    }

    @GetMapping(value = "/login/oauth2/code/{provider}")
    public ResponseEntity<?> oAuth2CodeV1(@PathVariable(name = "provider") String provider, @RequestParam(name = "code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchMemberException {
        log.info("code: " + code);

        // 요청한 url을 가져온다.
        String requestUrl = getHttpAndDomain(request);
        log.info("requestUrl: " + requestUrl);

        // Kakao에 Access Token을 요청한다.
        KakaoOAuthResponse kakaoOAuthResponse = oauthService.requestKakao(provider, code, requestUrl);

        // DB에 사용자 정보, Access Token, Refresh Token 저장
        MemberResponse memberResponse = authService.signIn(kakaoOAuthResponse, response);

        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping(value = "/reissue/v1", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reissueJwtsV1(HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException, JWTVerificationException, IOException {
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
        String host = request.getHeader("host");
        if (!host.isBlank()) {
            return "http://" + host;
        }

        // request의 domain을 가져온다.
        String xForwardedHost = request.getHeader("x-forwarded-host");
        log.info("x-forwarded-host: " + xForwardedHost);
        // http://localhost:3000/auth/login/oauth2/code/kakao?code=EGhvUbUYMBzvBOTjYaZlN5HDY-ST_SFqVyCH1IaxHfdfK4PNaKB066Q7kH0KPXNOAAABjbYKJreo9NUiJo7xnA
        if (xForwardedHost != null && xForwardedHost.contains("localhost")) {
            // 프론트 local
            String result = "http://" + xForwardedHost;
            log.info("result: " + result);
            return result;
        }

        String origin = request.getHeader(HttpHeaders.ORIGIN);
        log.info("origin: " + origin);

        if (origin != null) {
            // 프론트 local, 백엔드 dev
            log.info("origin: " + origin);
            return origin;
        }
        else {
            // 프론트 dev, 백엔드 dev
            log.info("frontend: " + frontend);
            return frontend;
        }
    }
}
