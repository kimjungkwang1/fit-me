package site.chachacha.fitme.domain.product.entity;

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

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ProductSize {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "product_option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption productOption;

    private String size;

    private int stockQuantity;

    @Builder
    private ProductSize(ProductOption productOption, String size, int stockQuantity) {
        this.productOption = productOption;
        this.size = size;
        this.stockQuantity = stockQuantity;
        this.productOption.addProductSize(this);
    }
}
