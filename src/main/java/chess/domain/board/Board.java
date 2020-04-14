package chess.domain.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import chess.domain.coordinates.Coordinates;
import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import chess.exception.PieceMoveFailedException;

public class Board {
	private final Map<Coordinates, PieceType> pieces;

	public Board(Map<Coordinates, PieceType> pieces) {
		validateBoard(pieces);
		this.pieces = pieces;
	}

	public PieceType movePiece(Coordinates from, Coordinates to) {
		validateMovability(from, to);
		PieceType target = pieces.remove(from);
		pieces.put(to, target);
		return target;
	}

	public Optional<PieceType> findPieceBy(Coordinates coordinates) {
		return Optional.ofNullable(pieces.get(coordinates));
	}

	public boolean isKingAliveOf(Color color) {
		return pieces.values()
				.stream()
				.anyMatch(piece -> isKingOf(piece, color));
	}

	private void validateBoard(Map<Coordinates, PieceType> pieces) {
		if (Objects.isNull(pieces)) {
			throw new IllegalArgumentException("체스판이 존재하지 않습니다.");
		}
	}

	private void validateMovability(Coordinates from, Coordinates to) {
		PieceType piece = findPieceBy(from).orElseThrow(() -> new PieceMoveFailedException("이동할 말이 존재하지 않습니다"));
		validateTargetIsNotAlly(piece, to);
		validateRouteHasObstacle(piece, from, to);
	}

	private void validateTargetIsNotAlly(PieceType piece, Coordinates coordinates) {
		if (isAlly(piece, coordinates)) {
			throw new PieceMoveFailedException("이동하려는 위치에 아군이 있습니다.");
		}
	}

	private boolean isAlly(PieceType piece, Coordinates coordinates) {
		return findPieceBy(coordinates)
				.filter(piece::isAlly)
				.isPresent();
	}

	private void validateRouteHasObstacle(PieceType piece, Coordinates from, Coordinates to) {
		if (canNotReachable(piece, from, to)) {
			throw new PieceMoveFailedException("경로상에 장애물이 있습니다.");
		}
	}

	private boolean canNotReachable(PieceType piece, Coordinates from, Coordinates to) {
		return piece.findMovableCoordinates(from, to)
				.stream()
				.anyMatch(coordinates -> !coordinates.equals(to) && isObstacle(coordinates));
	}

	private boolean isKingOf(PieceType piece, Color color) {
		return piece.isTeamOf(color) && piece.isKing();
	}

	private boolean isObstacle(Coordinates coordinates) {
		return Objects.nonNull(pieces.get(coordinates));
	}

	public Map<Coordinates, PieceType> getPieces() {
		return new HashMap<>(pieces);
	}

	public List<Cell> getPiecesAsList() {
		return pieces.entrySet()
				.stream()
				.map(cell -> new Cell(cell.getKey(), cell.getValue()))
				.collect(Collectors.toList());
	}
}
