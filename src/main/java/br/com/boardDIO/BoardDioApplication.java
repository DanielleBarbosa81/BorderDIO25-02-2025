package br.com.boardDIO;

import br.com.boardDIO.migration.MigrationStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class BoardDioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardDioApplication.class, args);

		try (var connection = getConnection()) {
			new MigrationStrategy(connection).executeMigration();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Connection getConnection() throws SQLException {
		// Substitua pelos dados do seu banco de dados
		String url = "jdbc:postgresql://localhost:5432/board";
		String user = "board";
		String password = "board";

		return DriverManager.getConnection(url, user, password);
	}
}
