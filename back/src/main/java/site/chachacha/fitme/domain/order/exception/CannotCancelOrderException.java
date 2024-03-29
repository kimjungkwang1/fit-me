package site.chachacha.fitme.domain.order.exception;

import org.springframework.http.HttpStatus;
import site.chachacha.fitme.advice.exception.BusinessException;

public class CannotCancelOrderException extends BusinessException {

    public CannotCancelOrderException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
