package common.exception;

import java.rmi.RemoteException;

public class RemoteInternalServerError extends RemoteAuthenticationException {
 public RemoteInternalServerError() {
     super(ErrorCode.INTERNAL_SERVER_ERROR,"Internal server error");
 }
}