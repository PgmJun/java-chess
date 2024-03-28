package chess.controller.command;

import chess.controller.ChessGame;
import chess.domain.board.ChessBoard;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public class StartState implements GameState {

    @Override
    public void operate(ChessGame chessGame, ChessBoard chessBoard, CommandInfoDto commandInfo) {
        chessGame.play(chessBoard);
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
