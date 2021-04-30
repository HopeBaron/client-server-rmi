package server.dao;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T>  {

    List<T> getAll() throws SQLException, RemoteException;

    void create(T t) throws SQLException, RemoteException;

    T get(long id) throws SQLException, RemoteException;

    boolean delete(long id) throws SQLException, RemoteException;

    boolean update(T t) throws SQLException, RemoteException;
}
