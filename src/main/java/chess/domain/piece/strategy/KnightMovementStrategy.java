package chess.domain.piece.strategy;

import chess.domain.position.Position;

public class KnightMovementStrategy implements MovementStrategy {

    private KnightMovementStrategy() {
    }

    public static final KnightMovementStrategy INSTANCE = new KnightMovementStrategy();

    public static KnightMovementStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isMovable(final Position source, final Position target) {
        int fileDistance = source.calculateFileDistanceTo(target);
        int rankDistance = source.calculateRankDistanceTo(target);

        return (fileDistance == 1 && rankDistance == 2) || (fileDistance == 2 && rankDistance == 1);
    }
}
