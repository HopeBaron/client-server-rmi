package server.service;

import common.model.Permission;
import common.model.User;
import server.dao.UserDAO;
import server.publisher.SetPublisher;

import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO;
    private SetPublisher<User> publisher = new SetPublisher<>();
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
        } catch (SQLException throwables) {
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
    public SetPublisher<User> getPublisher() {
        return publisher;
    }
}
