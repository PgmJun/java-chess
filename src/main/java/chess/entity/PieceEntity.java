package chess.entity;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceColor;
import chess.domain.piece.PieceType;
import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.domain.position.Position;

public class PieceEntity {
    private Long id;
    private Long gameId;
    private PieceType type;
    private PieceColor color;
    private ChessRank rank;
    private ChessFile file;

    public PieceEntity(Long id, Long gameId, PieceType type, PieceColor color, ChessRank rank, ChessFile file) {
        this.id = id;
        this.gameId = gameId;
        this.type = type;
        this.color = color;
        this.rank = rank;
        this.file = file;
    }

    public PieceEntity(final Long gameId, final Position position, final Piece piece) {
        this.gameId = gameId;
        this.type = piece.type();
        this.color = piece.color();
        this.rank = position.rank();
        this.file = position.file();
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public ChessRank getRank() {
        return rank;
    }

    public ChessFile getFile() {
        return file;
    }
}
