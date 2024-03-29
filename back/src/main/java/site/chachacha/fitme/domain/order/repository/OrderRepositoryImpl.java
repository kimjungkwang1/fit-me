package site.chachacha.fitme.domain.order.repository;

import static site.chachacha.fitme.domain.order.entity.QOrder.order;
import static site.chachacha.fitme.domain.order.entity.QOrderProduct.orderProduct;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import site.chachacha.fitme.domain.order.entity.Order;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findAllByMemberIdWithOrderProduct(Long memberId, Pageable pageable) {
        return queryFactory
            .selectFrom(order)
            .leftJoin(order.orderProducts, orderProduct).fetchJoin()
            .where(order.member.id.eq(memberId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(order.id.desc())
            .fetch();
    }

    @Override
    public Optional<Order> findByIdAndMemberId(Long orderId, Long memberId) {
        return Optional.ofNullable(
            queryFactory
                .selectFrom(order)
                .where(order.id.eq(orderId)
                    .and(order.member.id.eq(memberId)))
                .fetchOne());
    }
}
