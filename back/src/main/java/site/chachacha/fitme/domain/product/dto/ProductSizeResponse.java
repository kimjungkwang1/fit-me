package site.chachacha.fitme.domain.product.dto;

import lombok.Getter;
import site.chachacha.fitme.domain.product.entity.ProductSize;

@Getter
public class ProductSizeResponse {

    private Long id;
    private String size;
    private int stockQuantity;

    public ProductSizeResponse(Long id, String size, int stockQuantity) {
        this.id = id;
        this.size = size;
        this.stockQuantity = stockQuantity;
    }

    public static ProductSizeResponse from(ProductSize productSize) {
        return new ProductSizeResponse(productSize.getId(), productSize.getSize(), productSize.getStockQuantity());
    }
}
