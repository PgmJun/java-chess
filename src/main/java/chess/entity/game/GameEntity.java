package chess.entity.game;

import chess.domain.game.Turn;

public class GameEntity {
    private Long id;
    private String turn;

    public GameEntity(Long id, String turn) {
        this.id = id;
        this.turn = turn;
    }

    public GameEntity(Turn turn) {
        this.turn = turn.now().name();
    }

    public Long getId() {
        return id;
    }

    public String getTurn() {
        return turn;
    }
}
