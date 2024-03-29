package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public class EndCommand implements GameCommand {

    @Override
    public void execute(ChessController chessController, ChessGame chessGame, CommandInfoDto commandInfo) {
        return;
    }

    @Override
    public GameCommand changeCommand(final Command command) {
        return this;
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
