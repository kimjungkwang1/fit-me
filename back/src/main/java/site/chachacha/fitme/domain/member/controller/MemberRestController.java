package site.chachacha.fitme.domain.member.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.chachacha.fitme.common.annotation.MemberId;
import site.chachacha.fitme.domain.member.dto.MemberResponse;
import site.chachacha.fitme.domain.member.dto.MemberUpdate;
import site.chachacha.fitme.domain.member.exception.NoSuchMemberException;
import site.chachacha.fitme.domain.member.service.MemberService;

@RequiredArgsConstructor
@RequestMapping(value = "/api/members", consumes = APPLICATION_JSON_VALUE)
@RestController
public class MemberRestController {
    private final MemberService memberService;

    // 회원 정보 조회
    @GetMapping
    public ResponseEntity<?> getMemberInfo(@MemberId Long memberId) throws NoSuchMemberException {
        MemberResponse memberInfo = memberService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfo);
    }

    // 회원 정보 수정
    @PostMapping
    public ResponseEntity<?> updateMemberInfo(@MemberId Long memberId, @Validated @RequestBody MemberUpdate memberUpdate) throws NoSuchMemberException, ConstraintViolationException {
        memberService.updateMemberInfo(memberId, memberUpdate);

        return ResponseEntity.noContent().build();
    }
}
