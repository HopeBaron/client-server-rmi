package model;

import rmi.interfaces.ArticleBehavior;
import rmi.interfaces.AuthorBehavior;

import java.rmi.RemoteException;
import java.util.List;

public final class Author extends AbstractUser implements AuthorBehavior {
    public Author(String id, String username, String password, String name) {
        super(id, username, password, name);
    }

    @Override
    public Author editUsername(String name) throws RemoteException {
        return null;
    }

    @Override
    public Author editPassword(String password) throws RemoteException {
        return null;
    }

    @Override
    public Author editName(String username) throws RemoteException {
        return null;
    }

    @Override
    public List<Article> getAuthorArticles(String authorId) throws RemoteException {
        return null;
    }

    @Override
    public List<Article> getCurrentAuthorArticles() {
        return null;
    }
}
