package common.exception;

public class InvalidInfoException extends RemoteAuthenticationException {
    public InvalidInfoException() {
        super(ErrorCode.INVALID_INFO,"Username or password is invalid.");
    }
}
