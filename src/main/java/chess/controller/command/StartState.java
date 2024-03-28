package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public class StartState implements GameState {

    @Override
    public void operate(ChessController chessController, ChessGame chessGame, CommandInfoDto commandInfo) {
        chessController.play(chessGame);
    }

    @Override
    public GameState changeState(final Command command) {
        if (command.isType(Command.START)) {
            throw new IllegalArgumentException("게임 중, 해당 명령어를 입력할 수 없습니다.");
        }
        return command.gameState();
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
