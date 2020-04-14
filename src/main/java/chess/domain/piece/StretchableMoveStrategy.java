package chess.domain.piece;

import java.util.ArrayList;
import java.util.List;

import chess.domain.coordinates.Coordinates;
import chess.domain.coordinates.Direction;
import chess.exception.PieceMoveFailedException;

public class StretchableMoveStrategy extends AbstractMoveStrategy {
	public StretchableMoveStrategy(List<Direction> movableDirections) {
		super(movableDirections);
	}

	@Override
	public List<Coordinates> findMovableCoordinates(Coordinates from, Coordinates to) {
		Direction direction = Direction.of(from, to);
		validateDirection(direction);

		List<Coordinates> movableCoordinates = new ArrayList<>();
		while (!from.equals(to)) {
			from = from.next(direction);
			movableCoordinates.add(from);
		}
		return movableCoordinates;
	}

	private void validateDirection(Direction direction) {
		if (!movableDirections.contains(direction)) {
			throw new PieceMoveFailedException("움직일 수 없는 방향입니다.");
		}
	}
}
