package server.dao;

import common.model.Article;
import common.model.User;
import server.factory.ConnectionFactory;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ArticleDAO implements DAO<Article> {
    DAO<User> userDAO;

    public ArticleDAO(DAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Article get(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM articles WHERE id=?");
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next()) return null;
        return create(set);
    }

    @Override
    public List<Article> getAll() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM articles");
        ResultSet set = statement.executeQuery();
        ArrayList<Article> articles = new ArrayList<>();
        while (set.next()) {
            articles.add(create(set));
        }
        return articles;
    }


    @Override
    public boolean create(Article article) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO articles VALUES(?, ?, ?, ?)");
        statement.setLong(1, article.getId());
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getContent());
        statement.setLong(4, article.getAuthor().getId());
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE articles SET active=? WHERE id=?");
        statement.setBoolean(1, false);
        statement.setLong(2, id);
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean update(Article article) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET title=?, content=?" +
                        "WHERE id = ?"
        );

        statement.setString(1, article.getTitle());
        statement.setString(2, article.getContent());
        statement.setLong(3, article.getId());
        return statement.executeUpdate() > 0;
    }

    public List<Article> getArticlesOf(long id) throws SQLException, RemoteException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM articles WHERE id=?"
        );
        ResultSet set = statement.executeQuery();
        ArrayList<Article> articles = new ArrayList<>();
        while (set.next()) {
            articles.add(create(set));
        }
        return articles;
    }


    private Article create(ResultSet set) throws SQLException {
        return new Article(
                set.getLong("id"),
                set.getString("title"),
                set.getString("content"),
                userDAO.get(set.getLong("author_id"))
        );
    }

}
