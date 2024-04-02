package site.chachacha.fitme.domain.review.repository;

import static site.chachacha.fitme.domain.review.entity.QProductReview.productReview;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import site.chachacha.fitme.domain.review.entity.ProductReview;

@RequiredArgsConstructor
public class ProductReviewRepositoryImpl implements ProductReviewQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductReview> findAllByProductIdWithMember(Long productId) {
        return queryFactory
            .selectFrom(productReview)
            .join(productReview.member).fetchJoin()
            .where(productReview.product.id.eq(productId))
            .fetch();
    }

    @Override
    public Optional<ProductReview> findByIdWithMember(Long reviewId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(productReview)
            .join(productReview.member).fetchJoin()
            .where(productReview.id.eq(reviewId))
            .fetchOne());
    }

    @Override
    public Optional<ProductReview> findByIdWithMemberAndProduct(Long reviewId) {
        return Optional.ofNullable(queryFactory
            .selectFrom(productReview)
            .join(productReview.member)
            .join(productReview.product)
            .where(productReview.id.eq(reviewId))
            .fetchOne());
    }

    @Override
    public Boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        return queryFactory
            .selectOne()
            .from(productReview)
            .where(productReview.member.id.eq(memberId)
                .and(productReview.product.id.eq(productId)))
            .fetchFirst() != null;
    }
}
