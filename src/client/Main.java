package client;

import common.rmi.Connector;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        InetAddress host;
        Registry registry;
        Connector connector;
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            JOptionPane.showConfirmDialog(null, "Server is down, please try again later.");
            return;
        }

        try {
            registry = LocateRegistry.getRegistry(host.getHostAddress());
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Registry not found.");
            return;
        }

        try {
            connector = (Connector) registry.lookup("connector");
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Can't access the server's registry.");
            return;
        } catch (NotBoundException e) {
            JOptionPane.showMessageDialog(null, "Connector to the server was not found.");
            return;
        }

        JFrame frame = new JFrame("Login page");
        Login login = new Login(connector);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(login.getRoot());
        frame.pack();
        frame.setVisible(true);
    }
}
