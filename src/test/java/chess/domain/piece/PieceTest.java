package chess.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    static Stream<Arguments> isSamePieceTypeArguments() {
        return Stream.of(
                Arguments.arguments(PieceType.PAWN, PieceColor.BLACK, PieceType.PAWN, true),
                Arguments.arguments(PieceType.KING, PieceColor.BLACK, PieceType.PAWN, false)
        );
    }

    @DisplayName("기물의 타입이 주어진 타입과 같은지 판별한다.")
    @ParameterizedTest
    @MethodSource("isSamePieceTypeArguments")
    void isSamePieceType(PieceType pieceType, PieceColor pieceColor, PieceType expectedType, boolean expected) {
        // given
        Piece piece = new Piece(pieceType, pieceColor);

        // when
        boolean result = piece.isType(expectedType);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
