package common.exception;

public class MissingAccessException extends RemoteAuthenticationException {
    public MissingAccessException() {
        super(ErrorCode.MISSING_ACCESS, "This account is no longer available.");
    }
}
