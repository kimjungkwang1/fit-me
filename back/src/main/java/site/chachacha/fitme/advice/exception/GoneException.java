package site.chachacha.fitme.advice.exception;

import static org.springframework.http.HttpStatus.GONE;

import org.springframework.http.HttpStatus;

public class GoneException extends BusinessException {
    public GoneException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return GONE;
    }

}
