package chess.controller;

import chess.controller.command.GameCommand;
import chess.controller.command.InitCommand;
import chess.domain.game.ChessGame;
import chess.domain.position.Position;
import chess.dto.CommandInfoDto;
import chess.service.GameService;
import chess.view.GameOption;
import chess.view.InputView;
import chess.view.OutputView;

import java.sql.SQLException;
import java.util.function.Supplier;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;
    private GameCommand gameCommand;

    public ChessController(final InputView inputView, final OutputView outputView, final GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
        this.gameCommand = new InitCommand();
    }

    public void start() throws SQLException {
        outputView.printGameStartMessage();
        ChessGame game = retry(this::selectGame);

        outputView.printCommandInfoMessage();
        retry(() -> play(game));
        printGameResult(game);
        if (game.isGameEnd()) {
            gameService.deleteLatestGame(game.id());
        }
    }

    private ChessGame selectGame() {
        GameOption gameOption = inputView.readGameOption();
        if (gameOption == GameOption.NEW) {
            return gameService.createNewGame();
        }
        if (gameOption == GameOption.CONTINUE) {
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
        Long pieceId = chessGame.findPieceIdAtPosition(source);
        chessGame.move(source, target);

        gameService.updateGame(chessGame.id(), chessGame.turn(), pieceId, target);
    }

    private <T> T retry(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            outputView.printErrorMessage(exception.getMessage());
            return retry(supplier);
        }
    }

    private void retry(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception exception) {
            outputView.printErrorMessage(exception.getMessage());
            retry(runnable);
        }
    }
}
