package server.connection;

import common.exception.ErrorCode;
import common.exception.RemoteAuthenticationException;
import common.model.Article;
import common.model.User;
import common.rmi.Connection;
import common.rmi.Connector;
import server.dao.behaviors.ArticleDAO;
import server.dao.behaviors.UserDAO;
import server.dao.impl.ArticleDatabaseDAO;
import server.dao.impl.UserDatabaseDAO;
import server.publisher.Publisher;
import server.publisher.SetPublisher;
import server.service.ArticleService;
import server.service.UserService;

import java.rmi.RemoteException;

public final class ConnectorImpl implements Connector {
    private final Publisher<User> userPublisher = new SetPublisher<>();
    private final Publisher<Article> articlePublisher = new SetPublisher<>();
    private final UserDAO userDAO = new UserDatabaseDAO();
    private final ArticleDAO articleDAO = new ArticleDatabaseDAO(userDAO);

    private final UserService userService = new UserService(userDAO);
    private final ArticleService articleService = new ArticleService(articleDAO, userDAO);

    public Connection authenticate(String username, String password) throws RemoteException {
        User userInDb = userService.getUserByName(username);
        if (userInDb == null || !userInDb.getPassword().equals(password))
            throw new RemoteAuthenticationException(ErrorCode.INVALID_INFO, "Invalid user or password.");
        return new ConnectionImpl(userInDb.getId(), userService, articleService);
    }
}
