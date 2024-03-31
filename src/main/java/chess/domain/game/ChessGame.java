package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.position.Position;
import chess.dto.BoardStatusDto;

import java.util.List;

public class ChessGame {
    private Long id;
    private final ChessBoard board;
    private final Turn turn;

    public ChessGame(ChessBoard board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public ChessGame(Long id, ChessBoard board, Turn turn) {
        this.id = id;
        this.board = board;
        this.turn = turn;
    }

    public void move(final Position source, final Position target) {
        PieceColor color = board.getPieceColorOfPosition(source);
        if (!turn.isTurn(color)) {
            throw new IllegalArgumentException(String.format("%s 색의 차례가 아닙니다.", color));
        }
        board.move(source, target);
        turn.next();
    }

    public boolean isGameEnd() {
        List<PieceColor> aliveKingsColor = board.findAliveKingsColor();
        return aliveKingsColor.size() == 1;
    }

    public BoardStatusDto boardState() {
        return board.state();
    }

    public GameResult result() {
        return board.result();
    }

    public Long findPieceIdAtPosition(final Position position) {
        Piece piece = board.getPieceOfPosition(position);
        return piece.id();
    }

    public Long id() {
        return id;
    }

    public Turn turn() {
        return turn;
    }
}
