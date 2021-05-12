package common.exception;

public class MissingPermissionException extends RemoteAuthenticationException {
    public MissingPermissionException() {
        super(ErrorCode.MISSING_PERMISSION, "You don't have enough permissions.");
    }
}
