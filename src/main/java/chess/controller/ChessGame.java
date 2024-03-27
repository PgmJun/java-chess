package chess.controller;

import chess.domain.Turn;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.position.Position;
import chess.dto.BoardStatusDto;
import chess.dto.CommandInfoDto;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.matcher.ChessFileMatcher;
import chess.view.matcher.ChessRankMatcher;

public class ChessGame {
    private final InputView inputView;
    private final OutputView outputView;

    public ChessGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        outputView.printGameStartMessage();

        Turn turn = Turn.firstTurn();
        CommandInfoDto commandInfoDto = inputView.readCommand();
        Command command = commandInfoDto.command();
        ChessBoard chessBoard = new ChessBoard(ChessBoardGenerator.getInstance());

        if (command.isNotType(Command.START)) {
            throw new IllegalArgumentException("아직 게임이 시작되지 않았습니다.");
        }
        while (command.isNotType(Command.END) && chessBoard.isNotKingDead()) {
            BoardStatusDto boardStatusDto = chessBoard.status();
            outputView.printChessBoard(boardStatusDto);

            commandInfoDto = inputView.readCommand();
            command = commandInfoDto.command();
            if (command.isType(Command.START) || command.isType(Command.STATUS)) {
                throw new IllegalArgumentException(String.format("게임 도중 %s 명령어를 입력할 수 없습니다.", command));
            }
            if (command.isType(Command.MOVE)) {
                chessBoard.move(
                        extractPosition(commandInfoDto.options().get(0)),
                        extractPosition(commandInfoDto.options().get(1)),
                        turn
                );
            }
        }
        while (command.isNotType(Command.END)) {
            outputView.printGameResultMessage();
            command = inputView.readCommand().command();
            if (command.isType(Command.STATUS)) {
                outputView.printGameStatus(chessBoard.result());
            }
        }
    }

    private Position extractPosition(final String positionText) {
        String file = String.valueOf(positionText.charAt(0));
        String rank = String.valueOf(positionText.charAt(1));

        return Position.of(ChessFileMatcher.matchByText(file), ChessRankMatcher.matchByText(rank));
    }
}
