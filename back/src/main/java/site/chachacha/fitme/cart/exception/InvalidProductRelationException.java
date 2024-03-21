package site.chachacha.fitme.cart.exception;

import site.chachacha.fitme.advice.exception.BadRequestException;

public class InvalidProductRelationException extends BadRequestException {

    private static final String MESSAGE = "상품 옵션 또는 상품 사이즈가 주어진 상품과 일치하지 않습니다.";

    public InvalidProductRelationException() {
        super(MESSAGE);
    }
}
