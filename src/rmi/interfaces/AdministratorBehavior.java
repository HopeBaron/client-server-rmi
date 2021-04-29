package rmi.interfaces;

import java.rmi.RemoteException;

public interface AdministratorBehavior extends UserBehavior {
    UserBehavior getUser(String id) throws RemoteException;
}
