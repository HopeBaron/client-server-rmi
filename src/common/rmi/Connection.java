package common.rmi;

import common.model.Article;
import common.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Connection extends Remote {

    User getUser(long userId) throws RemoteException;

    User getUserByName(String username) throws RemoteException;

    User deleteUser(long targetUser) throws RemoteException;

    Article getArticle(long id) throws RemoteException;

    Article deleteArticle(long id) throws RemoteException;

    List<Article> getArticles() throws RemoteException;

    List<Article> getArticlesOf(long userId) throws RemoteException;

    List<User> getUsers() throws RemoteException;

    ProxyPublisher<User> getUserPublisher() throws RemoteException;

    ProxyPublisher<Article> getArticlePublisher() throws RemoteException;

    long getCurrentUserId() throws RemoteException;
}
