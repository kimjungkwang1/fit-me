package site.chachacha.fitme.domain.cart.exception;

import site.chachacha.fitme.advice.exception.ConflictException;

public class DuplicateCartException extends ConflictException {

    private static final String MESSAGE = "장바구니에 이미 등록된 상품 옵션입니다. 상품 옵션 ID: %d";

    public DuplicateCartException(Long productOptionId) {
        super(String.format(MESSAGE, productOptionId));
    }
}
