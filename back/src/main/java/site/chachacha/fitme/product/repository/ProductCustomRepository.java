package site.chachacha.fitme.product.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.product.entity.Product;

@Repository
public interface ProductCustomRepository {
    
    List<Product> findAllByProductConditions(Long lastId, Integer size, Integer gender, String ageRange, List<Long> brandIds,
        List<Long> categoryIds, Integer startPrice, Integer endPrice, String sortBy);
}