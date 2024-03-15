package site.chachacha.fitme.product.exception;

import site.chachacha.fitme.advice.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 상품 ID 입니다. 상품 ID: %d";

    public ProductNotFoundException(Long productId) {
        super(String.format(MESSAGE, productId));
    }
}
