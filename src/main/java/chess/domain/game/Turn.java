package chess.domain.game;

import chess.domain.piece.PieceColor;

public class Turn {
    private PieceColor color;

    public static Turn firstTurn() {
        return new Turn(PieceColor.WHITE);
    }

    public Turn(PieceColor color) {
        this.color = color;
    }

    public void next() {
        color = color.reverse();
    }

    public boolean isTurn(final PieceColor color) {
        return this.color == color;
    }
}
