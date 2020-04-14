package chess.domain.board;

import static chess.domain.piece.PieceType.BLACK_BISHOP;
import static chess.domain.piece.PieceType.BLACK_KING;
import static chess.domain.piece.PieceType.BLACK_KNIGHT;
import static chess.domain.piece.PieceType.BLACK_PAWN;
import static chess.domain.piece.PieceType.BLACK_QUEEN;
import static chess.domain.piece.PieceType.BLACK_ROOK;
import static chess.domain.piece.PieceType.WHITE_BISHOP;
import static chess.domain.piece.PieceType.WHITE_KING;
import static chess.domain.piece.PieceType.WHITE_KNIGHT;
import static chess.domain.piece.PieceType.WHITE_PAWN;
import static chess.domain.piece.PieceType.WHITE_QUEEN;
import static chess.domain.piece.PieceType.WHITE_ROOK;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import chess.domain.coordinates.Coordinates;
import chess.domain.piece.PieceType;

public class BoardFactory {
	private static final Map<Coordinates, PieceType> INITIAL_STATE_PIECES = initializePieces();

	public static Board createNewGame() {
		return new Board(new HashMap<>(INITIAL_STATE_PIECES));
	}

	private static Map<Coordinates, PieceType> initializePieces() {
		Map<Coordinates, PieceType> pieces = new HashMap<>();
		initializeWhite(pieces);
		initializeBlack(pieces);
		initializeWhitePawn(pieces);
		initializeBlackPawn(pieces);
		return pieces;
	}

	private static void initializeWhite(Map<Coordinates, PieceType> pieces) {
		pieces.put(Coordinates.of("A1"), WHITE_ROOK);
		pieces.put(Coordinates.of("B1"), WHITE_KNIGHT);
		pieces.put(Coordinates.of("C1"), WHITE_BISHOP);
		pieces.put(Coordinates.of("D1"), WHITE_QUEEN);
		pieces.put(Coordinates.of("E1"), WHITE_KING);
		pieces.put(Coordinates.of("F1"), WHITE_BISHOP);
		pieces.put(Coordinates.of("G1"), WHITE_KNIGHT);
		pieces.put(Coordinates.of("H1"), WHITE_ROOK);
	}

	private static void initializeBlack(Map<Coordinates, PieceType> pieces) {
		pieces.put(Coordinates.of("A8"), BLACK_ROOK);
		pieces.put(Coordinates.of("B8"), BLACK_KNIGHT);
		pieces.put(Coordinates.of("C8"), BLACK_BISHOP);
		pieces.put(Coordinates.of("D8"), BLACK_QUEEN);
		pieces.put(Coordinates.of("E8"), BLACK_KING);
		pieces.put(Coordinates.of("F8"), BLACK_BISHOP);
		pieces.put(Coordinates.of("G8"), BLACK_KNIGHT);
		pieces.put(Coordinates.of("H8"), BLACK_ROOK);
	}

	private static void initializeWhitePawn(Map<Coordinates, PieceType> pieces) {
		Map<Coordinates, PieceType> whitePawns = Stream.of("A", "B", "C", "D", "E", "F", "G", "H")
				.map(column -> Coordinates.of(column + "2"))
				.collect(toMap(coordinates -> coordinates, coordinates -> WHITE_PAWN));
		pieces.putAll(whitePawns);
	}

	private static void initializeBlackPawn(Map<Coordinates, PieceType> pieces) {
		Map<Coordinates, PieceType> blackPawns = Stream.of("A", "B", "C", "D", "E", "F", "G", "H")
				.map(column -> Coordinates.of(column + "7"))
				.collect(toMap(coordinates -> coordinates, coordinates -> BLACK_PAWN));
		pieces.putAll(blackPawns);
	}
}
