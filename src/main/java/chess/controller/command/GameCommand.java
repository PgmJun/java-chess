package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.dto.CommandInfoDto;
import chess.view.Command;

import java.sql.SQLException;

public interface GameCommand {

    void execute(final ChessController chessController, final ChessGame chessGame, final CommandInfoDto commandInfo) throws SQLException;

    GameCommand changeCommand(Command command);

    boolean isEnd();
}
