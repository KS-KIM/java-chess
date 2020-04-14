package chess.web.database;

import java.sql.Connection;

public interface DataSource {
	Connection getConnection();
}
