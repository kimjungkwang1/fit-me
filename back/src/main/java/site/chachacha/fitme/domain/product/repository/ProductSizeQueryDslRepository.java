package site.chachacha.fitme.domain.product.repository;

import java.util.Optional;
import site.chachacha.fitme.domain.product.entity.ProductSize;

public interface ProductSizeQueryDslRepository {

    Optional<ProductSize> findByIdAndProductOptionId(Long id, Long productOptionId);
}
