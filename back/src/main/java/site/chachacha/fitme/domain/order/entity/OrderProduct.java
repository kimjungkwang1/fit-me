package site.chachacha.fitme.domain.order.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.chachacha.fitme.common.entity.BaseEntity;
import site.chachacha.fitme.domain.brand.entity.Brand;
import site.chachacha.fitme.domain.member.entity.Member;
import site.chachacha.fitme.domain.order.exception.NotEnoughStockException;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;
import site.chachacha.fitme.domain.product.entity.ProductSize;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @NotBlank
    private String brandName;

    @NotNull
    private Long productCategoryId;

    @NotBlank
    private String productName;

    @NotBlank
    private String color;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_size_id")
    private ProductSize productSize;

    @NotBlank
    private String size;

    @NotNull
    private int price;

    @NotNull
    private int count;

    @Builder
    private OrderProduct(Member member, Brand brand, Product product, ProductOption productOption,
        ProductSize productSize, int count) throws NotEnoughStockException {
        this.member = member;
        this.member.addOrderProduct(this);

        this.brand = brand;
        this.brandName = brand.getName();

        this.product = product;
        this.productCategoryId = product.getCategory().getId();
        this.productName = product.getName();
        this.product.addOrderProduct(this);

        this.productOption = productOption;
        this.color = productOption.getColor();

        this.productSize = productSize;
        this.productSize.addOrderProduct(count);
        this.size = productSize.getSize();

        this.price = product.getPrice();
        this.count = count;
    }
}
