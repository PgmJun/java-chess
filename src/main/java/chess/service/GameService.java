package chess.service;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.GameInfoDto;
import chess.entity.GameEntity;
import chess.entity.PieceEntity;
import chess.repository.game.GameRepository;
import chess.repository.piece.PieceRepository;

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

    public ChessGame findGameById(final Long gameId) {
        List<PieceEntity> pieceEntities = pieceRepository.findByGameId(gameId);
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 gameId 입니다."));

        ChessBoard chessBoard = createBoardByPieceEntities(pieceEntities);
        return new ChessGame(chessBoard, gameEntity.getTurn());
    }

    public List<GameInfoDto> findAllGameInfo() {
        List<GameEntity> gameEntities = gameRepository.findAll();

        List<GameInfoDto> gameInfos = new ArrayList<>();
        for (GameEntity gameEntity : gameEntities) {
            gameInfos.add(new GameInfoDto(gameEntity.getId(), gameEntity.getGameName()));
        }
        return gameInfos;
    }

    public ChessGame createGame() {
        Turn turn = Turn.firstTurn();
        Long gameId = gameRepository.add(new GameEntity("gameName", turn));

        for (Map.Entry<Position, Piece> entry : ChessBoardGenerator.getInstance().generate().entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            pieceRepository.add(new PieceEntity(gameId, position, piece));
        }

        return findGameByIdAndTurn(gameId, turn);
    }

    public ChessGame loadGame() {
        GameEntity gameEntity = gameRepository.findLastGame()
                .orElseThrow(() -> new IllegalArgumentException("가장 최근 플레이한 게임이 존재하지 않습니다."));
        List<PieceEntity> pieceEntities = pieceRepository.findByGameId(gameEntity.getId());
        ChessBoard chessBoard = createBoardByPieceEntities(pieceEntities);
        return new ChessGame(gameEntity.getId(), chessBoard, gameEntity.getTurn());
    }

    private ChessGame findGameByIdAndTurn(Long gameId, Turn turn) {
        Map<Position, Piece> board = new HashMap<>();
        List<PieceEntity> pieceEntities = pieceRepository.findByGameId(gameId);
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

    public void updateGame(final Long gameId, final Turn turn, final Long pieceId, final Position target) {
        gameRepository.updateTurnById(gameId, turn.now());
        pieceRepository.updatePositionById(pieceId, target.file(), target.rank());
    }
}
