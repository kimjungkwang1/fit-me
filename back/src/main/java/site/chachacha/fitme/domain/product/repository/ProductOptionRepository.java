package site.chachacha.fitme.domain.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long>, ProductOptionQueryDslRepository {

    List<ProductOption> findAllByProduct(Product product);
    Optional<ProductOption> findByIdWithProduct(Long id);
}
