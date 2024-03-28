package chess.controller.command;

import chess.controller.ChessGame;
import chess.domain.board.ChessBoard;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public interface GameState {

    void operate(final ChessGame chessGame, final ChessBoard chessBoard, final CommandInfoDto commandInfo);

    GameState changeState(Command command);

    boolean isGameEnd();
}
