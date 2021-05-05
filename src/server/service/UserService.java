package server.service;

import common.model.Permission;
import common.model.User;
import server.dao.behaviors.UserDAO;
import server.publisher.Publisher;

import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.sql.SQLException;
import java.util.List;

public final class UserService {
    private final UserDAO userDAO;
    private final Publisher<User> publisher;

    public UserService(UserDAO userDAO, Publisher<User> publisher) {
        this.userDAO = userDAO;
        this.publisher = publisher;
    }
    public User get(long id) throws RemoteException {
        try {
            return userDAO.get(id);
        } catch (SQLException ignored) {
            throw new ServerError("Internal error", null);
        }
    }
    public User delete(long invoker, long target) throws RemoteException {
        try {
        User sourceUser = userDAO.get(invoker);
        User targetUser = userDAO.get(target);
        boolean canModifyOthers = sourceUser.getPermission().contains(Permission.MODIFY_OTHERS);
        boolean isSelf = sourceUser.getId() == targetUser.getId();
        if(!canModifyOthers && !isSelf) throw new ServerError("You don't have permission to delete this user",null);
            userDAO.delete(target);
            return targetUser;
        } catch (SQLException e) {
            e.getStackTrace();
            throw new ServerError("Internal error", null);
        }
    }


    public User update(long invoker, User target) throws RemoteException {
        try {
            User sourceUser = userDAO.get(invoker);
            User targetUserFromDB = userDAO.get(target.getId());
            boolean canModifyOthers = sourceUser.getPermission().contains(Permission.MODIFY_OTHERS);
            boolean isSelf = sourceUser.getId() == targetUserFromDB.getId();
            if(!canModifyOthers && !isSelf) throw new ServerError("You don't have permission to delete this user",null);
            userDAO.update(target);
            User result = userDAO.get(target.getId());
            publisher.notifySubscribers(result);
            return result;

        } catch (SQLException throwables) {
            throw new ServerError("Internal error", null);
        }
    }
    public Publisher<User> getPublisher() {
        return publisher;
    }

    public User getUserByName(long invoker, String username) throws ServerError {
        try {
            return userDAO.getUserByName(username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServerError("Internal server error", null);
        }
    }

    public List<User> getAllUsers(long invoker) throws ServerError {
        try {
            return userDAO.getAll();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServerError("Internal server error", null);
        }
    }
}
