package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public class EndState implements GameState {

    @Override
    public void operate(ChessController chessController, ChessGame chessGame, CommandInfoDto commandInfo) {
        return;
    }

    @Override
    public GameState changeState(final Command command) {
        return this;
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
