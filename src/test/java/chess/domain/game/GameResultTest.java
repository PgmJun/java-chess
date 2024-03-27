package chess.domain.game;

import chess.domain.board.BoardGeneratorStub;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResultTest {

    @DisplayName("승리한 팀을 알아낸다.")
    @Test
    void calculateWinnerTeam() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.A2, new Piece(PieceType.BLACK_KING));
        board.put(Position.A3, new Piece(PieceType.BLACK_QUEEN));

        GameResult gameResult = new GameResult(board);
        assertThat(gameResult.winnerTeam()).isEqualTo(PieceColor.BLACK);
    }

    @DisplayName("각 팀의 점수를 계산한다.")
    @Test
    void calculateScore() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.A2, new Piece(PieceType.BLACK_KING));
        board.put(Position.A3, new Piece(PieceType.BLACK_QUEEN));

        GameResult gameResult = new GameResult(board);
        assertThat(gameResult.winnerTeam()).isEqualTo(PieceColor.BLACK);
        assertThat(gameResult.whiteScore()).isEqualTo(0);
        assertThat(gameResult.blackScore()).isEqualTo(9);
    }

    @DisplayName("같은 File에 폰이 2개 이상 존재한다면 개당 0.5점으로 계산한다.")
    @Test
    void calculatePawnsOnFileScore() {
        // given
        BoardGeneratorStub boardGenerator = new BoardGeneratorStub();
        HashMap<Position, Piece> board = new HashMap<>();
        board.put(Position.A2, new Piece(PieceType.BLACK_PAWN));
        board.put(Position.A3, new Piece(PieceType.BLACK_PAWN));
        GameResult gameResult = new GameResult(board);

        // when & then
        assertThat(gameResult.blackScore()).isEqualTo(1.0);
    }
}
