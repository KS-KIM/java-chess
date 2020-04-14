package chess.web.database.dao;

import chess.domain.board.Board;
import chess.domain.coordinates.Coordinates;
import chess.domain.piece.PieceType;

public interface BoardDao {
	void insertBoard(Board board);

	PieceType findPieceBy(Coordinates coordinates);

	Board getBoard();

	void insertOrUpdatePieceBy(Coordinates coordinates, PieceType piece);

	void deleteBoard();

	void deletePieceBy(Coordinates coordinates);
}
