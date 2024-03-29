package site.chachacha.fitme.domain.member.repository;

import java.util.Optional;
import site.chachacha.fitme.domain.member.entity.Member;

public interface MemberRepositoryQueryDsl {

    Optional<Member> findNotDeletedById(Long id);

    Optional<Member> findNotDeletedByIdWithToken(Long id);

    Optional<Member> findNotDeletedByIdWithCart(Long id);

    Optional<Member> findNotDeletedByProviderIdWithToken(Long providerId);
}
