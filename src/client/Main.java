package client;

import server.ArticlesEntryPoint;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws UnknownHostException, RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress());
        ArticlesEntryPoint a = (ArticlesEntryPoint) registry.lookup("ArticlesEntryPoint");
        System.out.println(a.getUserDAO());
    }
}