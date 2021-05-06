package server.dao.behaviors;

import common.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CRUDDAO<User> {
    User getUserByName(String username) throws SQLException;
    List<User> getStateUsers(boolean active) throws SQLException;
}
