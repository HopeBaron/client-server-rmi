package server.connection;

import common.model.Article;
import common.model.User;
import common.rmi.Connection;
import server.service.ArticleService;
import server.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public final class ConnectionImpl extends UnicastRemoteObject implements Connection {
    private final UserService userService;
    private final ArticleService articleService;
    private final long userId;

    public ConnectionImpl(long userId, UserService userService, ArticleService articleService) throws RemoteException {
        super();
        this.userService = userService;
        this.articleService = articleService;
        this.userId = userId;
    }


    public User getUser(long userId) throws RemoteException {
        return userService.getUser(this.userId, userId);
    }

    public User getUserByName(String username) throws RemoteException {
        return userService.getUserByName(username);
    }

    public boolean deleteUser(long targetUser) throws RemoteException {
        return userService.delete(userId, targetUser);
    }

    @Override
    public Article getArticle(long id) throws RemoteException {
        return articleService.getArticle(userId, id);
    }

    @Override
    public boolean deleteArticle(long id) throws RemoteException {
        return articleService.delete(userId, id);
    }

    @Override
    public List<Article> getArticles() throws RemoteException {
        return articleService.getAll(userId);
    }

    @Override
    public List<Article> getArticlesOf(long target) throws RemoteException {
        return articleService.getArticlesOf(userId, target);
    }

    @Override
    public List<User> getUsers() throws RemoteException {
        return userService.getAll(userId);
    }


    @Override
    public User addUser(User user) throws RemoteException {
        return null;
    }

    @Override
    public Article addArticle(Article article) throws RemoteException {
        return articleService.addArticle(userId, article);
    }

    @Override
    public long getCurrentUserId() {
        return userId;
    }

    @Override
    public User getCurrentUser() throws RemoteException {
        return userService.getUser(userId, userId);
    }
}
