package chess.view;

import chess.controller.command.EndState;
import chess.controller.command.GameState;
import chess.controller.command.MoveState;
import chess.controller.command.StartState;
import chess.controller.command.StatusState;

public enum Command {
    START(new StartState()),
    END(new EndState()),
    MOVE(new MoveState()),
    STATUS(new StatusState()),
    ;

    private final GameState gameState;

    Command(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isType(Command command) {
        return this == command;
    }

    public boolean isNotType(Command command) {
        return this != command;
    }

    public GameState gameState() {
        return gameState;
    }
}
