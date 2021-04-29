package model;

import rmi.interfaces.UserBehavior;

import java.rmi.RemoteException;

public abstract class AbstractUser implements UserBehavior {
    String id;
    String username;
    String password;
    String name;

    public AbstractUser(String id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    @Override
    public String getId() throws RemoteException {
        return id;
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    @Override
    public String getPassword() throws RemoteException {
        return password;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    abstract public AbstractUser editUsername(String name) throws RemoteException;

    @Override
    abstract public AbstractUser editPassword(String password) throws RemoteException;

    @Override
    abstract public AbstractUser editName(String username) throws RemoteException;

    @Override
    public Article getArticle(String articleId) {
        return null;
    }
}
