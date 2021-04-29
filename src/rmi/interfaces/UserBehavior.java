package rmi.interfaces;

import model.Article;

import java.rmi.RemoteException;
import java.util.List;

public interface UserBehavior extends EntityBehavior {

    String getUsername() throws RemoteException;

    String getPassword() throws RemoteException;

    String getName() throws RemoteException;

    UserBehavior editUsername(String name) throws RemoteException;

    UserBehavior editPassword(String password) throws RemoteException;

    UserBehavior editName(String username) throws RemoteException;

    ArticleBehavior getArticle(String articleId) throws RemoteException;

    List<? extends ArticleBehavior> getAuthorArticles(String authorId) throws RemoteException;

}
