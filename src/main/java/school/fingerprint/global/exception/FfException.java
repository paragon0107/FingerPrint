package school.fingerprint.global.exception;

public class FfException extends RuntimeException {
    private final ErrorCode errorCode;

    public FfException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
