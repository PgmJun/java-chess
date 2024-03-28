package chess.domain.piece;

import chess.domain.piece.strategy.BishopMovementStrategy;
import chess.domain.piece.strategy.BlackPawnMovementStrategy;
import chess.domain.piece.strategy.KingMovementStrategy;
import chess.domain.piece.strategy.KnightMovementStrategy;
import chess.domain.piece.strategy.MovementStrategy;
import chess.domain.piece.strategy.QueenMovementStrategy;
import chess.domain.piece.strategy.RookMovementStrategy;
import chess.domain.piece.strategy.WhitePawnMovementStrategy;

public enum PieceType {
    WHITE_PAWN(PieceColor.WHITE, 1, WhitePawnMovementStrategy.getInstance()),
    BLACK_PAWN(PieceColor.BLACK, 1, BlackPawnMovementStrategy.getInstance()),
    WHITE_ROOK(PieceColor.WHITE, 5, RookMovementStrategy.getInstance()),
    BLACK_ROOK(PieceColor.BLACK, 5, RookMovementStrategy.getInstance()),
    WHITE_KNIGHT(PieceColor.WHITE, 2.5, KnightMovementStrategy.getInstance()),
    BLACK_KNIGHT(PieceColor.BLACK, 2.5, KnightMovementStrategy.getInstance()),
    WHITE_BISHOP(PieceColor.WHITE, 3, BishopMovementStrategy.getInstance()),
    BLACK_BISHOP(PieceColor.BLACK, 3, BishopMovementStrategy.getInstance()),
    WHITE_KING(PieceColor.WHITE, 0, KingMovementStrategy.getInstance()),
    BLACK_KING(PieceColor.BLACK, 0, KingMovementStrategy.getInstance()),
    WHITE_QUEEN(PieceColor.WHITE, 9, QueenMovementStrategy.getInstance()),
    BLACK_QUEEN(PieceColor.BLACK, 9, QueenMovementStrategy.getInstance()),
    ;

    private final PieceColor color;
    private final double score;
    private final MovementStrategy movementStrategy;

    PieceType(PieceColor color, double score, MovementStrategy movementStrategy) {
        this.color = color;
        this.score = score;
        this.movementStrategy = movementStrategy;
    }

    public PieceColor color() {
        return color;
    }

    public double score() {
        return score;
    }

    public MovementStrategy movementStrategy() {
        return movementStrategy;
    }
}
