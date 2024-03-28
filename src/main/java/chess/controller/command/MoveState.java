package chess.controller.command;

import chess.controller.ChessGame;
import chess.domain.board.ChessBoard;
import chess.dto.CommandInfoDto;
import chess.view.Command;
import chess.view.OutputView;

public class MoveState implements GameState {

    @Override
    public void operate(final ChessGame chessGame, final ChessBoard chessBoard, final CommandInfoDto commandInfo) {
        OutputView outputView = OutputView.getInstance();

        chessGame.move(chessBoard, commandInfo);
        outputView.printChessBoard(chessBoard.status());
    }

    @Override
    public GameState changeState(final Command command) {
        if (command.isType(Command.START)) {
            throw new IllegalArgumentException("게임 중, 해당 명령어를 입력할 수 없습니다.");
        }
        return command.gameState();
    }

    @Override
    public boolean isGameEnd() {
        return false;
    }
}
