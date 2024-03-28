package chess.domain.game;

import chess.domain.board.BoardGeneratorStub;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResultTest {

    @DisplayName("점수가 높은 팀이 승리한다.")
    @ParameterizedTest
    @MethodSource("calculateWinnerTeamArguments")
    void calculateWinnerTeam(PieceType type1, PieceType type2, PieceColor expected) {
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.A2, new Piece(type1));
        board.put(Position.A3, new Piece(type2));

        GameResult gameResult = new GameResult(board);
        assertThat(gameResult.winnerTeam()).isEqualTo(expected);
    }

    static Stream<Arguments> calculateWinnerTeamArguments() {
        return Stream.of(
                Arguments.arguments(PieceType.BLACK_KING, PieceType.BLACK_QUEEN, PieceColor.BLACK),
                Arguments.arguments(PieceType.WHITE_KING, PieceType.WHITE_QUEEN, PieceColor.WHITE)
        );
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
