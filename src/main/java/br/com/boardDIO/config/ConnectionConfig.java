package br.com.boardDIO.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionConfig {

    @Bean
    public Connection getConnection() throws SQLException {
        var url = "jdbc:postgresql://localhost:5432/board";
        var user = "board";
        var password = "board";

        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
