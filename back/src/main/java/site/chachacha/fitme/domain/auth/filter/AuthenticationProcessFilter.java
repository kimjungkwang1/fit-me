package site.chachacha.fitme.domain.auth.filter;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static site.chachacha.fitme.enumstorage.messages.JwtMessages.ACCESS_TOKEN;
import static site.chachacha.fitme.enumstorage.messages.Messages.INVALID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import site.chachacha.fitme.domain.auth.exception.InvalidAccessTokenException;
import site.chachacha.fitme.domain.auth.service.JwtService;


/**
 * /auth/login 이외의 요청이 들어올 때, access token이 유효한지 검증하고 인증 처리/인증 실패/토큰 재발급 등을 수행
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationProcessFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private static final List<String> NO_CHECK_URL = List.of("/api/auth", "/api/products",
        "/api/brands", "/auth/login",
        "/error", "/css", "/js", "/img", "/favicon.ico");

    private static final List<String> CHECK_URL = List.of("/like", "/favorites");

    /**
     * "/auth/login"으로 시작하는 URL 요청은 logIn 검증 및 authenticate X 그 외의 URL 요청은 access token 검증 및 authenticate 수행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        log.info("[REQUEST] Method : {}, RequestURI: {}", request.getMethod(),
            request.getRequestURI());
        if (CorsUtils.isPreFlightRequest(request)) {
            log.info("[REQUEST PREFLIGHT] Method : {}, RequestURI: {}", request.getMethod(),
                request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        // 메인 페이지거나, 확인하지 않는 URL이면 바로 다음 필터로 넘어가기
        {
            if (request.getRequestURI().endsWith("reviews") && request.getMethod().equals("GET")) {
                filterChain.doFilter(request, response);
                return;
            }

            if (request.getRequestURI().equals("/") || isNoCheckUrl(request.getRequestURI())) {
                filterChain.doFilter(request, response); //
                return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
            }
        }

        try {
            // 여기에는 애초에 검증 해야하는 요청만 들어오자나
            // access token 검증
            String accessToken = jwtService.extractAccessToken(request);
            //System.out.println("accessToken : " + accessToken);

            if (!jwtService.validateAccessToken(accessToken)) {
                throw new InvalidAccessTokenException("유효하지 않은 토큰입니다.");
            }

            filterChain.doFilter(request, response); //다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기
        }
        // accessToken이 유효하지 않으면,
        catch (InvalidAccessTokenException | IllegalArgumentException e) {
            // 원래 가려던 곳 상태 저장해주기
            response.setStatus(SC_UNAUTHORIZED);
            response.setHeader("Location",
                "/api/auth/reissue/v1?redirectUrl=" + request.getRequestURI());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(ACCESS_TOKEN.getMessage() + INVALID.getMessage());
        }
    }

    private boolean isNoCheckUrl(String url) {
        if (CHECK_URL.stream().anyMatch(url::endsWith)) {
            return false;
        }

        return NO_CHECK_URL.stream().anyMatch(url::startsWith);
    }

//    /**
//     * [인증 허가 메소드]
//     * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
//     *
//     * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성
//     * UsernamePasswordAuthenticationToken의 파라미터
//     * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보)
//     * 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거)
//     * 3. Collection < ? extends GrantedAuthority>로,
//     * UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에,
//     * new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
//     *
//     * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후,
//     * setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가 처리
//     */
//    private void saveAuthentication(Member member) {
//        UserDetails userDetails = new PrincipalUserDetails(member);
//
////        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority(member.getRole().toString())));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
}
