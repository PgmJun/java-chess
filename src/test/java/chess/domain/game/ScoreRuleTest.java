package chess.domain.game;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreRuleTest {

    @DisplayName("ScoreState 가 DEFAULT 이면 점수가 그대로이다.")
    @Test
    void pawnDefaultScore() {
        // given
        Piece piece = new Piece(PieceType.BLACK_PAWN);

        // when
        double score = piece.calculateScore(ScoreRule.DEFAULT);

        // then
        assertThat(score).isEqualTo(1);
    }

    @DisplayName("ScoreState 가 HALF 이면 점수가 절반이 된다.")
    @Test
    void pawnHalfScore() {
        // given
        Piece piece = new Piece(PieceType.BLACK_PAWN);

        // when
        double score = piece.calculateScore(ScoreRule.HALF);

        // then
        assertThat(score).isEqualTo(0.5);
    }
}
