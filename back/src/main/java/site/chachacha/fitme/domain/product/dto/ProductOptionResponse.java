package site.chachacha.fitme.domain.product.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import site.chachacha.fitme.domain.product.entity.ProductOption;

@Getter
public class ProductOptionResponse {

    private Long id;
    private String color;
    private List<ProductSizeResponse> sizes;

    @Builder
    private ProductOptionResponse(Long id, String color, List<ProductSizeResponse> sizes) {
        this.id = id;
        this.color = color;
        this.sizes = sizes;
    }

    public static ProductOptionResponse from(ProductOption productOption) {
        return ProductOptionResponse.builder()
            .id(productOption.getId())
            .color(productOption.getColor())
            .sizes(productOption.getProductSize().stream().map(ProductSizeResponse::from).toList())
            .build();
    }
}
