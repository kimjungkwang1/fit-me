package site.chachacha.fitme.member.exception;

// DB에 해당 회원이 없을 때 발생하는 예외
public class NoSuchMemberException extends RuntimeException {
    private static final String message = "해당 회원이 존재하지 않습니다.";
    public NoSuchMemberException() {
        super(message);
    }
}
