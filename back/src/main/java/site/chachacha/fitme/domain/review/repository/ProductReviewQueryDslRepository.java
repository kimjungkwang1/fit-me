package site.chachacha.fitme.domain.review.repository;

import java.util.List;
import java.util.Optional;
import site.chachacha.fitme.domain.review.entity.ProductReview;

public interface ProductReviewQueryDslRepository {

    List<ProductReview> findAllByProductIdWithMember(Long productId);

    Optional<ProductReview> findByIdWithMember(Long reviewId);

    Optional<ProductReview> findByIdWithMemberAndProduct(Long reviewId);

    Boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
