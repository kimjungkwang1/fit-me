package site.chachacha.fitme.domain.product.exception;

import site.chachacha.fitme.advice.exception.NotFoundException;

public class ProductOptionNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 상품 옵션 ID 입니다. 상품 옵션 ID: %d";

    public ProductOptionNotFoundException(Long productOptionId) {
        super(String.format(MESSAGE, productOptionId));
    }
}
