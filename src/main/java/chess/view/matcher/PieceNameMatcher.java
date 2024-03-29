package chess.view.matcher;

import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;

import java.util.Arrays;

public enum PieceNameMatcher {
    WHITE_PAWN(PieceType.PAWN, PieceColor.WHITE, "p"),
    BLACK_PAWN(PieceType.PAWN, PieceColor.BLACK, "P"),
    WHITE_ROOK(PieceType.ROOK, PieceColor.WHITE, "r"),
    BLACK_ROOK(PieceType.ROOK, PieceColor.BLACK, "R"),
    WHITE_KNIGHT(PieceType.KNIGHT, PieceColor.WHITE, "n"),
    BLACK_KNIGHT(PieceType.KNIGHT, PieceColor.BLACK, "N"),
    WHITE_BISHOP(PieceType.BISHOP, PieceColor.WHITE, "b"),
    BLACK_BISHOP(PieceType.BISHOP, PieceColor.BLACK, "B"),
    WHITE_KING(PieceType.KING, PieceColor.WHITE, "k"),
    BLACK_KING(PieceType.KING, PieceColor.BLACK, "K"),
    WHITE_QUEEN(PieceType.QUEEN, PieceColor.WHITE, "q"),
    BLACK_QUEEN(PieceType.QUEEN, PieceColor.BLACK, "Q"),
    ;

    private final PieceType type;
    private final PieceColor color;
    private final String name;

    PieceNameMatcher(PieceType type, PieceColor color, String name) {
        this.type = type;
        this.color = color;
        this.name = name;
    }

    public static String matchByType(PieceType type, PieceColor color) {
        return Arrays.stream(values())
                .filter(nameMatcher -> nameMatcher.type == type && nameMatcher.color == color)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("매칭되는 이름이 존재하지 않습니다."))
                .name;
    }

    public static boolean isPresentType(final PieceType type) {
        return Arrays.stream(values())
                .anyMatch(pieceNameMatcher -> pieceNameMatcher.type == type);
    }
}
