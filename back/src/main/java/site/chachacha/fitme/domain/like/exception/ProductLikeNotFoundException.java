package site.chachacha.fitme.domain.like.exception;

import site.chachacha.fitme.advice.exception.NotFoundException;

public class ProductLikeNotFoundException extends NotFoundException {

    private static final String MESSAGE = "사용자의 상품 좋아요 정보를 찾을 수 없습니다.";

    public ProductLikeNotFoundException() {
        super(MESSAGE);
    }
}
