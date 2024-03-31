package chess.repository.game;

import chess.domain.game.Turn;
import chess.domain.piece.PieceColor;
import chess.infra.entity.GameEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDAO implements GameRepository {

    @Override
    public List<GameEntity> findAll(Connection conn) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM game");

            ResultSet resultSet = preparedStatement.executeQuery();

            List<GameEntity> results = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String turnColor = resultSet.getString("turn");
                Turn turn = new Turn(PieceColor.valueOf(turnColor));
                results.add(new GameEntity(id, name, turn));
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GameEntity> findById(Connection conn, Long id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM game");

            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<GameEntity> result = Optional.empty();
            if (resultSet.next()) {
                long gameId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String turnColor = resultSet.getString("turn");
                Turn turn = new Turn(PieceColor.valueOf(turnColor));
                result = Optional.of(new GameEntity(gameId, name, turn));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long add(Connection conn, GameEntity game) throws SQLException {
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO game (name, turn) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, game.getGameName());
            preparedStatement.setString(2, game.getTurn().now().name());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            return generatedKeys.getLong(1);
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GameEntity> findLastGame(Connection conn) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM game ORDER BY game.game_id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<GameEntity> result = Optional.empty();
            if (resultSet.next()) {
                long gameId = resultSet.getLong("game_id");
                String name = resultSet.getString("name");
                String turnColor = resultSet.getString("turn");
                Turn turn = new Turn(PieceColor.valueOf(turnColor));
                result = Optional.of(new GameEntity(gameId, name, turn));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTurnById(final Connection conn, final Long gameId, final PieceColor now) throws SQLException {
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE game SET game.turn = ? WHERE game.game_id = ?");
            pstmt.setString(1, now.name());
            pstmt.setLong(2, gameId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(final Connection conn) throws SQLException {
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM game");
            pstmt.executeUpdate();

        } catch (final SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(final Connection conn, final Long gameId) throws SQLException {
        try {
            conn.setAutoCommit(false);
            
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM game WHERE game.game_id = ?");
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();

        } catch (final SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }
    }
}
