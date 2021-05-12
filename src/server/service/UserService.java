package server.service;

import common.exception.*;
import common.model.Permission;
import common.model.Permissions;
import common.model.User;
import server.dao.behaviors.UserDAO;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public final class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAll(long invoker) throws RemoteException {
        try {
            User invokerUser = userDAO.get(invoker);
            if (!invokerUser.isActive()) {
                throw new MissingAccessException();
            } else if (!invokerUser.getPermission().contains(Permission.MODIFY_OTHERS))
                return userDAO.getStateUsers(true);

            else return userDAO.getAll();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }
    public boolean delete(long invoker, long target) throws RemoteAuthenticationException, RemoteInternalServerError {
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
    public User getUser(long invoker, long id) throws RemoteException {
        try {
        User invokerUser = userDAO.get(invoker);
        User targetUser = userDAO.get(id);
            if (!invokerUser.isActive()) {
                throw new MissingPermissionException();
            } else if (!invokerUser.isAdmin() && !targetUser.isActive())
                return null;
            else return targetUser;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public User newUser(User user) throws RemoteException {
        try {
        User foundUser = userDAO.getUserByName(user.getUsername());
        if(foundUser != null) throw new AlreadyExistException();
        Permissions perms = new Permissions();
        perms.add(Permission.WRITE);
        user.setPermission(perms);

            return userDAO.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }

    public User getUserByName(String username) throws RemoteInternalServerError, RemoteAuthenticationException {
        try {
            User targetUser = userDAO.getUserByName(username);
             if (!targetUser.isActive())
                return null;
            else return targetUser;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteInternalServerError();
        }
    }
}
