package site.chachacha.fitme.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import site.chachacha.fitme.cart.entity.Cart;

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
        return CartResponse.builder()
            .id(cart.getId())
            .product(new ProductResponse(cart.getProduct().getId(), cart.getProduct().getName()))
            .productOption(
                new ProductOptionResponse(cart.getProductOption().getId(), cart.getProductOption().getColor(), cart.getProductSize().getSize(),
                    cart.getQuantity()))
            .productTotalPrice(cart.getTotalPrice())
            .build();
    }

    @Getter
    @AllArgsConstructor
    static class ProductResponse {

        private Long id;
        private String name;
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
