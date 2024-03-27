package chess.domain;

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

    public void validateTurnState(final PieceColor color) {
        if (this.color != color) {
            throw new IllegalArgumentException(String.format("%s 색의 차례가 아닙니다.", color));
        }
    }
}
