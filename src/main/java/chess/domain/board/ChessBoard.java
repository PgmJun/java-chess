package chess.domain.board;

import chess.domain.Direction;
import chess.domain.Turn;
import chess.domain.game.GameResult;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import chess.dto.BoardStatusDto;

import java.util.HashMap;
import java.util.Map;

// TODO: 책임 분리
public class ChessBoard {
    private final Map<Position, Piece> board;

    public ChessBoard(final BoardGenerator boardGenerator) {
        this(boardGenerator.generate());
    }

    private ChessBoard(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public BoardStatusDto status() {
        return BoardStatusDto.from(board);
    }

    public void move(final Position source, final Position target, final Turn turn) {
        validate(source, target, turn);

        Piece sourcePiece = board.get(source);
        board.put(target, sourcePiece);
        board.remove(source);

        turn.next();
    }

    private void validate(final Position source, final Position target, final Turn turn) {
        validatePosition(source, target);
        turn.validateTurnState(board.get(source).color());
        validateTarget(source, target);
        validateMovement(source, target);
        validatePath(source, target);
    }

    private void validatePosition(final Position source, final Position target) {
        if (!board.containsKey(source) || source == target) {
            throw new IllegalArgumentException("입력하신 이동 위치가 올바르지 않습니다.");
        }
    }

    private void validateTarget(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        Piece targetPiece = board.get(target);
        if (board.containsKey(target) && sourcePiece.isColor(targetPiece.color())) {
            throw new IllegalArgumentException("이동할 수 없는 target입니다.");
        }
    }

    private void validateMovement(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        Piece targetPiece = board.get(target);
        if (!sourcePiece.isInMovableRange(source, target)) {
            throw new IllegalArgumentException("기물이 이동할 수 없는 방식입니다.");
        }
        if (PieceType.isPawn(sourcePiece)) {
            if (Direction.isDiagonal(source, target) && !board.containsKey(target)) {
                throw new IllegalArgumentException("폰은 상대 기물이 존재할 때만 대각선 이동이 가능합니다.");
            }
            if (Direction.isVertical(source, target) && board.containsKey(target) && !targetPiece.isColor(sourcePiece.color())) {
                throw new IllegalArgumentException("폰은 대각선으로만 공격할 수 있습니다.");
            }
        }
    }

    private void validatePath(final Position source, final Position target) {
        Piece sourcePiece = board.get(source);
        if (!PieceType.isKnight(sourcePiece)) {
            source.findBetween(target).stream()
                    .filter(board::containsKey)
                    .findAny()
                    .ifPresent(position -> {
                        throw new IllegalArgumentException("이동하고자 하는 경로 사이에 기물이 존재합니다.");
                    });
        }
    }

    public boolean isNotKingDead() {
        long kingCount = board.values().stream()
                .filter(Piece::isKing)
                .count();

        return kingCount == 2;
    }

    public GameResult result() {
        return new GameResult(board);
    }
}
