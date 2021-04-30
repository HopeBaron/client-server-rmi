package server.dao;

import model.Article;
import model.User;
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
    public Article get(long id) throws SQLException, RemoteException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM articles WHERE id=?");
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next()) return null;
        return create(set);
    }

    @Override
    public List<Article> getAll() throws SQLException, RemoteException {
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
    public void add(Article article) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO articles VALUES(?, ?, ?, ?)");
        statement.setLong(1, article.getId());
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getContent());
        statement.setLong(4, article.getAuthor().getId());
        statement.executeUpdate();
    }

    @Override
    public boolean delete(Article user) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM articles WHERE id=?");
        statement.setLong(1, user.getId());
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean update(Article article) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET title=?, content=?+ " +
                        "WHERE id = ? "
        );

        statement.setString(1, article.getTitle());
        statement.setString(2, article.getContent());
        statement.setLong(3, article.getId());
        return statement.executeUpdate() > 0;
    }

    private Article create(ResultSet set) throws SQLException, RemoteException {
        return new Article(
                set.getLong("id"),
                set.getString("title"),
                set.getString("content"),
                userDAO.get(set.getLong("author_id"))
        );
    }
}
