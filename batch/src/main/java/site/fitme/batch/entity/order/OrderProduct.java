package site.fitme.batch.entity.order;

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
import site.fitme.batch.entity.memebr.Member;
import site.fitme.batch.entity.product.Product;
import site.fitme.batch.entity.product.ProductOption;
import site.fitme.batch.entity.product.ProductSize;
import site.fitme.batch.entity.common.BaseEntity;

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
    public OrderProduct(Member member, Product product, ProductOption productOption,
        ProductSize productSize,
        int count) {
        this.member = member;
        this.member.addOrderProduct(this);

        this.product = product;
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
