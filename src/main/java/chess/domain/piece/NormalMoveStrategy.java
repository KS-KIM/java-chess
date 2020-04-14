package chess.domain.piece;

import java.util.Collections;
import java.util.List;

import chess.domain.coordinates.Coordinates;
import chess.domain.coordinates.Direction;
import chess.exception.PieceMoveFailedException;

public class NormalMoveStrategy extends AbstractMoveStrategy {
	public NormalMoveStrategy(List<Direction> movableDirections) {
		super(movableDirections);
	}

	@Override
	public List<Coordinates> findMovableCoordinates(Coordinates from, Coordinates to) {
		Direction direction = Direction.of(from, to);
		from = from.next(direction);
		if (!movableDirections.contains(direction) || !from.equals(to)) {
			throw new PieceMoveFailedException("이동할 수 없는 위치입니다.");
		}
		return Collections.singletonList(from);
	}
}
