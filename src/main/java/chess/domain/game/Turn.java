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

    // TODO: is라는 네이밍 적절할까?
    public void isTurn(final PieceColor color) {
        if (this.color != color) {
            throw new IllegalArgumentException(String.format("%s 색의 차례가 아닙니다.", color));
        }
    }
}
