package br.com.boardDIO.migration;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MigrationStrategy {

    private final Connection connection;

    // Construtor para injetar a conexão
    public MigrationStrategy(Connection connection) {
        this.connection = connection;
    }

    public void executeMigration() {
        // Salvar saída original do console
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        try (FileOutputStream fos = new FileOutputStream("liquibase.log");
             PrintStream ps = new PrintStream(fos)) {

            // Redirecionar saída padrão e erro para o arquivo
            System.setOut(ps);
            System.setErr(ps);

            System.out.println("Iniciando a migração...");

            try (Connection conn = getConnection()) { // Obtém a conexão
                JdbcConnection jdbcConnection = new JdbcConnection(conn);

                // Instancia o Liquibase com o caminho do changelog
                Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml",
                        new ClassLoaderResourceAccessor(), jdbcConnection);

                // Executa a migração
                liquibase.update("");

                System.out.println("Migração concluída com sucesso!");

            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Erro ao executar a migração.");
                e.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Restaurar saída original do console
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    // Método para obter a conexão com o banco de dados
    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/board";
        String user = "board";
        String password = "board";
        return DriverManager.getConnection(url, user, password);
    }
}
