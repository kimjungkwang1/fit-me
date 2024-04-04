package site.chachacha.fitme.domain.auth.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.domain.member.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Token {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    // OAuth2.0 provider에서 제공하는 AccessToken
    @NotBlank
    @Column(unique = true)
    private String resourceAccessToken;

    @NotBlank
    @Column(unique = true)
    private String resourceRefreshToken;

    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    protected Token(String resourceAccessToken, String resourceRefreshToken, Member member) {
        this.resourceAccessToken = resourceAccessToken;
        this.resourceRefreshToken = resourceRefreshToken;
        this.member = member;

        this.member.setToken(this);
    }

    // == 비즈니스 로직 == //
    public void updateResourceTokens(String resourceAccessToken, String resourceRefreshToken) {
        this.resourceAccessToken = resourceAccessToken;
        this.resourceRefreshToken = resourceRefreshToken;
    }
}
