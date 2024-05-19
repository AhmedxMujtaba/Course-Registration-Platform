package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseLink {
    private Connection connection;

    public DataBaseLink() {
        this.connection = null;
    }

    // Establishes a connection to the database
    public void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/course_management";
            String user = "root"; // Default user for XAMPP
            String password = ""; // Default password for XAMPP (usually empty)
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Closes the connection to the database
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Executes a SELECT query and returns the result as a ResultSet
    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Executes an INSERT, UPDATE, or DELETE query and returns the number of affected rows
    public int executeUpdate(String query) {
        int result = 0;
        try {
            Statement stmt = connection.createStatement();
            result = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Returns the connection object for use in prepared statements
    public Connection getConnection() {
        return connection;
    }
    public int executeUpdateAndGetGeneratedKeys(String query) {
        int generatedKey = -1;
        try {
            connect();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedKey;
    }
}
