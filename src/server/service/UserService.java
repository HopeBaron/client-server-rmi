package server.service;

import common.exception.MissingAccessException;
import common.exception.MissingPermissionException;
import common.exception.RemoteAuthenticationException;
import common.exception.RemoteInternalServerError;
import common.model.User;
import server.dao.behaviors.UserDAO;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public final class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAll(long invoker) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);
            if (!invokerUser.isActive()) throw new MissingAccessException();
            else if (!invokerUser.isAdmin()) return userDAO.getStateUsers(true);
            else return userDAO.getAll();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public boolean delete(long invoker, long target) throws RemoteAuthenticationException {
        try {
            User invokerUser = userDAO.get(invoker);
            if (!invokerUser.isActive()) throw new MissingAccessException();
            else if (!invokerUser.isAdmin() && invoker != target)
                throw new MissingPermissionException();
            else return userDAO.delete(target);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }

    }

    public User getUser(long id) throws RemoteAuthenticationException {
        try {
            return userDAO.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public User getUserByName(String username) throws RemoteAuthenticationException {
        try {
            return userDAO.getUserByName(username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }
}
