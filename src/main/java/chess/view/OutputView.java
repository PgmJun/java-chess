package chess.view;

import chess.domain.game.GameResult;
import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.dto.BoardStatusDto;
import chess.dto.PieceInfoDto;
import chess.view.matcher.PieceNameMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class OutputView {

    private static final OutputView INSTANCE = new OutputView();

    private OutputView() {
    }

    public static OutputView getInstance() {
        return INSTANCE;
    }

    public void printGameStartMessage() {
        System.out.println("> 체스 게임을 시작합니다.");
    }

    public void printCommandInfoMessage() {
        StringJoiner readCommandMessageJoiner = new StringJoiner(System.lineSeparator());
        readCommandMessageJoiner.add("> 게임 시작 : start");
        readCommandMessageJoiner.add("> 게임 종료 : end");
        readCommandMessageJoiner.add("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
        System.out.println(readCommandMessageJoiner);
    }

    public void printChessBoard(final BoardStatusDto status) {
        List<List<String>> board = createInitBoard();
        applyBoardStatus(status, board);

        StringJoiner boardJoiner = new StringJoiner(System.lineSeparator());
        for (List<String> line : board) {
            boardJoiner.add(createBoardLine(line));
        }
        System.out.println(boardJoiner + System.lineSeparator());
    }

    private List<List<String>> createInitBoard() {
        List<List<String>> board = new ArrayList<>();

        for (int rank = ChessRank.minIndex(); rank <= ChessRank.maxIndex(); rank++) {
            ArrayList<String> boardLine = new ArrayList<>(Collections.nCopies(ChessFile.maxIndex() + 1, "."));
            board.add(boardLine);
        }
        return board;
    }

    private void applyBoardStatus(final BoardStatusDto status, final List<List<String>> board) {
        for (PieceInfoDto pieceInfoDto : status.pieceInfoDtos()) {
            int rankIndex = ChessRank.maxIndex() - pieceInfoDto.rankIndex();
            int fileIndex = pieceInfoDto.fileIndex();
            board.get(rankIndex).set(fileIndex, PieceNameMatcher.matchByType(pieceInfoDto.type(), pieceInfoDto.color()));
        }
    }

    private StringBuilder createBoardLine(final List<String> line) {
        StringBuilder lineBuilder = new StringBuilder();
        for (final String point : line) {
            lineBuilder.append(point);
        }
        return lineBuilder;
    }

    public void printGameStatus(final GameResult gameResult) {
        StringJoiner gameStatusMessage = new StringJoiner(System.lineSeparator());
        gameStatusMessage.add(String.format("> 검은색: %.1f", gameResult.blackScore()));
        gameStatusMessage.add(String.format("> 흰색: %.1f", gameResult.whiteScore()));
        gameStatusMessage.add(String.format("> 우승 진영: %s", gameResult.winnerTeam()));

        System.out.println(gameStatusMessage);
    }
}
