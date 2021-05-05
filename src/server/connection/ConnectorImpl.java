package server.connection;

import common.model.Article;
import common.model.User;
import common.rmi.Connection;
import common.rmi.Connector;
import server.dao.DatabaseArticleDAO;
import server.dao.DatabaseUserDAO;
import server.dao.behaviors.ArticleDAO;
import server.dao.behaviors.UserDAO;
import server.publisher.Publisher;
import server.publisher.SetPublisher;
import server.service.ArticleService;
import server.service.UserService;

import java.rmi.RemoteException;

public final class ConnectorImpl implements Connector {
    private final Publisher<User> userPublisher = new SetPublisher<>();
    private final Publisher<Article> articlePublisher = new SetPublisher<>();
    private final UserDAO userDAO = new DatabaseUserDAO();
    private final ArticleDAO articleDAO = new DatabaseArticleDAO(userDAO);

    private final UserService userService = new UserService(userDAO, userPublisher);
    private final ArticleService articleService = new ArticleService(articleDAO, userDAO, articlePublisher);

    public Connection authenticate(String username, String password) throws RemoteException {
        User userInDb = userService.getUserByName(0, username);
        if (userInDb == null || !userInDb.getPassword().equals(password))
            throw new RemoteException("Unauthorized", new SecurityException("Invalid user details."));
        if (!userInDb.isActive())
            throw new RemoteException("Unauthorized",
                    new SecurityException("This account has been marked as inactive by an admin.")
            );
        return new ConnectionImpl(userInDb.getId(), userService, articleService);
    }
}
