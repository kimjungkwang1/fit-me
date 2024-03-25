package site.chachacha.fitme.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.like.entity.ProductLike;
import site.chachacha.fitme.domain.product.entity.Product;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByProductAndMemberId(Product product, Long memberId);
}
