package domain.piece.type;

import domain.Direction;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceNamePattern;
import domain.position.Position;

import java.util.List;

public final class Queen extends Piece {
    private static final List<Direction> QUEEN_DIRECTION = List.of(Direction.TOP, Direction.DOWN, Direction.RIGHT, Direction.LEFT, Direction.TOP_LEFT, Direction.TOP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT);

    public Queen(PieceColor color) {
        super(PieceNamePattern.apply(color, "q"), color);
    }

    @Override
    public boolean isInMovableRange(Position source, Position target) {
        Direction direction = source.findDirectionTo(target);
        return isMovableDirection(direction) && isMovableDistance(source, target);
    }

    private boolean isMovableDirection(Direction direction) {
        return QUEEN_DIRECTION.contains(direction);
    }

    private boolean isMovableDistance(Position source, Position target) {
        int distance = source.calculateDistanceTo(target);
        return distance > 0;
    }
}
