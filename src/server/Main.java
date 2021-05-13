package server;

import common.rmi.Connector;
import server.connection.ConnectorImpl;
import server.factory.ConnectionFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ConnectorImpl implementedConnector = new ConnectorImpl();
            Registry registry = LocateRegistry.getRegistry();
            Connector connector = (Connector) UnicastRemoteObject.exportObject(implementedConnector, 0);
            registry.rebind("connector", connector);
        } catch (RemoteException e) {
            System.out.println("Make sure RMI Registry is enabled.");
        } finally {
            try {
                ConnectionFactory.getInstance().getConnection().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
