package site.chachacha.fitme.domain.dressroom.repository;

import static site.chachacha.fitme.domain.dressroom.entity.QDressRoom.dressRoom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.dressroom.entity.DressRoom;

@RequiredArgsConstructor
public class DressRoomRepositoryImpl implements DressRoomQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<DressRoom> findNoOffsetByMemberId(Long memberId, Long dressRoomId) {
        return queryFactory
            .selectFrom(dressRoom)
            .where(dressRoom.member.id.eq(memberId)
                .and(ltDressRoomId(dressRoomId)))
            .limit(10)
            .fetch();
    }

    public Optional<DressRoom> findByIdAndMemberId(Long memberId, Long dressRoomId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(dressRoom)
            .where(dressRoom.member.id.eq(memberId)
                .and(dressRoom.id.eq(dressRoomId)))
            .fetchOne()
        );
    }

    private BooleanExpression ltDressRoomId(Long dressRoomId) {
        return dressRoomId == null ? null : dressRoom.id.lt(dressRoomId);
    }
}
