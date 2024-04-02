package site.fitme.batch.exception;

import org.springframework.http.HttpStatus;
import site.fitme.batch.advice.exception.BusinessException;

public class NotEnoughStockException extends BusinessException {

    public NotEnoughStockException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
