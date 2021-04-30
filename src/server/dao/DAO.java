package server.dao;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> extends Remote, Serializable {

    List<T> getAll() throws SQLException, RemoteException;

    void add(T t) throws SQLException, RemoteException;

    T get(long id) throws SQLException, RemoteException;

    boolean delete(T t) throws SQLException, RemoteException;

    boolean update(T t) throws SQLException, RemoteException;
}
