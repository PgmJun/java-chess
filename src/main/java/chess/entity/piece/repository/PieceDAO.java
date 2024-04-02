package chess.entity.piece.repository;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.entity.PieceEntity;
import chess.infra.db.query.QueryManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDAO implements PieceRepository {

    @Override
    public List<PieceEntity> findByGameId(Connection conn, final Long gameId) throws SQLException {
        ResultSet resultSet = QueryManager.setConnection(conn)
                .select("SELECT * FROM piece WHERE piece.game_id = ?")
                .setString(gameId.toString())
                .executeQuery();

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
        ResultSet generatedKeys = QueryManager.setConnection(conn)
                .insert("INSERT INTO piece (`game_id`, `type`, `color`, `rank`, `file`) VALUES (?, ?, ?, ?, ?)")
                .setLong(pieceEntity.getGameId())
                .setString(pieceEntity.getType().name())
                .setString(pieceEntity.getColor().name())
                .setString(pieceEntity.getRank().name())
                .setString(pieceEntity.getFile().name())
                .executeUpdate()
                .getGeneratedKeys();

        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    @Override
    public void updatePositionById(Connection conn, Long pieceId, ChessFile file, ChessRank rank) throws SQLException {
        QueryManager.setConnection(conn)
                .update("UPDATE piece SET piece.file = ?, piece.rank = ? WHERE piece.piece_id = ?")
                .setString(file.name())
                .setString(rank.name())
                .setLong(pieceId)
                .executeUpdate();
    }

    @Override
    public void deleteAll(Connection conn) throws SQLException {
        QueryManager.setConnection(conn)
                .delete("DELETE FROM piece")
                .executeUpdate();
    }
}
