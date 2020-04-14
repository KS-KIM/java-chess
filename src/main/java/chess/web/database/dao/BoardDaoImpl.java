package chess.web.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.domain.board.Board;
import chess.domain.board.Cell;
import chess.domain.coordinates.Coordinates;
import chess.domain.piece.PieceType;
import chess.web.database.JdbcTemplate;

public class BoardDaoImpl implements BoardDao {
	private final JdbcTemplate jdbcTemplate;

	public BoardDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertBoard(Board board) {
		String query = "INSERT INTO board VALUES(?, ?)";
		List<Cell> cells = board.getPiecesAsList();
		jdbcTemplate.update(query, (preparedStatement -> {
			prepareInsertStatements(cells, preparedStatement);
			preparedStatement.executeBatch();
		}));
	}

	@Override
	public Board getBoard() {
		String query = "SELECT * FROM board";
		return jdbcTemplate.query(query, resultSet -> {
			Map<Coordinates, PieceType> pieces = new HashMap<>();
			while (resultSet.next()) {
				Coordinates coordinates = Coordinates.of(resultSet.getString(1));
				PieceType piece = PieceType.of(resultSet.getString(2));
				pieces.put(coordinates, piece);
			}
			return new Board(pieces);
		});
	}

	@Override
	public void deleteBoard() {
		String query = "DELETE FROM board";
		jdbcTemplate.update(query);
	}

	private void prepareInsertStatements(List<Cell> cells, PreparedStatement preparedStatement) throws SQLException {
		for (Cell cell : cells) {
			preparedStatement.setString(1, cell.getCoordinatesName());
			preparedStatement.setString(2, cell.getPieceName());
			preparedStatement.addBatch();
			preparedStatement.clearParameters();
		}
	}

	@Override
	public PieceType findPieceBy(Coordinates coordinates) {
		String query = "SELECT * FROM board WHERE position = (?)";
		return jdbcTemplate.query(query,
				resultSet -> PieceType.of(resultSet.getString(2)),
				preparedStatement -> preparedStatement.setString(1, coordinates.getName()));
	}

	@Override
	public void insertOrUpdatePieceBy(Coordinates coordinates, PieceType piece) {
		String query = "INSERT INTO board VALUES(?, ?) ON DUPLICATE KEY UPDATE piece_type=(?)";
		jdbcTemplate.update(query, preparedStatement -> {
			preparedStatement.setString(1, coordinates.getName());
			preparedStatement.setString(2, piece.name());
			preparedStatement.setString(3, piece.name());
		});
	}

	@Override
	public void deletePieceBy(Coordinates coordinates) {
		String query = "DELETE FROM board WHERE position = (?)";
		jdbcTemplate.update(query, preparedStatement -> preparedStatement.setString(1, coordinates.getName()));
	}
}
