package chess.controller;

import chess.controller.command.GameCommand;
import chess.controller.command.InitCommand;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.position.Position;
import chess.dto.CommandInfoDto;
import chess.view.InputView;
import chess.view.OutputView;

;

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
        ChessGame game = new ChessGame(new ChessBoard(ChessBoardGenerator.getInstance()), Turn.firstTurn());
        play(game);
        printGameResult(game);
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
