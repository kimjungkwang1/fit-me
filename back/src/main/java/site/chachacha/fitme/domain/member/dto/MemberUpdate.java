package site.chachacha.fitme.domain.member.dto;

import static lombok.AccessLevel.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberUpdate {
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하로 입력해주세요.")
    private String nickname;

    @Nullable
    private Boolean gender;

    @Nullable
    @Size(max = 20, message = "휴대폰 번호는 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[0-9]*$", message = "휴대폰 번호는 숫자만 입력해주세요.")
    private String phoneNumber;

    @Nullable
    @Max(value = 2024, message = "태어난 년도를 다시 확인해주세요.")
    @Min(value = 1900, message = "태어난 년도를 다시 확인해주세요.")
    private Integer birthYear;

    @Nullable
    @Size(min = 2, max = 200, message = "주소는 2자 이상, 200자 이하로 입력해주세요.")
    private String address;

    @Builder
    private MemberUpdate(String nickname, Boolean gender, String phoneNumber, Integer birthYear, String address) {
        this.nickname = nickname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthYear = birthYear;
        this.address = address;
    }
}
