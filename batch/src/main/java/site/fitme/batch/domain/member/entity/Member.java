package site.fitme.batch.domain.member.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.fitme.batch.entity.common.BaseEntity;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    // 카카오톡이 제공하는 사용자 구분을 위한 ID
    @NotNull
    @Column(unique = true)
    private Long providerId;

    @NotBlank
    @Column(length = 30)
    private String nickname;

    // false 남자, true 여자
    private Boolean gender;

    @Column(length = 300)
    private String profileUrl;

    @Column(length = 20)
    private String phoneNumber;

    private Integer birthYear;

    @Column(length = 200)
    private String address;

    @NotNull
    private Boolean isDeleted = false;
}
