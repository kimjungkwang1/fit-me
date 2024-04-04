package site.chachacha.fitme.domain.auth.dto;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)
public class KakaoOAuthRequest {
    private final String grant_type = "authorization_code";
    private final String client_id;
    private final String redirect_uri;
    private final String code;
    private final String client_secret;

    @Builder
    private KakaoOAuthRequest(String client_id, String redirect_uri, String code, String client_secret) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
        this.client_secret = client_secret;
    }

    public static KakaoOAuthRequest of(String client_id, String redirect_uri, String code, String client_secret) {
        return KakaoOAuthRequest.builder()
                .client_id(client_id)
                .redirect_uri(redirect_uri)
                .code(code)
                .client_secret(client_secret)
                .build();
    }
}
