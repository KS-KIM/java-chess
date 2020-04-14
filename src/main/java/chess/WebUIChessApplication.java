package chess;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import chess.web.controller.ChessGameController;
import chess.web.database.DataSource;
import chess.web.database.JdbcTemplate;
import chess.web.database.MySQLDataSource;
import chess.web.database.dao.BoardDao;
import chess.web.database.dao.BoardDaoImpl;
import chess.web.database.dao.TurnDao;
import chess.web.database.dao.TurnDaoImpl;
import chess.web.service.ChessService;
import com.google.gson.Gson;

public class WebUIChessApplication {
	public static void main(String[] args) {
		port(8080);
		staticFiles.location("/static");

		ChessService chessService = initializeChessService();
		Gson gson = new Gson();

		ChessGameController chessGameController = new ChessGameController(chessService, gson);
		chessGameController.run();
	}

	private static ChessService initializeChessService() {
		DataSource dataSource = MySQLDataSource.getInstance();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		BoardDao boardDao = new BoardDaoImpl(jdbcTemplate);
		TurnDao turnDao = new TurnDaoImpl(jdbcTemplate);
		return new ChessService(boardDao, turnDao);
	}
}