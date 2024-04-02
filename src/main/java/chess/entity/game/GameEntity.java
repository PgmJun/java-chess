package chess.entity.game;

import chess.domain.game.Turn;

public class GameEntity {
    private Long id;
    private Turn turn;

    public GameEntity(Long id, Turn turn) {
        this.id = id;
        this.turn = turn;
    }

    public GameEntity(Turn turn) {
        this.turn = turn;
    }

    public Long getId() {
        return id;
    }

    public Turn getTurn() {
        return turn;
    }
}
