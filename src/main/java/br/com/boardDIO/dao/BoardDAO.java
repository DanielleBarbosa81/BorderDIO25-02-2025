package br.com.boardDIO.dao;

import br.com.boardDIO.entity.BoardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BoardDAO {

    private final Connection connection;

    public BoardDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS (name, description) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription()); // Adicionando o segundo parâmetro

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("A inserção falhou, nenhuma linha foi afetada.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1)); // Obtém o ID gerado automaticamente
                } else {
                    throw new SQLException("A inserção falhou, nenhum ID foi gerado.");
                }
            }
        }
        return entity;
    }


    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM BOARDS WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        String sql = "SELECT id, name FROM BOARDS WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BoardEntity entity = new BoardEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                return Optional.of(entity); // Retorna a entidade encontrada
            }
        }
        return Optional.empty(); // Retorna vazio caso não encontre o ID
    }

    public boolean exists(final Long id) throws SQLException {
        String sql = "SELECT 1 FROM BOARDS WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Retorna true se houver resultado, false se não
        }
    }
}


