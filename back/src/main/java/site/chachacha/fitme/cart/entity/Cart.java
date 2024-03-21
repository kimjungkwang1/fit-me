package site.chachacha.fitme.cart.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.entity.BaseEntity;
import site.chachacha.fitme.member.entity.Member;
import site.chachacha.fitme.product.entity.Product;
import site.chachacha.fitme.product.entity.ProductOption;
import site.chachacha.fitme.product.entity.ProductSize;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "product_option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption productOption;

    @JoinColumn(name = "product_size_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductSize productSize;

    private int quantity;

    @Builder
    public Cart(Product product, Member member, ProductOption productOption, ProductSize productSize, int quantity) {
        this.product = product;
        this.member = member;
        this.productOption = productOption;
        this.productSize = productSize;
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return quantity * product.getPrice();
    }
}
