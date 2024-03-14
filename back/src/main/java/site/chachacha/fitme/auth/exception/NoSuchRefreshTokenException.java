package site.chachacha.fitme.auth.exception;

// DB에 Refresh Token이 없을 때 발생하는 예외
public class NoSuchRefreshTokenException extends RuntimeException {
    private static final String message = "Refresh Token이 존재하지 않습니다.";

    public NoSuchRefreshTokenException() {
        super(message);
    }
}
