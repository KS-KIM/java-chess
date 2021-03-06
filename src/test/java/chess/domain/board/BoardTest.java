package chess.domain.board;

import static chess.domain.piece.PieceType.WHITE_PAWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.domain.coordinates.Coordinates;
import chess.domain.piece.Color;
import chess.exception.PieceMoveFailedException;

class BoardTest {
	private Board board;

	@BeforeEach
	void setup() {
		board = BoardFactory.createNewGame();
	}

	@Test
	void initBoardTest() {
		assertThat(BoardFactory.createNewGame()).isInstanceOf(Board.class);
	}

	@Test
	void isKingAlive() {
		assertThat(board.isKingAliveOf(Color.WHITE)).isTrue();
	}

	@Test
	void findPieceBy() {
		assertThat(board.findPieceBy(Coordinates.of("A2")).get()).isEqualTo(WHITE_PAWN);
	}

	@Test
	void movePiece() {
		board.movePiece(Coordinates.of("B2"), Coordinates.of("B3"));
		assertThat(board.findPieceBy(Coordinates.of("B3")).get()).isEqualTo(WHITE_PAWN);
	}

	@Test
	void movePiece_ExistAlly_ExceptionThrown() {
		assertThatThrownBy(() -> board.movePiece(Coordinates.of("D1"), Coordinates.of("D2")))
				.isInstanceOf(PieceMoveFailedException.class)
				.hasMessageContaining("같은 팀의 piece");
	}
}
