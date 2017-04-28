package ca.van.abolyachevets.skunk.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ca.van.abolyachevets.skunk.game.Player;

public class DBConnector {

	private static String username = "root";
	private static String password = "password";
	private static String server = "localhost:3306";
	private static String database = "mydatabase";
	private static String url = "jdbc:mysql://" + server + "/" + database + "?autoReconnect=true&useSSL=false";
	public final static String TABLE_NAME = "TOP_SCORES";

	private final static String FIELD_1 = "Name";
	private final static String FIELD_2 = "Score";

	private Connection connection = null;
	
	
	public DBConnector() {
		connection = connect();
	}

	private Connection connect() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.getMessage();
		}
		return connection;

	}

	public static void create(Connection connection) throws SQLException {
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sqlString = "CREATE TABLE " + TABLE_NAME + " (Name VARCHAR(50), Score INTEGER, PRIMARY KEY (Name));";
		statement.executeUpdate(sqlString);
	}

	public static void add(Connection connection, String name, int s) throws SQLException {
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sqlString = "INSERT INTO " + TABLE_NAME + " (Name, Score) VALUES ('" + name + "'," + s + " );";
		statement.executeUpdate(sqlString);
	}

	public static void drop(Connection connection) throws SQLException {
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sqlString = "DROP TABLE " + TABLE_NAME + ";";
		statement.executeUpdate(sqlString);
	}

	public static ArrayList<Player> topScores(Connection connection) throws SQLException {
		ArrayList<Player> tempPlayerList = new ArrayList<>();

		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sqlString = "SELECT * FROM " + TABLE_NAME + ";";
		ResultSet resultSet = statement.executeQuery(sqlString);

		while (resultSet.next()) {
			Player player = new Player(resultSet.getString(FIELD_1), resultSet.getInt(FIELD_2));
			tempPlayerList.add(player);
		}
		return tempPlayerList;
	}

	public static Player getPlayer(Connection connection, String name) throws SQLException {
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sqlString = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIELD_1 + " = '" + name + "';";
		ResultSet resultSet = statement.executeQuery(sqlString);
		Player player = null;
		while (resultSet.next()) {
			player = new Player(resultSet.getString(FIELD_1), resultSet.getInt(FIELD_2));
		}
		statement.close();
		return player;
	}

	public static List<String> getPlayerNames(Connection connection) throws SQLException {
		List<String> names = new ArrayList<>();

		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sqlString = "SELECT " + FIELD_1 + " FROM " + TABLE_NAME + ";";
		ResultSet resultSet = statement.executeQuery(sqlString);

		while (resultSet.next()) {
			names.add(resultSet.getString(FIELD_1));
		}
		statement.close();
		return names;
	}

	public static boolean tableExists(Connection connection, String table) {
		boolean check = false;
		DatabaseMetaData md;
		try {
			md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
				if (rs.getString(3).equals(table)) {
					check = true;
				}
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return check;
	}
	
	public Connection getConnection() {
		return connection;
	}

}
