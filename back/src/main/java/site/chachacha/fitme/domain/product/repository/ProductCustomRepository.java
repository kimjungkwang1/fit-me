package site.chachacha.fitme.domain.product.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.product.entity.Product;

@Repository
public interface ProductCustomRepository {

    List<Product> findAllByProductConditions(Long lastId, Integer lastPopularityScore, Integer size, String keyword, Integer gender, String ageRange,
        List<Long> brandIds, List<Long> categoryIds, Integer startPrice, Integer endPrice, String sortBy);
}