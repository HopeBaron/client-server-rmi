package server;

import model.User;
import server.dao.DAO;
import server.dao.UserDAO;

import java.rmi.RemoteException;

public class Impl implements ArticlesEntryPoint {
    @Override
    public DAO<User> getUserDAO() throws RemoteException {
        return new UserDAO();
    }
}
