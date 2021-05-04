package server;

import common.rmi.Connector;
import server.connection.ConnectorImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {
            ConnectorImpl implementedConnector = new ConnectorImpl();
            Registry registry = LocateRegistry.getRegistry();
            Connector connector = (Connector) UnicastRemoteObject.exportObject(implementedConnector, 0);
            registry.rebind("connector", connector);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
