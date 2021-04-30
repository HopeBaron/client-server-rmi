package server;

import model.User;
import server.dao.DAO;
import server.dao.UserDAO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArticlesEntryPoint extends Remote {
    DAO<User> getUserDAO() throws RemoteException;
}
