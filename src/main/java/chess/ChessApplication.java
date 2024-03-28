package chess;

import chess.controller.ChessController;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {
    public static void main(String[] args) {
        final ChessController chessController = new ChessController(InputView.getInstance(), OutputView.getInstance());
        chessController.start();
    }
}
