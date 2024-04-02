package chess.entity.game.repository;

import chess.domain.game.Turn;
import chess.domain.piece.PieceColor;
import chess.entity.game.GameEntity;
import chess.infra.db.query.QueryManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDAO implements GameRepository {

    @Override
    public List<GameEntity> findAll(Connection conn) throws SQLException {
        ResultSet resultSet = QueryManager
                .setConnection(conn)
                .select("SELECT * FROM game")
                .executeQuery();

        List<GameEntity> results = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String turnColor = resultSet.getString("turn");
            Turn turn = new Turn(PieceColor.valueOf(turnColor));
            results.add(new GameEntity(id, turn));
        }

        return results;
    }

    @Override
    public Optional<GameEntity> findById(Connection conn, Long id) throws SQLException {
        ResultSet resultSet = QueryManager
                .setConnection(conn)
                .select("SELECT * FROM game WHERE game.game_id = ?")
                .setLong(id)
                .executeQuery();

        Optional<GameEntity> result = Optional.empty();
        if (resultSet.next()) {
            long gameId = resultSet.getLong("id");
            String turnColor = resultSet.getString("turn");
            Turn turn = new Turn(PieceColor.valueOf(turnColor));
            result = Optional.of(new GameEntity(gameId, turn));
        }

        return result;
    }

    @Override
    public Long add(Connection conn, GameEntity game) throws SQLException {
        ResultSet generatedKeys = QueryManager
                .setConnection(conn)
                .insert("INSERT INTO game (turn) VALUES(?)")
                .setString(game.getTurn().now().name())
                .executeUpdate()
                .getGeneratedKeys();

        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    @Override
    public Optional<GameEntity> findLastGame(Connection conn) throws SQLException {
        ResultSet resultSet = QueryManager
                .setConnection(conn)
                .select("SELECT * FROM game ORDER BY game.game_id DESC LIMIT 1")
                .executeQuery();

        Optional<GameEntity> result = Optional.empty();
        if (resultSet.next()) {
            long gameId = resultSet.getLong("game_id");
            String turnColor = resultSet.getString("turn");
            Turn turn = new Turn(PieceColor.valueOf(turnColor));
            result = Optional.of(new GameEntity(gameId, turn));
        }

        return result;
    }

    @Override
    public void updateTurnById(final Connection conn, final Long gameId, final PieceColor now) throws SQLException {
        QueryManager
                .setConnection(conn)
                .update("UPDATE game SET game.turn = ? WHERE game.game_id = ?")
                .setString(now.name())
                .setLong(gameId)
                .executeUpdate();
    }

    @Override
    public void deleteAll(final Connection conn) throws SQLException {
        QueryManager
                .setConnection(conn)
                .delete("DELETE FROM game")
                .executeUpdate();
    }

    @Override
    public void deleteById(final Connection conn, final Long gameId) throws SQLException {
        QueryManager
                .setConnection(conn)
                .delete("DELETE FROM game WHERE game.game_id = ?")
                .setLong(gameId)
                .executeUpdate();
    }
}
