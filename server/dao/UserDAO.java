package server.dao;

import model.Permissions;
import model.User;
import server.ConnectionFactory;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAO implements DAO<User> {

    @Override
    public User get(long id) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
        statement.setLong(1, id);
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
    public void add(User user) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ? VALUES(?, ?, ?, ?, ?, ?)");
        statement.setString(1, "users");
        statement.setLong(2, user.getId());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPassword());
        statement.setInt(5, user.getPermission().getValue());
        statement.setTimestamp(6, Timestamp.from(user.getRegistartionDate()));
        statement.executeUpdate();
    }

    @Override
    public boolean delete(User user) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
        statement.setLong(1, user.getId());
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    private User create(ResultSet set) throws SQLException {
        return new User(
                set.getLong("id"),
                set.getString("username"),
                set.getString("password"),
                new Permissions(set.getInt("permission")),
                set.getTimestamp("reg_date").toInstant()
        );
    }
}
