package site.chachacha.fitme.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.chachacha.fitme.cart.entity.Cart;
import site.chachacha.fitme.member.entity.Member;
import site.chachacha.fitme.product.entity.Product;
import site.chachacha.fitme.product.entity.ProductOption;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsByMemberAndProductAndProductOption(Member member, Product product, ProductOption productOption);
}
