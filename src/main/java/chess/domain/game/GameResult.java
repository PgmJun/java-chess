package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.position.ChessFile;

import java.util.List;

public class GameResult {
    private final ChessBoard board;

    public GameResult(ChessBoard board) {
        this.board = board;
    }

    public Winner winnerTeam() {
        return Winner.calculateWinner(board.findAliveKingsColor());
    }

    public double whiteScore() {
        return calculateScore(PieceColor.WHITE);
    }

    public double blackScore() {
        return calculateScore(PieceColor.BLACK);
    }

    private double calculateScore(final PieceColor color) {
        double score = 0;

        for (ChessFile file : ChessFile.values()) {
            score += calculateScore(board.getPiecesOfFileByColor(file, color));
        }
        return score;
    }


    private double calculateScore(final List<Piece> filePieces) {
        final long pawnCount = filePieces.stream()
                .filter(Piece::isPawn)
                .count();

        double score = 0;
        for (Piece piece : filePieces) {
            score += piece.calculateScore(getScoreRule(piece, pawnCount));
        }
        return score;
    }

    private ScoreRule getScoreRule(Piece piece, long pawnCount) {
        if (piece.isPawn() && pawnCount > 1) {
            return ScoreRule.HALF;
        }
        return ScoreRule.DEFAULT;
    }
}
