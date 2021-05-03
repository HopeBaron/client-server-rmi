package server.service;

import common.model.Permissions;
import common.model.User;
import server.dao.UserDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class UserService  {
    private UserDAO dao;

    public UserService(UserDAO dao) throws RemoteException {
        super();
        this.dao = dao;
    }

    public User delete(long id) throws SQLException {
        User user = get(id);
        if (dao.delete(id)) return user;
        return null;
    }

    public User create(String username, String password, Permissions permission) {
        User user = new User(0, username, password, permission, Instant.now());
        if (dao.create(user)) dao.get(username);
        return null;
    }

    public void update(User user) {
        User oldUser = dao.get(user.getId());
        if (oldUser != null) dao.update(user);
    }

    public User get(long id) {
        return dao.get(id);
    }

    public User get(String username) {
        return dao.get(username);
    }


    public List<User> getAll() {
        return dao.getAll();
    }
}
