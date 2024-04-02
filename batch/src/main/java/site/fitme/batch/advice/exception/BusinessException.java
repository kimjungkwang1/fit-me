package site.fitme.batch.advice.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(final String message) {
        super(message);
    }

    public abstract HttpStatus status();
}
