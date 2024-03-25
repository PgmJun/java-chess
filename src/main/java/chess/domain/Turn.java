package chess.domain;

import chess.domain.piece.PieceColor;

public class Turn {
    private PieceColor turn;

    public static Turn firstTurn() {
        return new Turn(PieceColor.WHITE);
    }

    public Turn(PieceColor turn) {
        this.turn = turn;
    }

    public void next() {
        if (turn.isWhite()) {
            turn = PieceColor.BLACK;
            return;
        }
        turn = PieceColor.WHITE;
    }

    public boolean isTurn(final PieceColor color) {
        return this.turn == color;
    }
}
