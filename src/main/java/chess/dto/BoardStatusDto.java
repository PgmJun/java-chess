package chess.dto;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardStatusDto(List<PieceInfoDto> pieceInfoDtos) {
    public static BoardStatusDto from(final Map<Position, Piece> board) {
        List<PieceInfoDto> pieceInfoDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            pieceInfoDtos.add(PieceInfoDto.of(entry.getKey(), entry.getValue()));
        }
        return new BoardStatusDto(pieceInfoDtos);
    }
}
