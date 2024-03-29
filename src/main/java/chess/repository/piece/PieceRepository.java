package chess.repository.piece;

import chess.entity.PieceEntity;

import java.util.List;

public interface PieceRepository {

    List<PieceEntity> findByGameId(final Long gameId);

    Long add(PieceEntity pieceEntity);
}
