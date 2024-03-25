package site.chachacha.fitme.domain.cart.exception;

import site.chachacha.fitme.advice.exception.ForbiddenException;

public class UnauthorizedCartAccessException extends ForbiddenException {

    private static final String MESSAGE = "해당 장바구니에 접근할 수 있는 회원이 아닙니다.";

    public UnauthorizedCartAccessException() {
        super(MESSAGE);
    }
}
