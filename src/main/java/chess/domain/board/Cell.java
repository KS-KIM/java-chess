package chess.domain.board;

import chess.domain.coordinates.Coordinates;
import chess.domain.piece.PieceType;

public class Cell {
	private final Coordinates coordinates;
	private final PieceType piece;

	public Cell(Coordinates coordinates, PieceType piece) {
		this.coordinates = coordinates;
		this.piece = piece;
	}

	public String getCoordinatesName() {
		return coordinates.getName();
	}

	public String getPieceName() {
		return piece.name();
	}
}
