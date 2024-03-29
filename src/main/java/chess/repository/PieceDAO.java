//package chess.repository;
//
//import chess.domain.piece.Piece;
//import chess.infa.DBConnectionPool;
//import domain.User;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PieceDAO implements PieceRepository {
//
//    public void addUser(final Piece piece) {
//        Connection connection = DBConnectionPool.getConnection();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user VALUES(?, ?)");
//            preparedStatement.setString(1, user.id());
//            preparedStatement.setString(2, user.name());
//            preparedStatement.executeUpdate();
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
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
//}
