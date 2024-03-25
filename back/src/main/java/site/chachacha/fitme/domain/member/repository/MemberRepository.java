package site.chachacha.fitme.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import site.chachacha.fitme.domain.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQueryDsl {
    Optional<Member> findNotDeletedById(Long id);

    Optional<Member> findNotDeletedByProviderIdWithToken(Long providerId);
}
