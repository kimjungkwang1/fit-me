package site.chachacha.fitme.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.auth.service.JwtService;

@Slf4j
public class MemberIdResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    public MemberIdResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.out.println("supportsParameter");
        return parameter.getParameterType().equals(Long.class) && parameter.hasParameterAnnotation(
            MemberId.class);
    }

    //
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = jwtService.extractAccessToken(request);
        log.info("[MemberIdResolver] accessToken: {}", accessToken);
        boolean result = jwtService.validateAccessToken(accessToken);
        log.info("[MemberIdResolver] result: {}", result);
        if (!result) {
            return null;
        }

        return jwtService.extractMemberIdFromAccessToken(accessToken);
    }
}
