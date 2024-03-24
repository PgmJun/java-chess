package chess.domain.piece.strategy;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class KnightMovementStrategyTest {

    static Stream<Arguments> canKnightMoveL_ShapeDirectionArguments() {
        return Stream.of(
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.C, ChessRank.TWO)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.E, ChessRank.TWO)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.C, ChessRank.SIX)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.E, ChessRank.SIX)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.F, ChessRank.THREE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.F, ChessRank.FIVE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.B, ChessRank.FIVE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.B, ChessRank.THREE))
        );
    }

    static Stream<Arguments> cannotKnightMoveExceptL_ShapeDirectionArguments() {
        return Stream.of(
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.G, ChessRank.FIVE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.G, ChessRank.SEVEN)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.C, ChessRank.FIVE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.C, ChessRank.SEVEN)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.A, ChessRank.THREE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.A, ChessRank.ONE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.E, ChessRank.THREE)),
                Arguments.arguments(Position.of(ChessFile.D, ChessRank.FOUR), Position.of(ChessFile.E, ChessRank.ONE))
        );
    }

    @DisplayName("나이트는 모든 방향으로 L모양으로만 움직일 수 있다.")
    @ParameterizedTest
    @MethodSource("canKnightMoveL_ShapeDirectionArguments")
    void canKnightMoveL_ShapeDirection(Position source, Position target) {
        // given
        Piece knight = new Piece(PieceType.BLACK_KNIGHT);

        // when
        boolean result = knight.isInMovableRange(source, target);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("나이트는 모든 방향으로 L모양 외에는 움직일 수 없다.")
    @ParameterizedTest
    @MethodSource("cannotKnightMoveExceptL_ShapeDirectionArguments")
    void cannotKnightMoveExceptL_ShapeDirection(Position source, Position target) {
        // given
        Piece knight = new Piece(PieceType.BLACK_KNIGHT);

        // when
        boolean result = knight.isInMovableRange(source, target);

        // then
        assertThat(result).isFalse();
    }
}
