package chess.domain.piece;

import chess.domain.game.ScoreRule;
import chess.domain.piece.strategy.MovementStrategy;
import chess.domain.position.Position;

public class Piece {
    protected final PieceType type;

    public Piece(final PieceType type) {
        this.type = type;
    }

    public boolean isInMovableRange(final Position source, final Position target) {
        MovementStrategy movementStrategy = type.movementStrategy();
        return movementStrategy.isMovable(source, target);
    }

    public boolean isColor(final PieceColor color) {
        return this.type.color() == color;
    }

    public boolean isType(final PieceType pieceType) {
        return this.type == pieceType;
    }

    public boolean isPawn() {
        return this.type == PieceType.BLACK_PAWN || this.type == PieceType.WHITE_PAWN;
    }

    public boolean isKing() {
        return this.type == PieceType.BLACK_KING || this.type == PieceType.WHITE_KING;
    }

    public boolean isKnight() {
        return this.type == PieceType.BLACK_KNIGHT || this.type == PieceType.WHITE_KNIGHT;
    }

    public PieceType type() {
        return type;
    }

    public PieceColor color() {
        return type.color();
    }

    public double calculateScore(final ScoreRule scoreRule) {
        return scoreRule.calculate(type.score());
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                '}';
    }
}
