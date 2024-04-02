package site.chachacha.fitme.domain.dressroom.exception;

import org.springframework.http.HttpStatus;
import site.chachacha.fitme.advice.exception.BusinessException;

public class InferenceFailureException extends BusinessException {

    public InferenceFailureException(String message) {
        super(message);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
