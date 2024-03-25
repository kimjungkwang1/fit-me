package site.chachacha.fitme.domain.member.repository;

import static site.chachacha.fitme.domain.member.entity.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.member.entity.Member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryQueryDsl {

    private final EntityManager em;

    @Override
    public Optional<Member> findNotDeletedById(Long id) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return Optional.ofNullable(
            query
                .select(member)
                .from(member)
                .leftJoin(member.token).fetchJoin()
                .where(
                    member.id.eq(id)
                    .and(
                        member.isDeleted.isFalse()
                    )
                )
                .fetchOne()
        );
    }

    @Override
    public Optional<Member> findNotDeletedByProviderIdWithToken(Long providerId) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return Optional.ofNullable(
            query
                .select(member)
                .from(member)
                .leftJoin(member.token).fetchJoin()
                .where(
                    member.providerId.eq(providerId)
                    .and(
                        member.isDeleted.isFalse()
                    )
                )
                .fetchOne()
        );
    }
}
