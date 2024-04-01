package chess.domain.game;

import chess.domain.piece.PieceColor;

import java.util.List;

public enum Winner {
    WHITE,
    BLACK,
    NONE;

    public static Winner calculateWinner(final List<PieceColor> aliveKingsColor) {
        if (aliveKingsColor.size() == 2) {
            return NONE;
        }
        PieceColor color = aliveKingsColor.get(0);
        if (color == PieceColor.BLACK) {
            return BLACK;
        }
        return WHITE;
    }
}
