package chess.domain.game;

import chess.domain.board.BoardGeneratorStub;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ChessGameTest {

    @DisplayName("같은 색상의 기물을 연속해서 움직일 수 없다.")
    @Test
    void isNotTurn() {
        // given
        BoardGeneratorStub generatorStub = new BoardGeneratorStub();
        HashMap<Position, Piece> board = new HashMap<>();
        board.put(Position.B2, new Piece(PieceType.WHITE_PAWN));
        board.put(Position.A1, new Piece(PieceType.WHITE_ROOK));
        generatorStub.setBoard(board);

        ChessGame game = new ChessGame(new ChessBoard(generatorStub), Turn.firstTurn());

        // when
        game.move(Position.B2, Position.B3);

        // then
        assertThatThrownBy(() -> game.move(Position.A1, Position.H1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("WHITE 색의 차례가 아닙니다.");
    }

    @DisplayName("킹이 죽으면 게임이 끝난다.")
    @Test
    void isGameEndWhenKingDead() {
        // given
        BoardGeneratorStub generatorStub = new BoardGeneratorStub();
        HashMap<Position, Piece> board = new HashMap<>();
        board.put(Position.B2, new Piece(PieceType.WHITE_PAWN));
        board.put(Position.A1, new Piece(PieceType.WHITE_KING));
        generatorStub.setBoard(board);

        ChessGame game = new ChessGame(new ChessBoard(generatorStub), Turn.firstTurn());

        // when & then
        assertThat(game.isGameEnd()).isTrue();
    }
}
