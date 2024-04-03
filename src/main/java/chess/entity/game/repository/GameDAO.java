package chess.entity.game.repository;

import chess.domain.piece.PieceColor;
import chess.entity.game.GameEntity;
import chess.infra.db.query.QueryManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GameDAO implements GameRepository {

    @Override
    public Long add(Connection conn, GameEntity game) throws SQLException {
        ResultSet generatedKeys = QueryManager
                .setConnection(conn)
                .insert("INSERT INTO game (turn) VALUES(?)")
                .setString(1, game.getTurn())
                .execute()
                .getGeneratedKeys();

        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    @Override
    public Optional<GameEntity> findLastGame(Connection conn) throws SQLException {
        ResultSet resultSet = QueryManager
                .setConnection(conn)
                .select("SELECT * FROM game ORDER BY game.game_id DESC LIMIT 1")
                .execute();

        Optional<GameEntity> result = Optional.empty();
        if (resultSet.next()) {
            long gameId = resultSet.getLong("game_id");
            String turn = resultSet.getString("turn");
            result = Optional.of(new GameEntity(gameId, turn));
        }

        return result;
    }

    @Override
    public void updateTurnById(final Connection conn, final Long gameId, final PieceColor now) throws SQLException {
        QueryManager
                .setConnection(conn)
                .update("UPDATE game SET game.turn = ? WHERE game.game_id = ?")
                .setString(1, now.name())
                .setLong(2, gameId)
                .execute();
    }

    @Override
    public void deleteAll(final Connection conn) throws SQLException {
        QueryManager
                .setConnection(conn)
                .delete("DELETE FROM game")
                .execute();
    }

    @Override
    public void deleteById(final Connection conn, final Long gameId) throws SQLException {
        QueryManager
                .setConnection(conn)
                .delete("DELETE FROM game WHERE game.game_id = ?")
                .setLong(1, gameId)
                .execute();
    }
}
