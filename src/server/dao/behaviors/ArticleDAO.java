package server.dao.behaviors;

import common.model.Article;

import java.sql.SQLException;
import java.util.List;

public interface ArticleDAO extends CRUDDAO<Article> {
    List<Article> getArticlesOf(long target) throws SQLException;
    List<Article> getStateArticles(boolean state) throws SQLException;
    List<Article> getStateArticlesOf(long id, boolean state) throws SQLException;
}
