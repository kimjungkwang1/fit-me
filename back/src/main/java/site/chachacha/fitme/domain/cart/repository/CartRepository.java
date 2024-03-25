package site.chachacha.fitme.domain.cart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.domain.cart.entity.Cart;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.entity.ProductSize;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByMemberAndProductAndProductOptionAndProductSize(Member member, Product product, ProductOption productOption,
        ProductSize productSize);

    List<Cart> findByMember(Member member);

    @Query("SELECT c FROM Cart c WHERE c.id IN :cartIds AND c.member.id = :memberId")
    List<Cart> findAllByIdAndMemberId(@Param("cartIds") List<Long> cartIds, @Param("memberId") Long memberId);
}
