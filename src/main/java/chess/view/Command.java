package chess.view;

import chess.controller.command.EndCommand;
import chess.controller.command.GameCommand;
import chess.controller.command.MoveCommand;
import chess.controller.command.StartCommand;
import chess.controller.command.StatusCommand;

public enum Command {
    START(new StartCommand()),
    END(new EndCommand()),
    MOVE(new MoveCommand()),
    STATUS(new StatusCommand()),
    ;

    private final GameCommand gameCommand;

    Command(GameCommand gameCommand) {
        this.gameCommand = gameCommand;
    }

    public boolean isType(Command command) {
        return this == command;
    }

    public GameCommand gameState() {
        return gameCommand;
    }
}
