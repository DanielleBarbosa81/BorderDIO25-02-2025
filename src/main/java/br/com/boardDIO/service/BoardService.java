package br.com.boardDIO.service;

import br.com.boardDIO.dao.BoardDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class BoardService {

    private final Connection connection;

    public BoardService(Connection connection) {
        this.connection = connection;
    }

    public boolean delete(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);

        try {
            if (!dao.exists(id)) {                  return false;
            }

            dao.delete(id);
            connection.commit(); // Mantendo commit caso autocommit esteja desativado
            return true;

        } catch (SQLException e) {
            connection.rollback(); // Se der erro, faz rollback da transação
            throw e;
        }
    }
}
