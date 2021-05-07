package server.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ConnectionFactory {
    private final String driverClassName = "org.mariadb.jdbc.Driver";
    private final String connectionUrl = "jdbc:mysql://localhost/rmi";
    private final String dbUser = "rmiUser";
    private final String dbPwd = "rmi";
    private Connection conn;
    private final String validateQuery = "SELECT 1";

    private static ConnectionFactory connectionFactory = null;

    private ConnectionFactory() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if(conn != null) {
            try (PreparedStatement statement = conn.prepareStatement(validateQuery)) {
                statement.executeQuery();
                return conn;
            } catch (SQLException ignored) {

            }
        }
        conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        return conn;
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }
}
