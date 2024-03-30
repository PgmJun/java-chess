package chess.repository.game;

import chess.domain.piece.PieceColor;
import chess.infra.entity.GameEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<GameEntity> findAll();

    Optional<GameEntity> findById(Long id);

    Long add(GameEntity gameName) throws SQLException;

    Optional<GameEntity> findLastGame();

    void updateTurnById(Long gameId, PieceColor now) throws SQLException;
}
