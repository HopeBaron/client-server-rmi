package common.exception;

import java.rmi.RemoteException;

public class RemoteAuthenticationException extends RemoteException {
    ErrorCode errorCode;

    public RemoteAuthenticationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}