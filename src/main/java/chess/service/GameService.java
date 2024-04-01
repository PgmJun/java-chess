package chess.service;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.entity.PieceEntity;
import chess.entity.game.GameEntity;
import chess.entity.game.repository.GameRepository;
import chess.entity.piece.repository.PieceRepository;
import chess.infra.DBConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameService {
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;

    public GameService(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public ChessGame createNewGame() throws SQLException {
        Connection conn = DBConnectionPool.getConnection();
        try {
            conn.setAutoCommit(false);

            pieceRepository.deleteAll(conn);
            gameRepository.deleteAll(conn);

            Turn turn = Turn.firstTurn();
            Long gameId = gameRepository.add(conn, new GameEntity("gameName", turn));

            for (Map.Entry<Position, Piece> entry : ChessBoardGenerator.getInstance().generate().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                pieceRepository.add(conn, new PieceEntity(gameId, position, piece));
            }
            conn.commit();
            return findGameByIdAndTurn(gameId, turn);
        } catch (Exception exception) {
            conn.rollback();
            throw new RuntimeException(exception);
        } finally {
            conn.setAutoCommit(true);
            DBConnectionPool.releaseConnection(conn);
        }
    }

    private ChessGame findGameByIdAndTurn(Long gameId, Turn turn) {
        Connection conn = DBConnectionPool.getConnection();
        try {
            Map<Position, Piece> board = new HashMap<>();
            List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameId);
            for (PieceEntity pieceEntity : pieceEntities) {
                Piece piece = new Piece(pieceEntity.getId(), pieceEntity.getType(), pieceEntity.getColor());
                Position position = Position.of(pieceEntity.getFile(), pieceEntity.getRank());

                board.put(position, piece);
            }

            return new ChessGame(gameId, new ChessBoard(board), turn);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            DBConnectionPool.releaseConnection(conn);
        }
    }

    public ChessGame loadGame() {
        Connection conn = DBConnectionPool.getConnection();
        try {
            GameEntity gameEntity = gameRepository.findLastGame(conn)
                    .orElseThrow(() -> new IllegalArgumentException("가장 최근 플레이한 게임이 존재하지 않습니다."));
            List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameEntity.getId());

            ChessBoard chessBoard = createBoardByPieceEntities(pieceEntities);

            DBConnectionPool.releaseConnection(conn);
            return new ChessGame(gameEntity.getId(), chessBoard, gameEntity.getTurn());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            DBConnectionPool.releaseConnection(conn);
        }
    }

    private ChessBoard createBoardByPieceEntities(List<PieceEntity> pieceEntities) {
        Map<Position, Piece> board = new HashMap<>();
        for (PieceEntity pieceEntity : pieceEntities) {
            Piece piece = new Piece(pieceEntity.getId(), pieceEntity.getType(), pieceEntity.getColor());
            Position position = Position.of(pieceEntity.getFile(), pieceEntity.getRank());

            board.put(position, piece);
        }
        ChessBoard chessBoard = new ChessBoard(board);
        return chessBoard;
    }

    public void updateGame(final Long gameId, final Turn turn, final Long pieceId, final Position target) throws SQLException {
        Connection conn = DBConnectionPool.getConnection();
        try {
            conn.setAutoCommit(false);
            gameRepository.updateTurnById(conn, gameId, turn.now());
            pieceRepository.updatePositionById(conn, pieceId, target.file(), target.rank());

            conn.commit();
            DBConnectionPool.releaseConnection(conn);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            conn.setAutoCommit(true);
            DBConnectionPool.releaseConnection(conn);
        }
    }

    public void deleteLatestGame(final Long gameId) {
        Connection conn = DBConnectionPool.getConnection();
        try {
            gameRepository.deleteById(conn, gameId);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            DBConnectionPool.releaseConnection(conn);
        }
    }
}
