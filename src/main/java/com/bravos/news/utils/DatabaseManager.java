package com.bravos.news.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class DatabaseManager {

    private static DatabaseManager instance;
    private final HikariDataSource dataSource;

    private DatabaseManager() {
        try {
            Properties properties = XProperties.getInstance().loadResourceProperties("database.properties");
            String host = properties.getProperty("database.host");
            String port = properties.getProperty("database.port");
            String username = properties.getProperty("database.username");
            String password = properties.getProperty("database.password");
            String dbName = properties.getProperty("database.name");
            String connectionUrl = "jdbc:sqlserver://" +
                    host + ":" + port + ";databaseName=" + dbName + "; " +
                    "encrypt=false;" +
                    "trustServerCertificate=false;" +
                    "integratedSecurity=false;";
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            config.setJdbcUrl(connectionUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(300000);
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public static DatabaseManager gI() {
        return getInstance();
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    public void closeConnection(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollBack(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(Statement statement) {
        try {
            if(statement != null && !statement.isClosed()) {
                statement.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollBack(Statement statement) {
        try {
            if(statement != null && !statement.isClosed()) {
                statement.getConnection().rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void closeResultSet(ResultSet resultSet) {
        try {
            if(resultSet != null && !resultSet.isClosed()) {
                resultSet.getStatement().getConnection().close();
                resultSet.getStatement().close();
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        if(dataSource != null) {
            dataSource.close();
            instance = null;
        }
    }
}
