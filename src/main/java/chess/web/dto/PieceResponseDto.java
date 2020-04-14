package chess.web.dto;

import chess.domain.coordinates.Coordinates;
import chess.domain.piece.PieceType;

public class PieceResponseDto {
	private String position;
	private String pieceType;

	public PieceResponseDto(Coordinates coordinates, PieceType piece) {
		this.position = coordinates.getName();
		this.pieceType = piece.name();
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPieceType() {
		return pieceType;
	}

	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}
}
