package server.dao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T>  {

    List<T> getAll() throws SQLException;

    boolean create(T t) throws SQLException;

    T get(long id) throws SQLException;

    boolean delete(long id) throws SQLException;

    boolean update(T t) throws SQLException;
}
