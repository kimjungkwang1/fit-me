package site.chachacha.fitme.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {
    @NotNull
    private Long id;

    @NotNull
    private Long providerId;

    @NotBlank
    private String nickname;

    @Builder
    private MemberResponse(Member member) {
        this.id = member.getId();
        this.providerId = member.getProviderId();
        this.nickname = member.getNickname();
    }

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .member(member)
                .build();
    }
}
