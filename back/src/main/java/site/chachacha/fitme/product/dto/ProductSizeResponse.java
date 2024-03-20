package site.chachacha.fitme.product.dto;

import lombok.Getter;
import site.chachacha.fitme.product.entity.ProductSize;

@Getter
public class ProductSizeResponse {

    private String size;
    private int stockQuantity;

    public ProductSizeResponse(String size, int stockQuantity) {
        this.size = size;
        this.stockQuantity = stockQuantity;
    }

    public static ProductSizeResponse from(ProductSize productSize) {
        return new ProductSizeResponse(productSize.getSize(), productSize.getStockQuantity());
    }
}
