package chess.utils;

import chess.domain.Position;
import chess.domain.Team;
import chess.domain.piece.AbstractPiece;

public class WebUtils {
    static String positionParser(Position position) {
        return position.getX() + position.getY();
    }

    public static String caseChanger(AbstractPiece piece) {
        if (piece.getTeam() == Team.BLACK) {
            return piece.getName().toUpperCase();
        }
        return piece.getName();
    }
}