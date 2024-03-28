package chess.controller;

import chess.controller.command.GameState;
import chess.controller.command.InitState;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.game.ChessGame;
import chess.domain.game.Turn;
import chess.domain.position.Position;
import chess.dto.CommandInfoDto;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.matcher.ChessFileMatcher;
import chess.view.matcher.ChessRankMatcher;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;
    private GameState gameState;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameState = new InitState();
    }

    public void start() {
        outputView.printGameStartMessage();
        ChessGame game = new ChessGame(new ChessBoard(ChessBoardGenerator.getInstance()), Turn.firstTurn());
        playTurn(game);
    }

    private void playTurn(final ChessGame chessGame) {
        CommandInfoDto commandInfoDto = inputView.readCommand();

        this.gameState = gameState.changeState(commandInfoDto.command());
        gameState.operate(this, chessGame, commandInfoDto);
    }

    public void play(final ChessGame chessGame) {
        outputView.printChessBoard(chessGame.boardState());
        while (!gameState.isEnd() && !chessGame.isGameEnd()) {
            playTurn(chessGame);
        }
    }

    public void move(final ChessGame chessGame, final CommandInfoDto commandInfo) {
        chessGame.move(
                extractPosition(commandInfo.options().get(0)),
                extractPosition(commandInfo.options().get(1))
        );
    }

    private Position extractPosition(final String positionText) {
        String file = String.valueOf(positionText.charAt(0));
        String rank = String.valueOf(positionText.charAt(1));

        return Position.of(ChessFileMatcher.matchByText(file), ChessRankMatcher.matchByText(rank));
    }
}
