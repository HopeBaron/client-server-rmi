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
                JOptionPane.showMessageDialog(null, "Please enter a username.");
                return;
            }

            if (finalPassword == null || finalPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a password.");
                return;
            }


                    try {

                        Connection connection = connector.authenticate(userTextValue, finalPassword);

                        ReadingController frame = new ReadingController(connection);
                        frame.setContentPane(frame.getRoot());
                        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    } catch (RemoteException ex) {
                        Throwable exx = ex.getCause();
                        if(exx instanceof RemoteAuthenticationException) {
                            JOptionPane.showMessageDialog(null, exx.getMessage());
                            return;
                        }
                        JOptionPane.showMessageDialog(null, "Remote error occurred.");
                    }
                }
                );
    }
}
