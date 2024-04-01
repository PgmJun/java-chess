package chess;

import chess.controller.ChessController;
import chess.entity.game.repository.GameDAO;
import chess.entity.piece.repository.PieceDAO;
import chess.service.GameService;
import chess.view.InputView;
import chess.view.OutputView;

import java.sql.SQLException;

public class ChessApplication {
    public static void main(String[] args) throws SQLException {
        final ChessController chessController = new ChessController(
                InputView.getInstance(),
                OutputView.getInstance(),
                new GameService(new GameDAO(), new PieceDAO())
        );
        chessController.start();
    }
}
