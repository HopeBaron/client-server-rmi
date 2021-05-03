package server.connection;

import server.service.ArticleService;
import server.service.UserService;

public class Connection {
    private UserService userService;
    private ArticleService articleService;

    public Connection(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void logout() {

    }
}
