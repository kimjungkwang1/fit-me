package site.chachacha.fitme.domain.cart.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import site.chachacha.fitme.domain.cart.entity.Cart;
import site.chachacha.fitme.domain.product.dto.MainImageResponse;
import site.chachacha.fitme.domain.product.entity.Product;
import site.chachacha.fitme.domain.product.entity.ProductOption;

@Getter
public class CartResponse {

    private Long id;
    private ProductResponse product;
    private ProductOptionResponse productOption;
    private int productTotalPrice;

    @Builder
    public CartResponse(Long id, ProductResponse product, ProductOptionResponse productOption, int productTotalPrice) {
        this.id = id;
        this.product = product;
        this.productOption = productOption;
        this.productTotalPrice = productTotalPrice;
    }

    public static CartResponse from(Cart cart) {
        Product product = cart.getProduct();
        ProductOption productOption = cart.getProductOption();
        return CartResponse.builder()
            .id(cart.getId())
            .product(
                new ProductResponse(product.getId(), product.getName(),
                    product.getCategory().getId(),
                    product.getMainImage().stream().map(MainImageResponse::from).toList()))
            .productOption(
                new ProductOptionResponse(productOption.getId(), productOption.getColor(), cart.getProductSize().getSize(),
                    cart.getQuantity()))
            .productTotalPrice(cart.getTotalPrice())
            .build();
    }

    @Getter
    @AllArgsConstructor
    static class ProductResponse {

        private Long id;
        private String name;
        private Long categoryId;
        private List<MainImageResponse> mainImages;
    }

    @Getter
    @AllArgsConstructor
    static class ProductOptionResponse {

        private Long id;
        private String color;
        private String size;
        private int quantity;
    }
}
