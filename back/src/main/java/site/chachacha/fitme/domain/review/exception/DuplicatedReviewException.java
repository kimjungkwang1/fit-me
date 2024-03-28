package site.chachacha.fitme.domain.review.exception;

import org.springframework.http.HttpStatus;
import site.chachacha.fitme.advice.exception.BusinessException;

public class DuplicatedReviewException extends BusinessException {
    public DuplicatedReviewException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

}
