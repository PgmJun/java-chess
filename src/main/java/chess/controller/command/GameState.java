package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;

public interface GameState {

    void operate(final ChessController chessController, final ChessGame chessGame, final CommandInfoDto commandInfo);

    GameState changeState(Command command);

    boolean isEnd();
}
