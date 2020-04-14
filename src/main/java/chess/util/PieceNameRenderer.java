package chess.util;

import java.util.HashMap;
import java.util.Map;

import chess.domain.piece.PieceType;

public class PieceNameRenderer {
	private static final Map<String, String> NAME_MAPPER = new HashMap<>();

	static {
		NAME_MAPPER.put("WHITE_KING", "k");
		NAME_MAPPER.put("BLACK_KING", "K");
		NAME_MAPPER.put("WHITE_QUEEN", "q");
		NAME_MAPPER.put("BLACK_QUEEN", "Q");
		NAME_MAPPER.put("WHITE_ROOK", "r");
		NAME_MAPPER.put("BLACK_ROOK", "R");
		NAME_MAPPER.put("WHITE_KNIGHT", "n");
		NAME_MAPPER.put("BLACK_KNIGHT", "N");
		NAME_MAPPER.put("WHITE_BISHOP", "b");
		NAME_MAPPER.put("BLACK_BISHOP", "B");
		NAME_MAPPER.put("WHITE_PAWN", "p");
		NAME_MAPPER.put("BLACK_PAWN", "P");
	}

	private PieceNameRenderer() {
	}

	public static String renderPieceName(PieceType pieceType) {
		return NAME_MAPPER.get(pieceType.name());
	}
}
