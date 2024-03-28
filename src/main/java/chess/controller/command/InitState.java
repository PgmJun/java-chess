package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public class InitState implements GameState {
    @Override
    public void operate(ChessController chessController, ChessGame chessGame, CommandInfoDto commandInfo) {
        return;
    }

    @Override
    public GameState changeState(final Command command) {
        if (command.isType(Command.START) || command.isType(Command.END)) {
            return command.gameState();
        }
        throw new IllegalArgumentException("게임 시작 전, 해당 명령어를 입력할 수 없습니다.");
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
