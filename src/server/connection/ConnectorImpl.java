package server.connection;

import common.model.User;
import common.rmi.Connection;
import common.rmi.Connector;
import server.dao.DatabaseUserDAO;
import server.dao.behaviors.UserDAO;
import server.publisher.Publisher;
import server.publisher.SetPublisher;
import server.service.ArticleService;
import server.service.UserService;

import java.rmi.RemoteException;

public final class ConnectorImpl implements Connector {
    private final Publisher<User> publisher = new SetPublisher<>();
    private final UserDAO userDAO = new DatabaseUserDAO();
    private final UserService userService = new UserService(userDAO, publisher);
    private final ArticleService articleService = new ArticleService();

    public Connection authenticate(String username, String password) throws RemoteException {
        User userInDb = userService.getUserByName(username);
        if (userInDb == null || !userInDb.getPassword().equals(password))
            throw new SecurityException("Invalid user details.");
        if(!userInDb.isActive()) throw new SecurityException("This account has been marked as inactive by an admin.");
        return new ConnectionImpl(userInDb.getId(), userService, articleService);
    }
}
