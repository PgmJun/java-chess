package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.piece.PieceColor;
import chess.domain.position.Position;
import chess.dto.BoardStatusDto;

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
        return board.isKingDead();
    }

    public BoardStatusDto boardState() {
        return board.state();
    }

    public GameResult result() {
        return board.result();
    }
}
