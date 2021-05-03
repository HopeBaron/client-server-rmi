package server.connection;

import server.service.ArticleService;
import server.service.UserService;

public final class Connection {
    private final UserService userService;
    private final ArticleService articleService;

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
