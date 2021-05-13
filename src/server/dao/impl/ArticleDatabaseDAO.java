package server.dao.impl;

import common.model.Article;
import common.model.User;
import server.dao.behaviors.ArticleDAO;
import server.dao.behaviors.CRUDDAO;
import server.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDatabaseDAO implements ArticleDAO {
    private CRUDDAO<User> userDAO;

    public ArticleDatabaseDAO(CRUDDAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<Article> getArticlesOf(long target) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM articles WHERE author_id=?"
        );
        statement.setLong(1, target);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Article> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(formate(resultSet));
        }
        statement.close();
        return list;
    }

    @Override
    public List<Article> getStateArticles(boolean state) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM articles WHERE shown=?"
        );

        statement.setBoolean(1, state);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Article> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(formate(resultSet));
        }


        statement.close();
        return list;
    }

    @Override
    public List<Article> getStateArticlesOf(long id, boolean state) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM articles WHERE shown=? AND author_id=?"
        );

        statement.setBoolean(1, state);
        statement.setLong(2, id);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Article> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(formate(resultSet));
        }
        statement.close();
        return list;
    }

    @Override
    public List<Article> getAll() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM articles"
        );
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Article> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(formate(resultSet));
        }

        ;
        statement.close();
        return list;
    }

    @Override
    public Article create(Article article) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO articles (title, content, author_id, shown) VALUES(?, ?, ?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        statement.setString(1, article.getTitle());
        statement.setString(2, article.getContent());
        statement.setLong(3, article.getAuthor().getId());
        statement.setBoolean(4, true);
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        if (!keys.next()) return null;
        Article res = formate(keys);
        statement.close();
        keys.close();
        return res;

    }

    @Override
    public Article get(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM articles WHERE id=?"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        statement.close();
        if (!resultSet.next()) return null;
        Article res = formate(resultSet);
        return res;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE articles SET shown=? WHERE id=?"
        );
        statement.setBoolean(1, false);
        statement.setLong(2, id);

        boolean res = statement.executeUpdate() > 0;
        statement.close();

        return res;
    }

    @Override
    public boolean update(Article article) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE articles SET title=?, content=?, shown=? WHERE id=?"
        );
        statement.setString(1, article.getTitle());
        statement.setString(2, article.getContent());
        statement.setBoolean(3, article.isShown());
        statement.setLong(4, article.getId());
        boolean res = statement.executeUpdate() > 0;
        statement.close();
        return res;
    }

    private Article formate(ResultSet resultSet) throws SQLException {
        return new Article(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                userDAO.get(resultSet.getLong("author_id")),
                resultSet.getBoolean("shown")
        );
    }
}