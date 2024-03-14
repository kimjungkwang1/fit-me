package site.chachacha.fitme.member.repository;

import java.util.Optional;
import site.chachacha.fitme.member.entity.Member;

public interface MemberRepositoryQueryDsl {
    Optional<Member> findNotDeletedById(Long id);
    Optional<Member> findNotDeletedByProviderIdWithToken(Long providerId);
}
