package server;

import common.rmi.Connector;
import server.connection.ConnectorImpl;
import server.factory.ConnectionFactory;

import javax.swing.*;
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
            System.out.println("Connector is now bound... Server running");
        } catch (RemoteException e) {
            System.out.println("Make sure RMI Registry is enabled.");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ConnectionFactory.getInstance().getConnection().close();
                System.out.println("DB connection closed... Bye!");
            } catch (SQLException throwables) {
                System.out.println("Couldn't not close connection...");
            }
        }));
    }
}
