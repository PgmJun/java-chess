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
import chess.infra.db.DBConnectionPool;
import chess.infra.db.transaction.TransactionManager;

import java.sql.Connection;
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

    public ChessGame createNewGame() {
        try {
            return TransactionManager.cud(DBConnectionPool.getConnection(), conn -> {
                pieceRepository.deleteAll(conn);
                gameRepository.deleteAll(conn);

                Turn turn = Turn.firstTurn();
                Long gameId = gameRepository.add(conn, new GameEntity(turn));

                for (Map.Entry<Position, Piece> entry : ChessBoardGenerator.getInstance().generate().entrySet()) {
                    Position position = entry.getKey();
                    Piece piece = entry.getValue();
                    pieceRepository.add(conn, new PieceEntity(gameId, position, piece));
                }
                return findGameByIdAndTurn(conn, gameId, turn);
            });
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private ChessGame findGameByIdAndTurn(final Connection connection, final Long gameId, final Turn turn) {
        return TransactionManager.read(connection, conn -> {
            Map<Position, Piece> board = new HashMap<>();
            List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameId);
            for (PieceEntity pieceEntity : pieceEntities) {
                Piece piece = new Piece(pieceEntity.getId(), pieceEntity.getType(), pieceEntity.getColor());
                Position position = Position.of(pieceEntity.getFile(), pieceEntity.getRank());
                board.put(position, piece);
            }

            return new ChessGame(gameId, new ChessBoard(board), turn);
        });
    }

    public ChessGame loadGame() {
        return TransactionManager.read(DBConnectionPool.getConnection(), conn -> {
            GameEntity gameEntity = gameRepository.findLastGame(conn).orElseThrow(
                    () -> new IllegalArgumentException("가장 최근 플레이한 게임이 존재하지 않습니다."));
            List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameEntity.getId());

            ChessBoard chessBoard = createBoardByPieceEntities(pieceEntities);
            return new ChessGame(gameEntity.getId(), chessBoard, gameEntity.getTurn());
        });
    }

    private ChessBoard createBoardByPieceEntities(final List<PieceEntity> pieceEntities) {
        Map<Position, Piece> board = new HashMap<>();
        for (PieceEntity pieceEntity : pieceEntities) {
            Piece piece = new Piece(pieceEntity.getId(), pieceEntity.getType(), pieceEntity.getColor());
            Position position = Position.of(pieceEntity.getFile(), pieceEntity.getRank());

            board.put(position, piece);
        }
        ChessBoard chessBoard = new ChessBoard(board);
        return chessBoard;
    }

    public void updateGame(final Long gameId, final Turn turn, final Long pieceId, final Position target) {
        try {
            TransactionManager.cud(DBConnectionPool.getConnection(), conn -> {
                gameRepository.updateTurnById(conn, gameId, turn.now());
                pieceRepository.updatePositionById(conn, pieceId, target.file(), target.rank());
                return null;
            });
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void deleteLatestGame(final Long gameId) {
        try {
            TransactionManager.cud(DBConnectionPool.getConnection(), conn -> {
                gameRepository.deleteById(conn, gameId);
                return null;
            });
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
