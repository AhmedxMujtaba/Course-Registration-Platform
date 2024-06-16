package com.ahmedxmujtaba.DataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import com.ahmedxmujtaba.Security.Log;

public class DataBaseLink {
    private Connection connection;
    private String url;
    private String user;
    private String password;

    // Hardcoded path to the configuration file
    private static final String CONFIG_FILE_PATH = "config/config.properties";

    public DataBaseLink() {
        loadConfig();
        this.connection = null;
    }

    // Loads the database configuration from the properties file
    private void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            System.out.println("Loading configuration from: " + CONFIG_FILE_PATH);
            File configFile = new File(CONFIG_FILE_PATH);
            if (configFile.exists()) {
                System.out.println("Config file exists at: " + configFile.getAbsolutePath());
            } else {
                System.out.println("Config file does not exist at: " + configFile.getAbsolutePath());
            }
            props.load(input);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            System.out.println("Database URL: " + url);
            System.out.println("Database User: " + user);
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.logError("Unable to load database configuration from " + CONFIG_FILE_PATH + ": " + getClass());
        }
    }

    // Establishes a connection to the database
    public void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.logError("Unable to connect to database (server might be down): " + getClass());
        }
    }

    // Closes the connection to the database
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
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
        Statement stmt = null;
        try {
            connect();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.logError("Query not executed: " + getClass());
        }
        // Note: The caller must close the ResultSet and Statement
        return rs;
    }

    // Executes an INSERT, UPDATE, or DELETE query and returns the number of affected rows
    public int executeUpdate(String query) {
        int result = 0;
        Statement stmt = null;
        try {
            connect();
            stmt = connection.createStatement();
            result = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.logError("Query not executed: " + getClass());
        } finally {
            try {
                if (stmt != null) stmt.close();
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Returns the connection object for use in prepared statements
    public Connection getConnection() {
        return connection;
    }

    // Executes an INSERT query and returns the generated keys
    public int executeUpdateAndGetGeneratedKeys(String query) {
        int generatedKey = -1;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connect();
            stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.logError("Unable to generate keys: " + getClass());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return generatedKey;
    }
}
