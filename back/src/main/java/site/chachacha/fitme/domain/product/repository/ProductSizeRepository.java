package site.chachacha.fitme.domain.product.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.product.entity.ProductSize;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long>,
    ProductSizeQueryDslRepository {

    Optional<ProductSize> findByIdAndProductOptionId(Long id, Long productOptionId);
}
