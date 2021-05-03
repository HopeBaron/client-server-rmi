package server;

import server.connection.Connector;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        Connector connectorl = new Connector();
        Registry registry = LocateRegistry.getRegistry();
        Connector connector (Connector) UnicastRemoteObject.exportObject(connector)
        registry.rebind("connector", connector);

    }
}
