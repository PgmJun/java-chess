package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.Map;

public interface BoardGenerator {
    //TODO: Board를 return하도록 변경
    Map<Position, Piece> generate();
}
