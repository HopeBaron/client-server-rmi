package common.rmi;

import common.model.Article;
import common.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Connection extends Remote {

    User getUser(long userId) throws RemoteException;

    User getUserByName(String username) throws RemoteException;

    boolean deleteUser(long targetUser) throws RemoteException;

    Article getArticle(long id) throws RemoteException;

    boolean deleteArticle(long id) throws RemoteException;

    List<Article> getArticles() throws RemoteException;

    List<Article> getArticlesOf(long userId) throws RemoteException;

    List<User> getUsers() throws RemoteException;

    User addUser(User user) throws RemoteException;

    Article addArticle(Article article) throws RemoteException;

    long getCurrentUserId() throws RemoteException;

    User getCurrentUser() throws RemoteException;
}
