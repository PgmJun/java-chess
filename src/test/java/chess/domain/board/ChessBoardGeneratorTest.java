package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ChessBoardGeneratorTest {

    @DisplayName("컬러를 입력하면 해당 컬러의 피스들을 만들어준다")
    @Test
    void generatePiecesByColor() {
        // when
        ChessBoardGenerator chessBoardGenerator = ChessBoardGenerator.getInstance();
        Map<Position, Piece> pieces = chessBoardGenerator.generate();

        // then
        assertThat(pieces).hasSize(32);

        assertAll(
                () -> assertThat(countPiece(pieces, PieceType.PAWN, PieceColor.WHITE)).isEqualTo(8),
                () -> assertThat(countPiece(pieces, PieceType.PAWN, PieceColor.BLACK)).isEqualTo(8),
                () -> assertThat(countPiece(pieces, PieceType.ROOK, PieceColor.WHITE)).isEqualTo(2),
                () -> assertThat(countPiece(pieces, PieceType.ROOK, PieceColor.BLACK)).isEqualTo(2),
                () -> assertThat(countPiece(pieces, PieceType.KNIGHT, PieceColor.WHITE)).isEqualTo(2),
                () -> assertThat(countPiece(pieces, PieceType.KNIGHT, PieceColor.BLACK)).isEqualTo(2),
                () -> assertThat(countPiece(pieces, PieceType.BISHOP, PieceColor.WHITE)).isEqualTo(2),
                () -> assertThat(countPiece(pieces, PieceType.BISHOP, PieceColor.BLACK)).isEqualTo(2),
                () -> assertThat(countPiece(pieces, PieceType.KING, PieceColor.WHITE)).isEqualTo(1),
                () -> assertThat(countPiece(pieces, PieceType.KING, PieceColor.BLACK)).isEqualTo(1),
                () -> assertThat(countPiece(pieces, PieceType.QUEEN, PieceColor.WHITE)).isEqualTo(1),
                () -> assertThat(countPiece(pieces, PieceType.QUEEN, PieceColor.BLACK)).isEqualTo(1)
        );
    }

    private int countPiece(Map<Position, Piece> pieces, PieceType type, PieceColor color) {
        return (int) pieces.values().stream()
                .filter(piece -> piece.isType(type) && piece.isColor(color))
                .count();
    }
}
