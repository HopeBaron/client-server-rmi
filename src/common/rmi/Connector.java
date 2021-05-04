package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connector extends Remote {
    public Connection authenticate(String username, String password) throws RemoteException;
}
