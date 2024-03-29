package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardGenerator implements BoardGenerator {
    private static final ChessBoardGenerator INSTANCE = new ChessBoardGenerator();

    private ChessBoardGenerator() {
    }

    public static ChessBoardGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Map<Position, Piece> generate() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(createBlackFirstLine());
        pieces.putAll(createBlackSecondLine());

        pieces.putAll(createWhiteSecondLine());
        pieces.putAll(createWhiteFirstLine());
        return pieces;
    }

    private Map<Position, Piece> createBlackFirstLine() {
        Map<Position, Piece> line = new HashMap<>();
        line.put(Position.A8, new Piece(PieceType.ROOK, PieceColor.BLACK));
        line.put(Position.B8, new Piece(PieceType.KNIGHT, PieceColor.BLACK));
        line.put(Position.C8, new Piece(PieceType.BISHOP, PieceColor.BLACK));
        line.put(Position.D8, new Piece(PieceType.QUEEN, PieceColor.BLACK));
        line.put(Position.E8, new Piece(PieceType.KING, PieceColor.BLACK));
        line.put(Position.F8, new Piece(PieceType.BISHOP, PieceColor.BLACK));
        line.put(Position.G8, new Piece(PieceType.KNIGHT, PieceColor.BLACK));
        line.put(Position.H8, new Piece(PieceType.ROOK, PieceColor.BLACK));

        return line;
    }

    private Map<Position, Piece> createBlackSecondLine() {
        Map<Position, Piece> line = new HashMap<>();
        line.put(Position.A7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        line.put(Position.B7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        line.put(Position.C7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        line.put(Position.D7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        line.put(Position.E7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        line.put(Position.F7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        line.put(Position.G7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        line.put(Position.H7, new Piece(PieceType.PAWN, PieceColor.BLACK));
        return line;
    }

    private Map<Position, Piece> createWhiteFirstLine() {
        Map<Position, Piece> line = new HashMap<>();
        line.put(Position.A1, new Piece(PieceType.ROOK, PieceColor.WHITE));
        line.put(Position.B1, new Piece(PieceType.KNIGHT, PieceColor.WHITE));
        line.put(Position.C1, new Piece(PieceType.BISHOP, PieceColor.WHITE));
        line.put(Position.D1, new Piece(PieceType.QUEEN, PieceColor.WHITE));
        line.put(Position.E1, new Piece(PieceType.KING, PieceColor.WHITE));
        line.put(Position.F1, new Piece(PieceType.BISHOP, PieceColor.WHITE));
        line.put(Position.G1, new Piece(PieceType.KNIGHT, PieceColor.WHITE));
        line.put(Position.H1, new Piece(PieceType.ROOK, PieceColor.WHITE));

        return line;
    }

    private Map<Position, Piece> createWhiteSecondLine() {
        Map<Position, Piece> line = new HashMap<>();
        line.put(Position.A2, new Piece(PieceType.PAWN, PieceColor.WHITE));
        line.put(Position.B2, new Piece(PieceType.PAWN, PieceColor.WHITE));
        line.put(Position.C2, new Piece(PieceType.PAWN, PieceColor.WHITE));
        line.put(Position.D2, new Piece(PieceType.PAWN, PieceColor.WHITE));
        line.put(Position.E2, new Piece(PieceType.PAWN, PieceColor.WHITE));
        line.put(Position.F2, new Piece(PieceType.PAWN, PieceColor.WHITE));
        line.put(Position.G2, new Piece(PieceType.PAWN, PieceColor.WHITE));
        line.put(Position.H2, new Piece(PieceType.PAWN, PieceColor.WHITE));

        return line;
    }
}
