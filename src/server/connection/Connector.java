package server.connection;

import server.connection.Connection;
import server.service.ArticleService;
import server.service.UserService;

public class Connector {
    UserService userService = new UserService();
    ArticleService articleService = new ArticleService();
    public Connection authenticate(String username, String password) {
        return new Connection(userService, articleService);
    }
}
