package chess.controller;

import chess.domain.Turn;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardGenerator;
import chess.domain.position.Position;
import chess.dto.BoardStatusDto;
import chess.dto.CommandInfoDto;
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
        ChessBoard chessBoard = new ChessBoard(ChessBoardGenerator.getInstance());

        if (!commandInfoDto.command().isStart()) {
            throw new IllegalArgumentException("아직 게임이 시작되지 않았습니다.");
        }
        while (!commandInfoDto.command().isEnd()) {
            BoardStatusDto boardStatusDto = chessBoard.status();
            outputView.printChessBoard(boardStatusDto);

            commandInfoDto = inputView.readCommand();
            if (commandInfoDto.command().isStart()) {
                throw new IllegalArgumentException("게임 도중 start 명령어를 입력할 수 없습니다.");
            }
            if (commandInfoDto.command().isMove()) {
                chessBoard.move(extractPosition(commandInfoDto.source()), extractPosition(commandInfoDto.target()), turn);
            }
        }
        // 게임 종료
    }

    private Position extractPosition(final String positionText) {
        String file = String.valueOf(positionText.charAt(0));
        String rank = String.valueOf(positionText.charAt(1));

        return Position.of(ChessFileMatcher.matchByText(file), ChessRankMatcher.matchByText(rank));
    }
}
