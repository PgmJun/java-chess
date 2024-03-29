package chess.entity;

import chess.domain.game.Turn;

public class GameEntity {
    private Long id;
    private String gameName;
    private Turn turn;

    public GameEntity(Long id, String gameName, Turn turn) {
        this.id = id;
        this.gameName = gameName;
        this.turn = turn;
    }

    public GameEntity(String gameName, Turn turn) {
        this.gameName = gameName;
        this.turn = turn;
    }

    public Long getId() {
        return id;
    }

    public String getGameName() {
        return gameName;
    }

    public Turn getTurn() {
        return turn;
    }
}
