package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;
import chess.view.OutputView;

public class MoveState implements GameState {

    @Override
    public void operate(final ChessController chessController, final ChessGame chessGame, final CommandInfoDto commandInfo) {
        OutputView outputView = OutputView.getInstance();

        chessController.move(chessGame, commandInfo);
        outputView.printChessBoard(chessGame.boardState());
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
