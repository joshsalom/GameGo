import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

public class DisplaySqlProc {
    private static Connection conn;

    public DisplaySqlProc() {
	DataSource ds = DataSourceFactory.getMySQLDataSource();
	try {
	    conn = ds.getConnection();
	} catch (SQLException e) {
	    System.out.println("Connection Failed! Check output console");
	    e.printStackTrace();
	    return;
	}

	if (conn != null) {
	    // System.out.println("You made it, take control your database
	    // now!");
	} else {
	    System.out.println("Failed to make connection!");
	}
    }

    public ArrayList<String> viewGames(String category) {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL viewGamesBy" + category + "()}");
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
		    int gid = rs.getInt("gid");
		    String title = rs.getString("title");
		    String author = rs.getString("author");
		    String genre = rs.getString("genre");
		    String console = rs.getString("console_type");
		    int rating = rs.getInt("rating");
		    double price = rs.getDouble("price");
		    int stock = rs.getInt("stock");

		    String gidFormat = String.format("|%-5d|", gid);
		    String titleFormat = String.format("%-20s|", title);
		    String authorFormat = String.format("%-15s|", author);
		    String genreFormat = String.format("%-10s|", genre);
		    String consoleFormat = String.format("%-15s|", console);
		    String ratingFormat = String.format("%-2d|", rating);
		    String priceFormat = String.format("$%10.2f|", price);
		    String stockFormat = String.format("%-5d|", stock);

		    gameList.add(gidFormat + titleFormat + authorFormat + genreFormat + consoleFormat + ratingFormat
			    + priceFormat + stockFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return gameList;
	} catch (Exception e) {
	    // e.printStackTrace();
	    return null;
	}

    }

}
