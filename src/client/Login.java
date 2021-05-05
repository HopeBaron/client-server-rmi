package client;

import common.rmi.Connection;
import common.rmi.Connector;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public class Login {
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JPanel root;

    public JPanel getRoot() {
        return root;
    }

    public Login(Connector connector) {
        loginButton.addActionListener(e -> {
            String userTextValue = usernameText.getText();
            String password = null;
            try {
                password = new String(passwordText.getPassword());
            } catch (NullPointerException ignored) {
            }
            String finalPassword = password;

            if (userTextValue == null || userTextValue.isEmpty()) {
                JOptionPane.showMessageDialog(root, "Please enter a username.");
                return;
            }

            if (finalPassword == null || finalPassword.isEmpty()) {
                JOptionPane.showMessageDialog(root, "Please enter a password.");
                return;
            }
            SwingWorker<Connection, Void> worker = new SwingWorker<Connection, Void>() {
                @Override
                protected Connection doInBackground() throws RemoteException {

                    return connector.authenticate(userTextValue, finalPassword);
                }

                @Override
                public void done() {
                    try {
                        Connection connection = get();

                        ReadingController mainInterface = new ReadingController(connection);
                        JFrame frame = new JFrame();
                        frame.setContentPane(mainInterface.getRoot());
                        frame.pack();
                        frame.setVisible(true);
                    } catch (ExecutionException | InterruptedException ignored) {
                        JOptionPane.showMessageDialog(root, "Fail!");
                    }
                }

            };
            worker.execute();


        });
    }
}
