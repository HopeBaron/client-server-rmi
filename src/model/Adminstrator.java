package model;

import rmi.interfaces.AdministratorBehavior;
import rmi.interfaces.ArticleBehavior;
import rmi.interfaces.UserBehavior;

import java.rmi.RemoteException;
import java.util.List;

public final class Adminstrator extends AbstractUser implements AdministratorBehavior {
    public Adminstrator(String id, String username, String password, String name) {
        super(id, username, password, name);
    }

    @Override
    public Adminstrator editUsername(String name) throws RemoteException {
        return null;
    }

    @Override
    public Adminstrator editPassword(String password) throws RemoteException {
        return null;
    }

    @Override
    public Adminstrator editName(String username) throws RemoteException {
        return null;
    }

    @Override
    public UserBehavior getUser(String id) throws RemoteException {
        return null;
    }

    @Override
    public List<ArticleBehavior> getAuthorArticles(String authorId) throws RemoteException {
        return null;
    }
}
