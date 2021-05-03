package server.connection;

import server.connection.Connection;
import server.service.ArticleService;
import server.service.UserService;

public final class Connector {
    private final UserService userService = new UserService();
    private final ArticleService articleService = new ArticleService();
    public Connection authenticate(String username, String password) {
        return new Connection(userService, articleService);
    }
}
