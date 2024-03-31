package chess.domain.game;

import chess.domain.piece.PieceColor;

public enum Result {
    WIN_WHITE,
    WIN_BLACK,
    DRAW;

    public static Result getWinnerByColor(final PieceColor pieceColor) {
        if (pieceColor.isWhite()) {
            return WIN_WHITE;
        }
        return WIN_BLACK;
    }
}
