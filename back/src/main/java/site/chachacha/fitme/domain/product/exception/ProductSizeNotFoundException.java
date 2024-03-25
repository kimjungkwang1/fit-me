package site.chachacha.fitme.domain.product.exception;

import site.chachacha.fitme.advice.exception.NotFoundException;

public class ProductSizeNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 상품 사이즈 ID 입니다. 상품 사이즈 ID: %d";

    public ProductSizeNotFoundException(Long productSizeId) {
        super(String.format(MESSAGE, productSizeId));
    }
}
