package com.example.epamfinalproject.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionPool {
    private static final HikariConfig config = new HikariConfig("/datasource.properties");
    private static final HikariDataSource connection;

    static {

        connection = new HikariDataSource( config );
    }

    private HikariConnectionPool() {}

    public static Connection getConnection() throws SQLException {
        return connection.getConnection();
    }
}
