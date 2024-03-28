package site.chachacha.fitme.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.chachacha.fitme.domain.auth.service.JwtService;
import site.chachacha.fitme.resolver.MemberIdResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtService jwtService;

    public WebConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberIdResolver(jwtService));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedOrigins("https://fit-me.site", "http://fit-me.site", "http://localhost:3000")
            .allowedHeaders("Authorization", "AuthorizationRefresh")
            .allowCredentials(true)
            .exposedHeaders(HttpHeaders.LOCATION, "Authorization", "AuthorizationRefresh");
    }
}
