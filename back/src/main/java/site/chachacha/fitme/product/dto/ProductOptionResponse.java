package site.chachacha.fitme.product.dto;

import lombok.Getter;
import site.chachacha.fitme.product.entity.ProductOption;

@Getter
public class ProductOptionResponse {

    private Long id;
    private String color;
    private String size;
    private int stockQuantity;

    public ProductOptionResponse(Long id, String color, String size, int stockQuantity) {
        this.id = id;
        this.color = color;
        this.size = size;
        this.stockQuantity = stockQuantity;
    }

    public static ProductOptionResponse from(ProductOption productOption) {
        return new ProductOptionResponse(productOption.getId(), productOption.getColor(), productOption.getSize(), productOption.getStockQuantity());
    }
}
