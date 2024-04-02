package site.fitme.batch.entity.product;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @NotBlank
    private String color;

    @OneToMany(mappedBy = "productOption", cascade = PERSIST, orphanRemoval = true)
    private List<ProductSize> productSize = new ArrayList<>();

    @Builder
    public ProductOption(Product product, String color) {
        this.product = product;
        this.product.addProductOption(this);

        this.color = color;
    }

    // == 연관 관계 메서드 == //
    public void addProductSize(ProductSize productSize) {
        this.productSize.add(productSize);
    }
}
