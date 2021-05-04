package common.rmi;

import common.model.Article;
import common.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    User getUser(long userId) throws RemoteException;

    User getUserByName(String username) throws RemoteException;

    User deleteUser(long targetUser) throws RemoteException;

    ProxyPublisher<User> getUserPublisher() throws RemoteException;

    ProxyPublisher<Article> getArticlePublisher() throws RemoteException;

    long getCurrentUserId() throws RemoteException;
}
