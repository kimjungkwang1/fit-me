package site.chachacha.fitme.domain.product.repository;

import java.util.Optional;
import site.chachacha.fitme.domain.product.entity.Product;

public interface ProductRepositoryQueryDsl {

    Optional<Product> findByIdWithBrand(Long productId);
}
