package chess.web.database.dao;

import chess.domain.piece.Color;
import chess.web.database.JdbcTemplate;

public class TurnDaoImpl implements TurnDao {
	private final JdbcTemplate jdbcTemplate;

	public TurnDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(Color turn) {
		String query = "INSERT INTO turn VALUES (?)";
		jdbcTemplate.update(query, preparedStatement -> preparedStatement.setString(1, turn.name()));
	}

	@Override
	public void update(Color turn) {
		String query = "UPDATE turn SET current_turn = (?)";
		jdbcTemplate.update(query, preparedStatement -> preparedStatement.setString(1, turn.name()));
	}

	@Override
	public Color getTurn() {
		String query = "SELECT * FROM turn";
		return jdbcTemplate.query(query, (resultSet) -> {
			resultSet.next();
			return Color.of(resultSet.getString(1));
		});
	}

	@Override
	public void deleteTurn() {
		String query = "DELETE FROM turn";
		jdbcTemplate.update(query);
	}
}

