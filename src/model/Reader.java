package model;

import rmi.interfaces.ArticleBehavior;

import java.rmi.RemoteException;
import java.util.List;

public final class Reader extends AbstractUser {
    public Reader(String id, String username, String password, String name) {
        super(id, username, password, name);
    }

    @Override
    public Reader editUsername(String name) throws RemoteException {
        return null;
    }

    @Override
    public Reader editPassword(String password) throws RemoteException {
        return null;
    }

    @Override
    public Reader editName(String username) throws RemoteException {
        return null;
    }

    @Override
    public List<ArticleBehavior> getAuthorArticles(String authorId) throws RemoteException {
        return null;
    }
}
