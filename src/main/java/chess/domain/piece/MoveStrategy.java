package chess.domain.piece;

import java.util.List;

import chess.domain.coordinates.Coordinates;

public interface MoveStrategy {
	List<Coordinates> findMovableCoordinates(Coordinates from, Coordinates to);
}
