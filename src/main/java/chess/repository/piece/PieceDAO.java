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
        Connection connection = DBConnectionPool.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM piece WHERE piece.game_id = ?");
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
            DBConnectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Long add(PieceEntity pieceEntity) {
        Connection connection = DBConnectionPool.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(
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
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectionPool.releaseConnection(connection);
        }
    }

//
//    public User findUserById(final String userId) {
//        Connection connection = DBConnectionPool.getConnection();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user_id = ?");
//            preparedStatement.setString(1, userId);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (!resultSet.next()) {
//                throw new IllegalArgumentException("존재하지 않는 userId 입니다.");
//            }
//            String findUserId = resultSet.getString(1);
//            String findUserName = resultSet.getString(2);
//
//            return new User(findUserId, findUserName);
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<User> findAll() {
//        Connection connection = DBConnectionPool.getConnection();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user");
//
//            List<User> users = new ArrayList<>();
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                String findUserId = resultSet.getString(1);
//                String findUserName = resultSet.getString(2);
//
//                users.add(new User(findUserId, findUserName));
//            }
//            return users;
//
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void deleteUserById(final String userId) {
//        Connection connection = DBConnectionPool.getConnection();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE user_id = ?");
//            preparedStatement.setString(1, userId);
//
//            preparedStatement.executeUpdate();
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void updateUserName(final String userId, final String updateName) {
//        Connection connection = DBConnectionPool.getConnection();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET name = ? WHERE user_id = ?");
//            preparedStatement.setString(1, updateName);
//            preparedStatement.setString(2, userId);
//
//            preparedStatement.executeUpdate();
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
