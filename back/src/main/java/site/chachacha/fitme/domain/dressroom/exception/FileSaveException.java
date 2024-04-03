package site.chachacha.fitme.domain.dressroom.exception;

import org.springframework.http.HttpStatus;
import site.chachacha.fitme.advice.exception.BusinessException;

public class FileSaveException extends BusinessException {

    public FileSaveException() {
        super("파일 저장에 실패했습니다.");
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
