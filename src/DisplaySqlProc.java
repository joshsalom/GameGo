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
    public ArrayList<String> viewConsoles(String category) {
	try {
	    ArrayList<String> consoleList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL viewConsolesBy" + category + "()}");
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
		    int cid = rs.getInt("cid");
		    String name = rs.getString("name");
		    double price = rs.getDouble("price");
		    int stock = rs.getInt("stock");

		    String cidFormat = String.format("|%-5d|", cid);
		    String nameFormat = String.format("%-20s|", name);
		    String priceFormat = String.format("$%10.2f|", price);
		    String stockFormat = String.format("%-5d|", stock);

		    consoleList.add(cidFormat + nameFormat + priceFormat + stockFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return consoleList;
	} catch (Exception e) {
	    // e.printStackTrace();
	    return null;
	}

    }
    public void viewTransactionsById(int uid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL viewGameTransactionsById(?)}");
	    cs.setInt(1, uid);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		System.out.println("-----Game Purchase history-----");
		while (rs.next()) {
		    String title = rs.getString("title");
		    String author = rs.getString("author");
		    double price = rs.getDouble("price");
		    String date = rs.getString("date");

		    String titleFormat = String.format("|%-20s|", title);
		    String authorFormat = String.format("%-15s|", author);
		    String priceFormat = String.format("$%10.2f|", price);
		    String dateFormat = String.format("%-20s|", date);

		    System.out.println(titleFormat + authorFormat + priceFormat + dateFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	try {
	    CallableStatement cs = conn.prepareCall("{CALL viewConsoleTransactionsById(?)}");
	    cs.setInt(1, uid);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		System.out.println("-----Console Purchase history-----");
		while (rs.next()) {
		    String name = rs.getString("name");
		    double price = rs.getDouble("price");
		    String date = rs.getString("date");

		    String nameFormat = String.format("|%-20s|", name);
		    String priceFormat = String.format("$%10.2f|", price);
		    String dateFormat = String.format("%-20s|", date);

		    System.out.println(nameFormat + priceFormat + dateFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    public ArrayList<String> searchGamesString(String category, String input) {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL searchGamesBy" + category + "(?)}");
	    cs.setString(1, input);
	    
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
    public ArrayList<String> searchGamesByRating(String lessOrGreat, int input) {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL searchGamesByRating" + lessOrGreat + "(?)}");
	    cs.setInt(1, input);
	    
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
    public ArrayList<String> searchGamesByPrice(String lessOrGreat, Double input) {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL searchGamesByPrice" + lessOrGreat + "(?)}");
	    cs.setDouble(1, input);
	    
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
    
    public ArrayList<String> viewGamesOnSale() {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL viewGamesOnSale()}");
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		System.out.println("-----Games on sale-----");
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
