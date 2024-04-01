package chess.entity.piece.repository;

import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.entity.PieceEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PieceRepository {

    List<PieceEntity> findByGameId(Connection conn, final Long gameId) throws SQLException;

    Long add(Connection conn, PieceEntity pieceEntity) throws SQLException;

    void updatePositionById(Connection conn, Long pieceId, ChessFile file, ChessRank rank) throws SQLException;

    void deleteAll(Connection conn) throws SQLException;
}
