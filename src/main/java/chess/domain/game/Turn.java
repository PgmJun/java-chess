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
        if (color.isWhite()) {
            color = PieceColor.BLACK;
            return;
        }
        color = PieceColor.WHITE;
    }

    public boolean isTurn(final PieceColor color) {
        return this.color == color;
    }
}
