package common.exception;

public class AlreadyExistException extends RemoteAuthenticationException {
    public AlreadyExistException() {
        super(ErrorCode.ALREADY_EXIST, "This username is already taken.");
    }
}
