package chess.domain.piece;

import chess.domain.piece.strategy.BishopMovementStrategy;
import chess.domain.piece.strategy.BlackPawnMovementStrategy;
import chess.domain.piece.strategy.KingMovementStrategy;
import chess.domain.piece.strategy.KnightMovementStrategy;
import chess.domain.piece.strategy.MovementStrategy;
import chess.domain.piece.strategy.QueenMovementStrategy;
import chess.domain.piece.strategy.RookMovementStrategy;
import chess.domain.piece.strategy.WhitePawnMovementStrategy;

import java.util.Arrays;

public enum PieceRule {
    WHITE_PAWN(PieceType.PAWN, PieceColor.WHITE, 1, WhitePawnMovementStrategy.getInstance()),
    BLACK_PAWN(PieceType.PAWN, PieceColor.BLACK, 1, BlackPawnMovementStrategy.getInstance()),
    WHITE_ROOK(PieceType.ROOK, PieceColor.WHITE, 5, RookMovementStrategy.getInstance()),
    BLACK_ROOK(PieceType.ROOK, PieceColor.BLACK, 5, RookMovementStrategy.getInstance()),
    WHITE_KNIGHT(PieceType.KNIGHT, PieceColor.WHITE, 2.5, KnightMovementStrategy.getInstance()),
    BLACK_KNIGHT(PieceType.KNIGHT, PieceColor.BLACK, 2.5, KnightMovementStrategy.getInstance()),
    WHITE_BISHOP(PieceType.BISHOP, PieceColor.WHITE, 3, BishopMovementStrategy.getInstance()),
    BLACK_BISHOP(PieceType.BISHOP, PieceColor.BLACK, 3, BishopMovementStrategy.getInstance()),
    WHITE_KING(PieceType.KING, PieceColor.WHITE, 0, KingMovementStrategy.getInstance()),
    BLACK_KING(PieceType.KING, PieceColor.BLACK, 0, KingMovementStrategy.getInstance()),
    WHITE_QUEEN(PieceType.QUEEN, PieceColor.WHITE, 9, QueenMovementStrategy.getInstance()),
    BLACK_QUEEN(PieceType.QUEEN, PieceColor.BLACK, 9, QueenMovementStrategy.getInstance()),
    ;

    private final PieceType type;
    private final PieceColor color;
    private final double score;
    private final MovementStrategy movementStrategy;

    PieceRule(PieceType type, PieceColor color, double score, MovementStrategy movementStrategy) {
        this.type = type;
        this.color = color;
        this.score = score;
        this.movementStrategy = movementStrategy;
    }

    public static PieceRule findRule(final PieceType type, final PieceColor color) {
        return Arrays.stream(values())
                .filter(rule -> rule.color == color && rule.type == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 PieceRule 입니다."));
    }

    public MovementStrategy movementStrategy() {
        return movementStrategy;
    }

    public double score() {
        return score;
    }
}
