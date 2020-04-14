package chess.domain.piece;

import static chess.domain.coordinates.Direction.DOWN;
import static chess.domain.coordinates.Direction.LEFT;
import static chess.domain.coordinates.Direction.LEFT_DOWN;
import static chess.domain.coordinates.Direction.LEFT_DOWN_DOWN;
import static chess.domain.coordinates.Direction.LEFT_LEFT_DOWN;
import static chess.domain.coordinates.Direction.LEFT_LEFT_UP;
import static chess.domain.coordinates.Direction.LEFT_UP;
import static chess.domain.coordinates.Direction.LEFT_UP_UP;
import static chess.domain.coordinates.Direction.RIGHT;
import static chess.domain.coordinates.Direction.RIGHT_DOWN;
import static chess.domain.coordinates.Direction.RIGHT_DOWN_DOWN;
import static chess.domain.coordinates.Direction.RIGHT_RIGHT_DOWN;
import static chess.domain.coordinates.Direction.RIGHT_RIGHT_UP;
import static chess.domain.coordinates.Direction.RIGHT_UP;
import static chess.domain.coordinates.Direction.RIGHT_UP_UP;
import static chess.domain.coordinates.Direction.UP;
import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import chess.domain.coordinates.Coordinates;

public enum PieceType {
	BLACK_PAWN(BLACK,
			new NormalMoveStrategy(Arrays.asList(DOWN, LEFT_DOWN, RIGHT_DOWN)),
			1.0),
	WHITE_PAWN(WHITE,
			new NormalMoveStrategy(Arrays.asList(UP, LEFT_UP, RIGHT_UP)),
			1.0),
	BLACK_KNIGHT(BLACK,
			new NormalMoveStrategy(
					Arrays.asList(LEFT_LEFT_DOWN, LEFT_LEFT_UP, LEFT_DOWN_DOWN, LEFT_UP_UP, RIGHT_RIGHT_DOWN,
							RIGHT_RIGHT_UP, RIGHT_DOWN_DOWN, RIGHT_UP_UP)),
			2.5),
	WHITE_KNIGHT(WHITE,
			new NormalMoveStrategy(
					Arrays.asList(LEFT_LEFT_DOWN, LEFT_LEFT_UP, LEFT_DOWN_DOWN, LEFT_UP_UP, RIGHT_RIGHT_DOWN,
							RIGHT_RIGHT_UP, RIGHT_DOWN_DOWN, RIGHT_UP_UP)),
			2.5),
	BLACK_BISHOP(BLACK,
			new StretchableMoveStrategy(Arrays.asList(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN)),
			3.0),
	WHITE_BISHOP(WHITE,
			new StretchableMoveStrategy(Arrays.asList(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN)),
			3.0),
	BLACK_ROOK(BLACK,
			new StretchableMoveStrategy(Arrays.asList(UP, DOWN, LEFT, RIGHT)),
			5.0),
	WHITE_ROOK(WHITE,
			new StretchableMoveStrategy(Arrays.asList(UP, DOWN, LEFT, RIGHT)),
			5.0),
	BLACK_QUEEN(BLACK,
			new StretchableMoveStrategy(Arrays.asList(UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN)),
			9.0),
	WHITE_QUEEN(WHITE,
			new StretchableMoveStrategy(Arrays.asList(UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN)),
			9.0),
	BLACK_KING(BLACK,
			new NormalMoveStrategy(Arrays.asList(UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN)),
			0.0),
	WHITE_KING(WHITE,
			new NormalMoveStrategy(Arrays.asList(UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN)),
			0.0);

	private final Color color;
	private final MoveStrategy moveStrategy;
	private final double score;

	PieceType(Color color, MoveStrategy moveStrategy, double score) {
		this.color = color;
		this.moveStrategy = moveStrategy;
		this.score = score;
	}

	public static PieceType of(String pieceName) {
		return Stream.of(values())
				.filter(pieceType -> pieceType.isSameName(pieceName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Piece를 찾을 수 없습니다."));
	}

	public List<Coordinates> findMovableCoordinates(Coordinates from, Coordinates to) {
		return moveStrategy.findMovableCoordinates(from, to);
	}

	public boolean isAlly(PieceType pieceType) {
		return isTeamOf(pieceType.color);
	}

	public boolean isEnemy(PieceType pieceType) {
		return !isTeamOf(pieceType.color);
	}

	public boolean isTeamOf(Color teamColor) {
		return color.equals(teamColor);
	}

	public boolean isKing() {
		return BLACK_KING.equals(this) || WHITE_KING.equals(this);
	}

	public boolean isPawn() {
		return BLACK_PAWN.equals(this) || WHITE_PAWN.equals(this);
	}

	public boolean isSameName(String name) {
		return name().equals(name);
	}

	public Color getColor() {
		return color;
	}

	public double getScore() {
		return score;
	}
}
