package site.chachacha.fitme.domain.review.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.review.entity.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>,
    ProductReviewQueryDslRepository {

    List<ProductReview> findAllByProductId(Long productId);

    Optional<ProductReview> findByIdWithMember(Long reviewId);

    Optional<ProductReview> findByIdWithMemberAndProduct(Long reviewId);

    Boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
