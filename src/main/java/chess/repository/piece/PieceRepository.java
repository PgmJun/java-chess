package chess.repository.piece;

import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.infra.entity.PieceEntity;

import java.sql.SQLException;
import java.util.List;

public interface PieceRepository {

    List<PieceEntity> findByGameId(final Long gameId);

    Long add(PieceEntity pieceEntity) throws SQLException;

    void updatePositionById(Long pieceId, ChessFile file, ChessRank rank) throws SQLException;
}
