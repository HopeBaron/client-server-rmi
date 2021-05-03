package server.service;

import common.model.Article;
import common.model.Permissions;
import common.model.User;
import server.dao.ArticleDAO;
import server.dao.UserDAO;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class ArticleService {

    private ArticleDAO articleDAO;
    private UserDAO userDAO;

    public ArticleService(ArticleDAO articleDAO, UserDAO userDAO) {
        this.articleDAO = articleDAO;
        this.userDAO = userDAO;
    }

    public Article get(long id) {
        return articleDAO.get(id);
    }


    public Article delete(long id) throws SQLException {
        Article article = this.get(id);
        if (articleDAO.delete(id)) return article;
        return null;
    }


    public User create(String title, String content) {

    }




    public void update(Article user) {
        User oldUser = user.get(user.getId());
        if (oldUser != null) dao.update(user);
    }




    public List<Article> getAll() {
        return articleDAO.getAll();
    }
}
