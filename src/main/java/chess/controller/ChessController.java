package chess.controller;

import chess.controller.command.GameCommand;
import chess.controller.command.InitCommand;
import chess.domain.game.ChessGame;
import chess.domain.position.Position;
import chess.dto.CommandInfoDto;
import chess.repository.game.GameDAO;
import chess.repository.piece.PieceDAO;
import chess.service.GameService;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.List;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;
    private GameCommand gameCommand;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameCommand = new InitCommand();
    }

    public void start() {
        outputView.printGameStartMessage();
        ChessGame game = selectGame();

        outputView.printCommandInfoMessage();
        play(game);
        printGameResult(game);
    }

    private ChessGame selectGame() {
        GameService gameService = new GameService(new GameDAO(), new PieceDAO());
        List<String> gameCommand = inputView.readGameCommand();
        if (gameCommand.get(1).equals("new")) {
            return gameService.createGame();
        }
        if (gameCommand.get(1).equals("continue")) {
            return gameService.loadGame();
        }
        throw new IllegalArgumentException("존재하지 않는 명령어입니다.");
    }

    public void play(final ChessGame chessGame) {
        if (!gameCommand.isEnd() && !chessGame.isGameEnd()) {
            CommandInfoDto commandInfoDto = inputView.readCommand();

            this.gameCommand = gameCommand.changeCommand(commandInfoDto.command());
            gameCommand.execute(this, chessGame, commandInfoDto);
        }
    }

    public void printBoardState(final ChessGame chessGame) {
        outputView.printChessBoard(chessGame.boardState());
    }

    public void printGameResult(final ChessGame chessGame) {
        OutputView outputView = OutputView.getInstance();
        outputView.printGameStatus(chessGame.result());
    }

    public void move(final ChessGame chessGame, final Position source, final Position target) {
        chessGame.move(source, target);
    }
}
