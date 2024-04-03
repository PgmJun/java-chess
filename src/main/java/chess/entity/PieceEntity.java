package chess.entity;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

public class PieceEntity {
    private Long id;
    private Long gameId;
    private String type;
    private String color;
    private String rank;
    private String file;

    public PieceEntity(Long id, Long gameId, String type, String color, String rank, String file) {
        this.id = id;
        this.gameId = gameId;
        this.type = type;
        this.color = color;
        this.rank = rank;
        this.file = file;
    }

    public PieceEntity(final Long gameId, final Position position, final Piece piece) {
        this.gameId = gameId;
        this.type = piece.type().name();
        this.color = piece.color().name();
        this.rank = position.rank().name();
        this.file = position.file().name();
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getRank() {
        return rank;
    }

    public String getFile() {
        return file;
    }
}
