package server.dao.behaviors;

import common.model.User;

import java.sql.SQLException;

public interface UserDAO extends CRUDDAO<User> {
    User getUserByName(String username) throws SQLException;
}
