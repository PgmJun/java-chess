package chess.repository.game;

import chess.domain.piece.PieceColor;
import chess.entity.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<GameEntity> findAll();

    Optional<GameEntity> findById(Long id);

    Long add(GameEntity gameName);

    Optional<GameEntity> findLastGame();

    void updateTurnById(Long gameId, PieceColor now);
}
