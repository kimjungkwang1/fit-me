package site.chachacha.fitme.domain.product.dto;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.chachacha.fitme.domain.order.entity.OrderProduct;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductResponseForOrder {

    private Long id;

    private String brandName;

    private Long categoryId;

    private String name;

    private String url;

    @Builder
    private ProductResponseForOrder(OrderProduct orderProduct) {
        this.id = orderProduct.getProduct().getId();
        this.brandName = orderProduct.getBrandName();
        this.categoryId = orderProduct.getProductCategoryId();
        this.name = orderProduct.getProductName();
        this.url = "https://fit-me.site/images/products/" + this.id
            + "/main/mainimage_" + this.id + "_1.jpg";
    }

    public static ProductResponseForOrder of(OrderProduct orderProduct) {
        return ProductResponseForOrder.builder()
            .orderProduct(orderProduct)
            .build();
    }
}
