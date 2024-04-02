package chess.entity.game.repository;

import chess.domain.piece.PieceColor;
import chess.entity.game.GameEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<GameEntity> findAll(Connection conn) throws SQLException;

    Optional<GameEntity> findById(Connection conn, Long id) throws SQLException;

    Long add(Connection conn, GameEntity gameName) throws SQLException;

    Optional<GameEntity> findLastGame(Connection conn) throws SQLException;

    void updateTurnById(Connection conn, Long gameId, PieceColor now) throws SQLException;

    void deleteAll(Connection conn) throws SQLException;

    void deleteById(Connection conn, Long gameId) throws SQLException;
}
