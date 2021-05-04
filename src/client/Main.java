package client;

import common.rmi.Connection;
import common.rmi.Connector;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            InetAddress local = InetAddress.getLocalHost();
            Registry registry = LocateRegistry.getRegistry(local.getHostAddress());
            Connector connector = (Connector) registry.lookup("connector");
            Connection connection = connector.authenticate("mhc","1234");
            connection.getUser(connection.getCurrentUserId());
        } catch (UnknownHostException e) {
            System.out.println("Couldn't connect to the server");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {

        }
    }
}
