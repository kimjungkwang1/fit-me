package site.chachacha.fitme.domain.product.repository;

import java.util.List;
import site.chachacha.fitme.domain.product.entity.ProductRecommendation;

public interface ProductRecommendationQueryDslRepository {

    List<ProductRecommendation> findByProductIdWithProduct(Long productId);
}
