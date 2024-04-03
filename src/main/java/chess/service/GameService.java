package chess.service;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
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
            List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameId);
            return createGameByPieceEntities(pieceEntities, gameId, turn);
        });
    }

    public ChessGame loadGame() {
        return TransactionManager.read(DBConnectionPool.getConnection(), conn -> {
            GameEntity gameEntity = gameRepository.findLastGame(conn).orElseThrow(() -> new IllegalArgumentException("가장 최근 플레이한 게임이 존재하지 않습니다."));
            List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameEntity.getId());

            PieceColor turnColor = PieceColor.valueOf(gameEntity.getTurn());
            return createGameByPieceEntities(pieceEntities, gameEntity.getId(), new Turn(turnColor));
        });
    }

    private ChessGame createGameByPieceEntities(final List<PieceEntity> pieceEntities, final Long gameId, final Turn turn) {
        Map<Position, Piece> board = new HashMap<>();
        for (PieceEntity pieceEntity : pieceEntities) {
            PieceType type = PieceType.valueOf(pieceEntity.getType());
            PieceColor color = PieceColor.valueOf(pieceEntity.getColor());

            ChessFile file = ChessFile.valueOf(pieceEntity.getFile());
            ChessRank rank = ChessRank.valueOf(pieceEntity.getRank());

            Piece piece = new Piece(pieceEntity.getId(), type, color);
            Position position = Position.of(file, rank);

            board.put(position, piece);
        }
        ChessBoard chessBoard = new ChessBoard(board);

        return new ChessGame(gameId, chessBoard, turn);
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
