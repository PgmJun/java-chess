package chess.domain.game;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.position.ChessFile;
import chess.domain.position.Position;

import java.util.List;
import java.util.Map;

public class GameResult {
    private final Map<Position, Piece> board;

    public GameResult(Map<Position, Piece> board) {
        this.board = board;
    }

    public PieceColor winnerTeam() {
        // TODO: 왕이 죽으면 지는 것이 아니라, 점수로만 계산하는 거면 왕만 죽은 경우 동점인데 어떻게 처리할까
        if (whiteScore() > blackScore()) {
            return PieceColor.WHITE;
        }
        return PieceColor.BLACK;
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
            score += calculateScore(getFilePiecesByColor(file, color));
        }
        return score;
    }

    private List<Piece> getFilePiecesByColor(final ChessFile file, final PieceColor color) {
        return board.entrySet().stream()
                .filter(entry ->
                        entry.getKey().isFile(file) && entry.getValue().isColor(color))
                .map(Map.Entry::getValue)
                .toList();
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
