package chess.dto;

import chess.domain.piece.PieceColor;

public record ChessResultDto(double whiteScore, double blackScore, PieceColor winnerColor) {
}
