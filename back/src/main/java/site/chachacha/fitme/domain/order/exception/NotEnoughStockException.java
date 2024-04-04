package site.chachacha.fitme.domain.order.exception;

import org.springframework.http.HttpStatus;
import site.chachacha.fitme.advice.exception.BusinessException;

public class NotEnoughStockException extends BusinessException {

    public NotEnoughStockException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
