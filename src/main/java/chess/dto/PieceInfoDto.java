package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;

public record PieceInfoDto(int fileIndex, int rankIndex, PieceType type) {
    public static PieceInfoDto of(final Position position, final Piece piece) {
        return new PieceInfoDto(position.indexOfFile(), position.indexOfRank(), piece.type());
    }
}
