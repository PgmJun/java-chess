package chess.repository.game;

import chess.domain.piece.PieceColor;
import chess.infra.entity.GameEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<GameEntity> findAll(Connection conn);

    Optional<GameEntity> findById(Connection conn, Long id);

    Long add(Connection conn, GameEntity gameName) throws SQLException;

    Optional<GameEntity> findLastGame(Connection conn);

    void updateTurnById(Connection conn, Long gameId, PieceColor now) throws SQLException;

    void deleteAll(Connection conn) throws SQLException;
}
