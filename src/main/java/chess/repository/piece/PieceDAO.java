package chess.repository.piece;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.entity.PieceEntity;
import chess.infa.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PieceDAO implements PieceRepository {

    @Override
    public List<PieceEntity> findByGameId(final Long gameId) {
        Connection conn = DBConnectionPool.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM piece WHERE piece.game_id = ?");
            pstmt.setString(1, gameId.toString());
            ResultSet resultSet = pstmt.executeQuery();

            List<PieceEntity> results = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("piece_id");
                PieceType type = PieceType.valueOf(resultSet.getString("type"));
                PieceColor color = PieceColor.valueOf(resultSet.getString("color"));
                ChessRank rank = ChessRank.valueOf(resultSet.getString("rank"));
                ChessFile file = ChessFile.valueOf(resultSet.getString("file"));

                results.add(new PieceEntity(id, gameId, type, color, rank, file));
            }

            return results;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionPool.releaseConnection(conn);
        }
    }

    @Override
    public Long add(PieceEntity pieceEntity) throws SQLException {
        Connection conn = DBConnectionPool.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO piece (`game_id`, `type`, `color`, `rank`, `file`) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, pieceEntity.getGameId());
            pstmt.setString(2, pieceEntity.getType().name());
            pstmt.setString(3, pieceEntity.getColor().name());
            pstmt.setString(4, pieceEntity.getRank().name());
            pstmt.setString(5, pieceEntity.getFile().name());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            generatedKeys.next();

            conn.commit();
            return generatedKeys.getLong(1);
        } catch (final SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            DBConnectionPool.releaseConnection(conn);
        }
    }

    @Override
    public void updatePositionById(Long pieceId, ChessFile file, ChessRank rank) throws SQLException {
        Connection conn = DBConnectionPool.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE piece SET piece.file = ?, piece.rank = ? WHERE piece.piece_id = ?");
            pstmt.setString(1, file.name());
            pstmt.setString(2, rank.name());
            pstmt.setLong(3, pieceId);
            pstmt.executeUpdate();

            conn.commit();
        } catch (final SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            DBConnectionPool.releaseConnection(conn);
        }
    }
}
