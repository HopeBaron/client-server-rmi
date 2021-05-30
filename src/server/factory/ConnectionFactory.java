package server.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class ConnectionFactory {
    private final String connectionUrl;
    private final String dbUser;
    private final String dbPwd;
    private Connection conn;

    private static ConnectionFactory connectionFactory = null;

    private ConnectionFactory() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        connectionUrl = bundle.getString("url");
        dbUser = bundle.getString("dbUser");
        dbPwd = bundle.getString("dbPass");
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't configure db, please check your properties file.");
        }
    }

    public Connection getConnection() throws SQLException {
        if (conn != null) {
            String validateQuery = "SELECT 1";
            try (PreparedStatement statement = conn.prepareStatement(validateQuery)) {
                statement.executeQuery();
                return conn;
            } catch (SQLException ignored) {

            }
        }
        conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        return conn;
    }

    public static synchronized ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }
}
