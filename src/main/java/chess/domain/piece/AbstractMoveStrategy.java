package chess.domain.piece;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import chess.domain.coordinates.Coordinates;
import chess.domain.coordinates.Direction;

public abstract class AbstractMoveStrategy implements MoveStrategy {
	protected final Set<Direction> movableDirections;

	public AbstractMoveStrategy(List<Direction> movableDirections) {
		validateMovableDirections(movableDirections);
		this.movableDirections = new HashSet<>(movableDirections);
	}

	// empty list는 빈 Cell의 상태를 표현하기 위해 허용
	private void validateMovableDirections(List<Direction> movableDirections) {
		if (Objects.isNull(movableDirections)) {
			throw new IllegalArgumentException("이동할 수 있는 방향을 입력해야 합니다.");
		}
	}

	@Override
	public abstract List<Coordinates> findMovableCoordinates(Coordinates from, Coordinates to);
}
