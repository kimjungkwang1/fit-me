package site.chachacha.fitme.domain.member.dto;

import static lombok.AccessLevel.PROTECTED;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String nickname;

    private Boolean gender;

    private String profileUrl;

    private String phoneNumber;

    private Integer birthYear;

    private String address;

    @Builder
    private MemberResponse(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.profileUrl = member.getProfileUrl();
        this.phoneNumber = member.getPhoneNumber();
        this.birthYear = member.getBirthYear();
        this.address = member.getAddress();
    }


    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .member(member)
                .build();
    }
}
