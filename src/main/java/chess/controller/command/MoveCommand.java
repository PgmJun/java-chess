package chess.controller.command;

import chess.controller.ChessController;
import chess.domain.game.ChessGame;
import chess.domain.position.Position;
import chess.dto.CommandInfoDto;
import chess.view.Command;
import chess.view.matcher.ChessFileMatcher;
import chess.view.matcher.ChessRankMatcher;

public class MoveCommand implements GameCommand {

    @Override
    public void execute(final ChessController chessController, final ChessGame chessGame, final CommandInfoDto commandInfo) {
        chessController.move(chessGame,
                extractPosition(commandInfo.options().get(0)),
                extractPosition(commandInfo.options().get(1))
        );
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

    private Position extractPosition(final String positionText) {
        String file = String.valueOf(positionText.charAt(0));
        String rank = String.valueOf(positionText.charAt(1));

        return Position.of(ChessFileMatcher.matchByText(file), ChessRankMatcher.matchByText(rank));
    }
}
