package site.chachacha.fitme.domain.review.repository;

import java.util.Optional;
import site.chachacha.fitme.domain.review.entity.ProductReview;

public interface ProductReviewQueryDslRepository {

    Optional<ProductReview> findByIdWithMember(Long reviewId);

    Optional<ProductReview> findByIdWithMemberAndProduct(Long reviewId);

    Boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
