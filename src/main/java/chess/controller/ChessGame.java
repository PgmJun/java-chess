package chess.controller;

import chess.controller.command.GameState;
import chess.controller.command.InitState;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.position.Position;
import chess.dto.CommandInfoDto;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.matcher.ChessFileMatcher;
import chess.view.matcher.ChessRankMatcher;

public class ChessGame {
    private final InputView inputView;
    private final OutputView outputView;
    private GameState gameState;

    public ChessGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameState = new InitState();
    }

    public void start() {
        outputView.printGameStartMessage();
        ChessBoard chessBoard = new ChessBoard(ChessBoardGenerator.getInstance());
        playTurn(chessBoard);
    }

    public void play(final ChessBoard chessBoard) {
        outputView.printChessBoard(chessBoard.status());
        while (!gameState.isGameEnd() && chessBoard.isNotKingDead()) {
            playTurn(chessBoard);
        }
    }

    private void playTurn(ChessBoard chessBoard) {
        CommandInfoDto commandInfoDto = inputView.readCommand();

        this.gameState = gameState.changeState(commandInfoDto.command());
        gameState.operate(this, chessBoard, commandInfoDto);
    }

    public void move(final ChessBoard chessBoard, final CommandInfoDto commandInfo) {
        chessBoard.move(
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
