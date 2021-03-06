package chess.web.dto;

import java.util.List;
import java.util.stream.Collectors;

import chess.domain.ChessGame;
import chess.domain.piece.Color;

public class BoardResponseDto {
	private List<PieceResponseDto> pieces;
	private Color turn;

	public BoardResponseDto(ChessGame chessGame) {
		this.turn = chessGame.getTurn();
		this.pieces = chessGame.getPieces()
				.entrySet()
				.stream()
				.map(cell -> new PieceResponseDto(cell.getKey(), cell.getValue()))
				.collect(Collectors.toList());
	}

	public String getTurn() {
		return turn.name();
	}

	public void setTurn(Color turn) {
		this.turn = turn;
	}

	public List<PieceResponseDto> getPieces() {
		return pieces;
	}

	public void setPieces(List<PieceResponseDto> pieces) {
		this.pieces = pieces;
	}
}
