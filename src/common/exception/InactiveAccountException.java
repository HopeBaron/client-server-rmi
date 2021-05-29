package common.exception;

public class InactiveAccountException  extends RemoteAuthenticationException {
    public InactiveAccountException() {
        super(ErrorCode.INACTIVE_ACCOUNT, "This account is inactive.");
    }
}
