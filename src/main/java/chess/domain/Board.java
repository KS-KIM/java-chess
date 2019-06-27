package chess.domain;

import chess.domain.piece.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Board {
    private static final String WHITE_HERO_INIT_ROW = "1";
    private static final String BLACK_HERO_INIT_ROW = "8";
    private static final String WHITE_PAWN_INIT_ROW = "2";
    private static final String BLACK_PAWN_INIT_ROW = "7";
    private static final int KING_KIND_ID = 1;
    private static final int QUEEN_KIND_ID = 2;
    private static final int ROOK_KIND_ID = 3;
    private static final int KNIGHT_KIND_ID = 4;
    private static final int BISHOP_KIND_ID = 5;
    private static final int PAWN_KIND_ID = 6;
    private static final String KING_NAME = "K";
    private static final String PAWN_NAME = "P";

    private Map<Position, Piece> pieces = new HashMap<>();
    private Aliance thisTurn;

    public Board(Aliance turn) {
        this.thisTurn = turn;
    }

    public void initBoard() {
        putInitHeroPiece(Aliance.WHITE, WHITE_HERO_INIT_ROW);
        putInitHeroPiece(Aliance.BLACK, BLACK_HERO_INIT_ROW);

        List<Position> whitePawnPositions = Position.getRowPositions(WHITE_PAWN_INIT_ROW);
        List<Position> blackPawnPositions = Position.getRowPositions(BLACK_PAWN_INIT_ROW);

        for (Position whitePawnPosition : whitePawnPositions) {
            pieces.put(whitePawnPosition, new Pawn(Aliance.WHITE, PieceValue.PAWN));
        }

        for (Position blackPawnPosition : blackPawnPositions) {
            pieces.put(blackPawnPosition, new Pawn(Aliance.BLACK, PieceValue.PAWN));
        }
    }

    private void putInitHeroPiece(Aliance aliance, String initHeroRow) {
        pieces.put(Position.valueOf("a" + initHeroRow), new Rook(aliance, PieceValue.ROOK));
        pieces.put(Position.valueOf("b" + initHeroRow), new Knight(aliance, PieceValue.KNIGHT));
        pieces.put(Position.valueOf("c" + initHeroRow), new Bishop(aliance, PieceValue.BISHOP));
        pieces.put(Position.valueOf("d" + initHeroRow), new Queen(aliance, PieceValue.QUEEN));
        pieces.put(Position.valueOf("e" + initHeroRow), new King(aliance, PieceValue.KING));
        pieces.put(Position.valueOf("f" + initHeroRow), new Bishop(aliance, PieceValue.BISHOP));
        pieces.put(Position.valueOf("g" + initHeroRow), new Knight(aliance, PieceValue.KNIGHT));
        pieces.put(Position.valueOf("h" + initHeroRow), new Rook(aliance, PieceValue.ROOK));
    }

    public void putPiece(String position, int teamId, int kindId) {
        if (kindId == KING_KIND_ID) {
            pieces.put(Position.valueOf(position), new King(Aliance.valueOf(teamId), PieceValue.valueOf(kindId)));
        }
        if (kindId == QUEEN_KIND_ID) {
            pieces.put(Position.valueOf(position), new Queen(Aliance.valueOf(teamId), PieceValue.valueOf(kindId)));
        }
        if (kindId == ROOK_KIND_ID) {
            pieces.put(Position.valueOf(position), new Rook(Aliance.valueOf(teamId), PieceValue.valueOf(kindId)));
        }
        if (kindId == KNIGHT_KIND_ID) {
            pieces.put(Position.valueOf(position), new Knight(Aliance.valueOf(teamId), PieceValue.valueOf(kindId)));
        }
        if (kindId == BISHOP_KIND_ID) {
            pieces.put(Position.valueOf(position), new Bishop(Aliance.valueOf(teamId), PieceValue.valueOf(kindId)));
        }
        if (kindId == PAWN_KIND_ID) {
            pieces.put(Position.valueOf(position), new Pawn(Aliance.valueOf(teamId), PieceValue.valueOf(kindId)));
        }
    }

    public Optional<Piece> pieceValueOf(String position) {
        Optional<Piece> maybePiece = Optional.ofNullable(pieces.get(Position.valueOf(position)));
        return maybePiece;
    }

    public Aliance switchTurn() {
        if (thisTurn == Aliance.WHITE) {
            thisTurn = Aliance.BLACK;
            return thisTurn;
        }
        thisTurn = Aliance.WHITE;
        return thisTurn;
    }

    public void movePiece(String start, String end) {
        checkValidStartPosition(start);
        checkValidEndPosition(end);

        Position startPosition = Position.valueOf(start);
        Position endPosition = Position.valueOf(end);

        Piece piece = pieces.get(startPosition);

        Navigator navigator = new Navigator(startPosition, endPosition);
        piece.checkPossibleMove(this, startPosition, navigator);
        navigator.simulateMove(this, startPosition);

        pieces.remove(startPosition);
        pieces.put(endPosition, piece);
    }

    private void checkValidEndPosition(String position) {
        pieceValueOf(position).ifPresent(p -> {
            if (p.isDifferentTeam(thisTurn)) {
                throw new IllegalArgumentException("우리팀 말을 공격할 수 없습니다.");
            }
        });
    }

    private void checkValidStartPosition(String position) {
        Optional<Piece> maybePiece = pieceValueOf(position);
        maybePiece.ifPresent(p -> {
            if (p.isDifferentTeam(thisTurn)) {
                throw new IllegalArgumentException("상대팀 말은 움직일 수 없습니다.");
            }
        });
        maybePiece.orElseThrow(() -> new IllegalArgumentException("해당 위치에 말이 없습니다."));
    }

    public void checkUnOccupiedPosition(String position) {
        if (pieceValueOf(position).isPresent()) {
            throw new IllegalArgumentException("이동경로에 다른 말이 있습니다.");
        }
    }

    public long getDuplicatePawnCount(Aliance aliance) {
        List<Position> pawnPositions = pieces.keySet().stream()
                .filter(k -> pieces.get(k).getAliance() == aliance)
                .filter(k -> pieces.get(k).getPieceValue().getName() == PAWN_NAME)
                .collect(Collectors.toList());

        List<Character> pawnColumns = pawnPositions.stream()
                .map(p -> p.toString().charAt(0))
                .collect(Collectors.toList());

        Map<Character, Long> pawnColumnCount = pawnColumns.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return pawnColumnCount.values().stream()
                .filter(i -> i > 1)
                .reduce((long) 0, (a, b) -> a + b);
    }

    public boolean isKingAlive(Aliance aliance) {
        return pieces.values().stream()
                .filter(p -> p.getAliance() == aliance)
                .filter(p -> p.getPieceValue().getName() == KING_NAME)
                .count() != 0;
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }

    public Aliance getThisTurn() {
        return thisTurn;
    }
}