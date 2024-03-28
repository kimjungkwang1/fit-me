package site.chachacha.fitme.domain.review.exception;

import org.springframework.http.HttpStatus;
import site.chachacha.fitme.advice.exception.BusinessException;

public class ImageUploadException extends BusinessException {

    public ImageUploadException() {
        super("이미지 업로드에 실패했습니다.");
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
