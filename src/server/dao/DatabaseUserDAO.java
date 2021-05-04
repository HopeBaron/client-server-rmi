package server.dao;

import common.model.Permissions;
import common.model.User;
import server.dao.behaviors.CRUDDAO;
import server.dao.behaviors.UserDAO;
import server.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseUserDAO implements UserDAO {


    @Override
    public User get(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
        statement.setLong(1, id);
        ResultSet set = statement.executeQuery();
        if (!set.next()) return null;
        return create(set);
    }


    public User getUserByName(String username) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=?");
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        if (!set.next()) return null;
        return create(set);
    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet set = statement.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (set.next()) {
            users.add(create(set));
        }
        return users;
    }


    @Override
    public boolean create(User user) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, permission, reg_date) " +
                "VALUES(?, ?, ?, ?)");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getPermission().getValue());
        statement.setTimestamp(4, Timestamp.from(user.getRegistrationDate()));
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
        statement.setLong(1, id);
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean update(User user) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET username=?, password=?, permission=?" +
                        "WHERE id=?"
        );

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getPermission().getValue());
        statement.setLong(4, user.getId());
        return statement.executeUpdate() > 0;
    }

    private User create(ResultSet set) throws SQLException {
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
