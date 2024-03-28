package site.chachacha.fitme.domain.product.repository;

import java.util.Optional;
import site.chachacha.fitme.domain.product.entity.ProductOption;

public interface ProductOptionQueryDslRepository {
    Optional<ProductOption> findByIdWithProduct(Long id);
}
