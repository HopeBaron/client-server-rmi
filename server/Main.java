package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) throws RemoteException {

        Impl impl = new Impl();
        ArticlesEntryPoint stub = (ArticlesEntryPoint) UnicastRemoteObject.exportObject(impl, 0);
        LocateRegistry.getRegistry().rebind("ArticlesEntryPoint", stub);
    }
}
