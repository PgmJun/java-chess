package chess.repository.piece;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.infra.entity.PieceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PieceDAO implements PieceRepository {

    @Override
    public List<PieceEntity> findByGameId(Connection conn, final Long gameId) throws SQLException {
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
    }

    @Override
    public Long add(Connection conn, PieceEntity pieceEntity) throws SQLException {
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

        return generatedKeys.getLong(1);
    }

    @Override
    public void updatePositionById(Connection conn, Long pieceId, ChessFile file, ChessRank rank) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("UPDATE piece SET piece.file = ?, piece.rank = ? WHERE piece.piece_id = ?");
        pstmt.setString(1, file.name());
        pstmt.setString(2, rank.name());
        pstmt.setLong(3, pieceId);
        pstmt.executeUpdate();
    }

    @Override
    public void deleteAll(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM piece");
        pstmt.executeUpdate();
    }
}
