package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EntityBehavior extends Remote {
    String getId() throws RemoteException;
}
