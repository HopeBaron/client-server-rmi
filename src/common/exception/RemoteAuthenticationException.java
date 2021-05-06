package common.exception;

import java.rmi.RemoteException;

public final class RemoteAuthenticationException extends RemoteException {
    ErrorCode errorCode;

    public RemoteAuthenticationException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}