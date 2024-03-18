package site.chachacha.fitme.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import site.chachacha.fitme.annotation.MemberId;
import site.chachacha.fitme.auth.service.JwtService;

public class MemberIdResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    public MemberIdResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class) && parameter.hasParameterAnnotation(
            MemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null || accessToken.isBlank()) {
            return null;
        }
        return jwtService.validateAndExtractMemberIdFromAccessToken(accessToken);
    }
}
