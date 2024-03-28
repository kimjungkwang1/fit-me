package site.chachacha.fitme.domain.order.repository;

import static site.chachacha.fitme.domain.order.QOrderProduct.orderProduct;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.order.OrderProduct;

@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<OrderProduct> findAllByMemberIdAndProductId(Long memberId, Long productId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(orderProduct)
            .where(orderProduct.member.id.eq(memberId)
                .and(orderProduct.product.id.eq(productId)))
            .fetchOne()
        );
    }

    @Override
    public Boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        return queryFactory
            .selectOne()
            .from(orderProduct)
            .where(orderProduct.member.id.eq(memberId)
                .and(orderProduct.product.id.eq(productId)))
            .fetchFirst() != null;
    }
}
