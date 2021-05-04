package server.connection;

import common.model.Article;
import common.model.User;
import common.rmi.Connection;
import common.rmi.ProxyPublisher;
import server.publisher.proxy.ProxyDelegatePublisher;
import server.service.ArticleService;
import server.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public final class ConnectionImpl extends UnicastRemoteObject implements Connection  {
    private final UserService userService;
    private final ArticleService articleService;
    private final ProxyPublisher<User> userProxyPublisher;
    private final ProxyPublisher<Article> articleProxyPublisher;
    private final long userId;

    public ConnectionImpl(long userId, UserService userService, ArticleService articleService) throws RemoteException {
        super();
        this.userService = userService;
        this.articleService = articleService;
        this.userId = userId;
        this.userProxyPublisher = new ProxyDelegatePublisher<>(userService.getPublisher());
        this.articleProxyPublisher = new ProxyDelegatePublisher<>(articleService.getPublisher());
    }


    public User getUser(long userId) throws RemoteException {
        return userService.get(userId);
    }

    public User getUserByName(String username) throws RemoteException {
        return userService.getUserByName(username);
    }

    public User deleteUser(long targetUser) throws RemoteException {
        return userService.delete(userId, targetUser);
    }

    @Override
    public ProxyPublisher<User> getUserPublisher() throws RemoteException {
        return userProxyPublisher;
    }

    @Override
    public ProxyPublisher<Article> getArticlePublisher() throws RemoteException {
        return articleProxyPublisher;
    }

    @Override
    public long getCurrentUserId() {
        return userId;
    }

    public void logout() {

    }
}
