package site.chachacha.fitme.domain.like.exception;

import site.chachacha.fitme.advice.exception.ConflictException;

public class DuplicateProductLikeException extends ConflictException {

    private static final String MESSAGE = "이미 좋아요한 상품입니다.";

    public DuplicateProductLikeException() {
        super(MESSAGE);
    }
}
