package common.exception;

public class InactiveAccountException  extends RemoteAuthenticationException {
    public InactiveAccountException() {
        super(ErrorCode.INACTIVE_ACCOUNT, "This account is inactive. Contact super admin to re-activate.");
    }
}
