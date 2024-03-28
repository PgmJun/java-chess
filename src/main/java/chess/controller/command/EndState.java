package chess.controller.command;

import chess.controller.ChessGame;
import chess.domain.board.ChessBoard;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public class EndState implements GameState {

    @Override
    public void operate(ChessGame chessGame, ChessBoard chessBoard, CommandInfoDto commandInfo) {
        return;
    }

    @Override
    public GameState changeState(final Command command) {
        return this;
    }

    @Override
    public boolean isGameEnd() {
        return true;
    }
}
