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

    @Override
    public List<DressRoom> findNoOffsetByMemberId(Long memberId, Long dressRoomId) {
        return queryFactory
            .selectFrom(dressRoom)
            .where(dressRoom.member.id.eq(memberId)
                .and(ltDressRoomId(dressRoomId))
            )
            .orderBy(dressRoom.id.desc())
            .limit(10)
            .fetch();
    }

    @Override
    public Optional<DressRoom> findByIdAndMemberId(Long memberId, Long dressRoomId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(dressRoom)
            .where(dressRoom.member.id.eq(memberId)
                .and(dressRoom.id.eq(dressRoomId)))
            .fetchOne()
        );
    }

    @Override
    public List<DressRoom> findByModelAndProductTopAndProductBottom(Long modelId,
        Long productTopId, Long productBottomId) {
        return queryFactory
            .selectFrom(dressRoom)
            .where(dressRoom.model.id.eq(modelId)
                .and(productTopId == null ? dressRoom.productTop.isNull()
                    : dressRoom.productTop.id.eq(productTopId))
                .and(productBottomId == null ? dressRoom.productBottom.isNull()
                    : dressRoom.productBottom.id.eq(productBottomId)))
            .limit(1)
            .fetch();
    }

    @Override
    public Boolean findByProductTopAndNull(Long productTopId) {
        return !queryFactory
            .select(dressRoom.id)
            .from(dressRoom)
            .where(dressRoom.productTop.id.eq(productTopId)
                .and(dressRoom.productBottom.isNull()))
            .limit(1)
            .fetch().isEmpty();
    }

    @Override
    public Boolean findByProductBottomAndNull(Long productBottomId) {
        return !queryFactory
            .select(dressRoom.id)
            .from(dressRoom)
            .where(dressRoom.productBottom.id.eq(productBottomId)
                .and(dressRoom.productTop.isNull()))
            .limit(1)
            .fetch().isEmpty();
    }

    private BooleanExpression ltDressRoomId(Long dressRoomId) {
        return dressRoomId == null ? null : dressRoom.id.lt(dressRoomId);
    }
}
