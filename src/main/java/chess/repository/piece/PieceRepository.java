package chess.repository.piece;

import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.entity.PieceEntity;

import java.util.List;

public interface PieceRepository {

    List<PieceEntity> findByGameId(final Long gameId);

    Long add(PieceEntity pieceEntity);

    void updatePositionById(Long pieceId, ChessFile file, ChessRank rank);
}
