package Database;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:/home/linnedvard/Appdatabase.db";
    Connection connection = DriverManager.getConnection(URL);

    public DatabaseConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
    }
}
