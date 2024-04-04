package site.chachacha.fitme.domain.member.service;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.chachacha.fitme.domain.member.dto.MemberResponse;
import site.chachacha.fitme.domain.member.dto.MemberUpdate;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.member.exception.NoSuchMemberException;
import site.chachacha.fitme.domain.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getMemberInfo(Long memberId) throws NoSuchMemberException {
        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(NoSuchMemberException::new);

        return MemberResponse.from(member, false);
    }

    @Transactional
    public void updateMemberInfo(Long memberId, MemberUpdate memberUpdate)
        throws NoSuchMemberException, ConstraintViolationException {
        Member member = memberRepository.findNotDeletedById(memberId)
            .orElseThrow(NoSuchMemberException::new);

        member.updateProfile(memberUpdate);

        memberRepository.save(member);
    }
}
