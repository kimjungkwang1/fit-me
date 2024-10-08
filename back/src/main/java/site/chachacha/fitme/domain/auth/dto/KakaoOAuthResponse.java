package site.chachacha.fitme.domain.auth.dto;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)
public class KakaoOAuthResponse {
    private final String registrationId;
    private final Long providerId;
    private final String resourceAccessToken;
    private final String resourceRefreshToken;

    @Builder
    private KakaoOAuthResponse(String registrationId, Long providerId, String resourceAccessToken, String resourceRefreshToken) {
        this.registrationId = registrationId;
        this.providerId = providerId;
        this.resourceAccessToken = resourceAccessToken;
        this.resourceRefreshToken = resourceRefreshToken;
    }

    public static KakaoOAuthResponse of(String registrationId, Long providerId, String resourceAccessToken, String resourceRefreshToken) {
        return KakaoOAuthResponse.builder()
                .registrationId(registrationId)
                .providerId(providerId)
                .resourceAccessToken(resourceAccessToken)
                .resourceRefreshToken(resourceRefreshToken)
                .build();
    }
}
