package site.chachacha.fitme.domain.cart.exception;

import site.chachacha.fitme.advice.exception.NotFoundException;

public class CartNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 장바구니 ID 입니다. 장바구니 ID: %d";

    public CartNotFoundException(Long cartId) {
        super(String.format(MESSAGE, cartId));
    }
}
