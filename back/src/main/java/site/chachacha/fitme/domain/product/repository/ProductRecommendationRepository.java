package site.chachacha.fitme.domain.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import site.chachacha.fitme.domain.product.entity.ProductRecommendation;

public interface ProductRecommendationRepository extends
    JpaRepository<ProductRecommendation, Long> {

    List<ProductRecommendation> findByProductIdWithProduct(Long productId);
}
