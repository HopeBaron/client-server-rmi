package client;

import common.exception.RemoteAuthenticationException;
import common.rmi.Connection;
import common.rmi.Connector;

import javax.swing.*;
import java.rmi.RemoteException;

public class Login extends JFrame {
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


                    try {

                        Connection connection = connector.authenticate(userTextValue, finalPassword);

                        ReadingController mainInterface = new ReadingController(connection);
                        JFrame frame = new JFrame();
                        frame.setContentPane(mainInterface.getRoot());
                        frame.pack();
                        frame.setVisible(true);
                    } catch (RemoteException ex) {
                        Throwable exx = ex.getCause();
                        if(exx instanceof RemoteAuthenticationException) {
                            JOptionPane.showMessageDialog(root, exx.getMessage());
                        }
                    }
                }
                );
    }
}
