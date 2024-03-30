package chess.service;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.GameInfoDto;
import chess.infra.DBConnectionPool;
import chess.infra.entity.GameEntity;
import chess.infra.entity.PieceEntity;
import chess.repository.game.GameRepository;
import chess.repository.piece.PieceRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

        Turn turn = Turn.firstTurn();
        pieceRepository.deleteAll(conn);
        gameRepository.deleteAll(conn);
        Long gameId = gameRepository.add(conn, new GameEntity("gameName", turn));

        for (Map.Entry<Position, Piece> entry : ChessBoardGenerator.getInstance().generate().entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            pieceRepository.add(conn, new PieceEntity(gameId, position, piece));
        }

        conn.commit();
        DBConnectionPool.releaseConnection(conn);
        return findGameByIdAndTurn(gameId, turn);
    }

    public ChessGame loadGame() {
        Connection conn = DBConnectionPool.getConnection();

        GameEntity gameEntity = gameRepository.findLastGame(conn)
                .orElseThrow(() -> new IllegalArgumentException("가장 최근 플레이한 게임이 존재하지 않습니다."));
        List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameEntity.getId());
        ChessBoard chessBoard = createBoardByPieceEntities(pieceEntities);

        DBConnectionPool.releaseConnection(conn);
        return new ChessGame(gameEntity.getId(), chessBoard, gameEntity.getTurn());
    }

    private ChessGame findGameByIdAndTurn(Long gameId, Turn turn) {
        Connection conn = DBConnectionPool.getConnection();

        Map<Position, Piece> board = new HashMap<>();
        List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameId);
        for (PieceEntity pieceEntity : pieceEntities) {
            Piece piece = new Piece(pieceEntity.getId(), pieceEntity.getType(), pieceEntity.getColor());
            Position position = Position.of(pieceEntity.getFile(), pieceEntity.getRank());

            board.put(position, piece);
        }

        return new ChessGame(gameId, new ChessBoard(board), turn);
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

        gameRepository.updateTurnById(conn, gameId, turn.now());
        pieceRepository.updatePositionById(conn, pieceId, target.file(), target.rank());

        conn.commit();
        DBConnectionPool.releaseConnection(conn);
    }

    public ChessGame findGameById(final Long gameId) throws SQLException {
        Connection conn = DBConnectionPool.getConnection();

        List<PieceEntity> pieceEntities = pieceRepository.findByGameId(conn, gameId);
        GameEntity gameEntity = gameRepository.findById(conn, gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 gameId 입니다."));

        ChessBoard chessBoard = createBoardByPieceEntities(pieceEntities);

        conn.commit();
        DBConnectionPool.releaseConnection(conn);
        return new ChessGame(chessBoard, gameEntity.getTurn());
    }

    public List<GameInfoDto> findAllGameInfo() {
        Connection conn = DBConnectionPool.getConnection();

        List<GameEntity> gameEntities = gameRepository.findAll(conn);

        List<GameInfoDto> gameInfos = new ArrayList<>();
        for (GameEntity gameEntity : gameEntities) {
            gameInfos.add(new GameInfoDto(gameEntity.getId(), gameEntity.getGameName()));
        }

        DBConnectionPool.releaseConnection(conn);
        return gameInfos;
    }
}
