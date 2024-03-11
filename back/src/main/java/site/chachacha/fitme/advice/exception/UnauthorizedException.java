package site.chachacha.fitme.advice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}