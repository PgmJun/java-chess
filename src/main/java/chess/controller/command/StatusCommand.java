package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public class StatusCommand implements GameCommand {

    @Override
    public void execute(ChessController chessController, ChessGame chessGame, CommandInfoDto commandInfo) {
        chessController.printGameResult(chessGame);
        chessController.printBoardState(chessGame);
        chessController.play(chessGame);
    }

    @Override
    public GameCommand changeCommand(final Command command) {
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
