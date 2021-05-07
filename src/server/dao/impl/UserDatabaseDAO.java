package server.dao.impl;

import common.model.Permissions;
import common.model.User;
import server.dao.behaviors.UserDAO;
import server.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseDAO implements UserDAO {

    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<User> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(formate(resultSet));
        }
        statement.close();

        return list;
    }

    @Override
    public User create(User user) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, permission) VALUES(?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getPermission().getValue());

        int affectedRows = statement.executeUpdate();
        if (affectedRows <= 0) return null;
        ResultSet generatedKeys = statement.getGeneratedKeys();
        statement.close();
        generatedKeys.close();
        if (!generatedKeys.next()) return null;
        return get(generatedKeys.getLong(1));
    }

    @Override
    public User get(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id=?"
        );
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        statement.close();
        if (!resultSet.next()) return null;
        return formate(resultSet);
    }

    @Override
    public boolean delete(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET active=? WHERE id=?"
        );
        statement.setBoolean(1, false);
        statement.setLong(2, id);
        boolean res = statement.executeUpdate() > 0;
        statement.close();
        return res;
    }

    @Override
    public boolean update(User user) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET username=?, password=?, permssions=?, active=?"
        );
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getPermission().getValue());
        statement.setBoolean(4, user.isActive());
        boolean res = statement.executeUpdate() > 0;
        statement.close();
        return res;
    }

    @Override
    public User getUserByName(String username) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username=?"
        );
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        statement.close();

        if (!resultSet.next()) return null;
        return formate(resultSet);
    }

    @Override
    public List<User> getStateUsers(boolean state) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE active=?");
        statement.setBoolean(1, state);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<User> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(formate(resultSet));
        }
        statement.close();
        return list;
    }

    private User formate(ResultSet set) throws SQLException {
        return new User(
                set.getLong("id"),
                set.getString("username"),
                set.getString("password"),
                new Permissions(set.getInt("permission")),
                set.getTimestamp("reg_date").toInstant(),
                set.getBoolean("active")
        );
    }

}
