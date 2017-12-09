import java.sql.*;
import java.sql.Date;
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
    
    public ArrayList<String> sortTransactionsBy(String category) {
	try {
	    ArrayList<String> transactionList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL sortTransactionsBy" + category + "()}");
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		
		String tidFormat = String.format("|%-5s|", "tid");
		String uidFormat = String.format("%-5s|", "uid");
		String gidFormat = String.format("%-5s|", "gid");
		String cidFormat = String.format("%-5s|", "cid");
		String priceFormat = String.format("%-11s|", "Price");
		String dateFormat = String.format("%-20s|", "Date");
		transactionList.add(tidFormat + uidFormat + gidFormat + cidFormat + priceFormat + dateFormat);

		
		while (rs.next()) {
		    int tid = rs.getInt("tid");
		    int uid = rs.getInt("uid");
		    int gid = rs.getInt("gid");
		    int cid = rs.getInt("cid");
		    double price = rs.getDouble("price");
		    Date date = rs.getDate("date");

		    tidFormat = String.format("|%-5d|", tid);
		    uidFormat = String.format("%-5d|", uid);
		    gidFormat = String.format("%-5d|", gid);
		    cidFormat = String.format("%-5d|", cid);
		    priceFormat = String.format("$%10.2f|", price);
		    dateFormat = String.format("%-20s|", date.toString());

		    transactionList.add(tidFormat + uidFormat + gidFormat + cidFormat + priceFormat + dateFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return transactionList;
	} catch (Exception e) {
	    System.out.println("SOMETHING WENT WRONG: " + e);
	    return null;
	}

    }
    
    public ArrayList<String> searchAllTransactionsBy(String category, int someID){
	try {
	    ArrayList<String> transactionList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL searchAllTransactionsBy" + category + "(?)}");
	    cs.setInt(1, someID);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		
		String tidFormat = String.format("|%-5s|", "tid");
		String uidFormat = String.format("%-5s|", "uid");
		String gidFormat = String.format("%-5s|", "gid");
		String cidFormat = String.format("%-5s|", "cid");
		String priceFormat = String.format("%-11s|", "Price");
		String dateFormat = String.format("%-20s|", "Date");
		transactionList.add(tidFormat + uidFormat + gidFormat + cidFormat + priceFormat + dateFormat);

		
		while (rs.next()) {
		    int tid = rs.getInt("tid");
		    int uid = rs.getInt("uid");
		    int gid = rs.getInt("gid");
		    int cid = rs.getInt("cid");
		    double price = rs.getDouble("price");
		    Date date = rs.getDate("date");

		    tidFormat = String.format("|%-5d|", tid);
		    uidFormat = String.format("%-5d|", uid);
		    gidFormat = String.format("%-5d|", gid);
		    cidFormat = String.format("%-5d|", cid);
		    priceFormat = String.format("$%10.2f|", price);
		    dateFormat = String.format("%-20s|", date.toString());

		    transactionList.add(tidFormat + uidFormat + gidFormat + cidFormat + priceFormat + dateFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return transactionList;
	} catch (Exception e) {
	    System.out.println("SOMETHING WENT WRONG: " + e);
	    return null;
	}

    }
    
    public ArrayList<String> searchAllTransactionsByDate(String date1, String date2){
	try {
	    ArrayList<String> transactionList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL searchAllTransactionsByTWODATES(?, ?)}");
	    cs.setString(1, date1);
	    cs.setString(2, date2);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		
		String tidFormat = String.format("|%-5s|", "tid");
		String uidFormat = String.format("%-5s|", "uid");
		String gidFormat = String.format("%-5s|", "gid");
		String cidFormat = String.format("%-5s|", "cid");
		String priceFormat = String.format("%-11s|", "Price");
		String dateFormat = String.format("%-20s|", "Date");
		transactionList.add(tidFormat + uidFormat + gidFormat + cidFormat + priceFormat + dateFormat);

		
		while (rs.next()) {
		    int tid = rs.getInt("tid");
		    int uid = rs.getInt("uid");
		    int gid = rs.getInt("gid");
		    int cid = rs.getInt("cid");
		    double price = rs.getDouble("price");
		    Date date = rs.getDate("date");

		    tidFormat = String.format("|%-5d|", tid);
		    uidFormat = String.format("%-5d|", uid);
		    gidFormat = String.format("%-5d|", gid);
		    cidFormat = String.format("%-5d|", cid);
		    priceFormat = String.format("$%10.2f|", price);
		    dateFormat = String.format("%-20s|", date.toString());

		    transactionList.add(tidFormat + uidFormat + gidFormat + cidFormat + priceFormat + dateFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return transactionList;
	} catch (Exception e) {
	    System.out.println("SOMETHING WENT WRONG: " + e);
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
    
    public ArrayList<String> viewPrizes() {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL viewPrizes()}");
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		System.out.println("-----Prizes for Points!!-----");
		while (rs.next()) {
		    int pid = rs.getInt("pid");
		    String prize_name = rs.getString("prize_name");
		    int prize_points = rs.getInt("prize_points");

		    String pid_priceFormat = String.format("|%-5d|", pid);
		    String prize_nameFormat = String.format("%-20s|", prize_name);
		    String prize_priceFormat = String.format("%-5d|", prize_points);

		    gameList.add(pid_priceFormat + prize_nameFormat + prize_priceFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return gameList;
	} catch (Exception e) {
	    // e.printStackTrace();
	    return null;
	}
    }
    
    /****** MEMBERSHIP DISPLAYS *********************************************************/
    
    public String viewMemberPoints(int mid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL getMemberPoints(?)}");
	    cs.setInt(1, mid);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
		    return "Availible Points: " + rs.getInt("points");
		}
		hasResults = cs.getMoreResults();
	    }
	    return "Availible Points: 0";
	} catch (Exception e) {
	    // e.printStackTrace();
	    return "ERR";
	}

    }
    
    public ArrayList<String> viewMemberRentals(int mid) {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL viewMemberRentals(?)}");
	    cs.setInt(1, mid);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		System.out.println("-----Currently Rented Games-----");
		while (rs.next()) {
		    int gid = rs.getInt("gid");
		    String title = rs.getString("title");
		    String author = rs.getString("author");
		    String genre = rs.getString("genre");
		    Date due_date = rs.getDate("date_due");

		    String gidFormat = String.format("|%-5d|", gid);
		    String titleFormat = String.format("%-20s|", title);
		    String authorFormat = String.format("%-15s|", author);
		    String genreFormat = String.format("%-10s|", genre);
		    String dateFormat = String.format("%-15s|", due_date.toString());

		    gameList.add(gidFormat + titleFormat + authorFormat + genreFormat + dateFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return gameList;
	} catch (Exception e) {
	    // e.printStackTrace();
	    return null;
	}

    }
    
    /****** ADMIN DISPLAYS ******************************************************/
    public ArrayList<String> admin_viewMemberships() {
	try {
	    ArrayList<String> list = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL admin_viewMemberships()}");
	    
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		String uidFormat = String.format("|%-5s|", "UID");
		String midFormat = String.format("%-5s|", "MID");
		String pointsFormat = String.format("%-10s|", "Points");
		String nameFormat = String.format("%-25s|", "Name");		    
		String ageFormat = String.format("%-3s|", "Age");		    
		String emailFormat = String.format("%-25s|", "Email");
		list.add(uidFormat + midFormat + pointsFormat + nameFormat + ageFormat + emailFormat);
		
		while (rs.next()) {
		    int uid = rs.getInt("uid");
		    int mid = rs.getInt("mid");
		    int points = rs.getInt("points");
		    String name = rs.getString("name");
		    int age = rs.getInt("age");
		    String email = rs.getString("email");

		    uidFormat = String.format("|%-5d|", uid);
		    
		    if (mid == 0) {
			midFormat = String.format("%-5s|", "-----");
			pointsFormat = String.format("%-10s|", "-----");
		    } else {
			midFormat = String.format("%-5d|", mid);
			pointsFormat = String.format("%-10d|", points);
		    }
		    nameFormat = String.format("%-25s|", name);		    
		    ageFormat = String.format("%-3d|", age);		    
		    emailFormat = String.format("%-25s|", email);


		    list.add(uidFormat + midFormat + pointsFormat + nameFormat + ageFormat + emailFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return list;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
    
    public ArrayList<String> admin_searchMembershipsByEmail(String input) {
	try {
	    ArrayList<String> list = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL admin_searchMembershipsByEmail(?)}");
	    cs.setString(1, input);
	    
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		String uidFormat = String.format("|%-5s|", "UID");
		String midFormat = String.format("%-5s|", "MID");
		String pointsFormat = String.format("%-10s|", "Points");
		String nameFormat = String.format("%-25s|", "Name");		    
		String ageFormat = String.format("%-3s|", "Age");		    
		String emailFormat = String.format("%-25s|", "Email");
		list.add(uidFormat + midFormat + pointsFormat + nameFormat + ageFormat + emailFormat);
		
		while (rs.next()) {
		    int uid = rs.getInt("uid");
		    int mid = rs.getInt("mid");
		    int points = rs.getInt("points");
		    String name = rs.getString("name");
		    int age = rs.getInt("age");
		    String email = rs.getString("email");

		    uidFormat = String.format("|%-5d|", uid);
		    
		    if (mid == 0) {
			midFormat = String.format("%-5s|", "-----");
			pointsFormat = String.format("%-10s|", "-----");
		    } else {
			midFormat = String.format("%-5d|", mid);
			pointsFormat = String.format("%-10d|", points);
		    }
		    nameFormat = String.format("%-25s|", name);		    
		    ageFormat = String.format("%-3d|", age);		    
		    emailFormat = String.format("%-25s|", email);


		    list.add(uidFormat + midFormat + pointsFormat + nameFormat + ageFormat + emailFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return list;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
    
    public ArrayList<String> countGames(String category) {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL countGamesBy" + category + "()}");
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
		    String categoryString = "";
		    if (category.equals("rating")) {
			categoryString = "" + rs.getInt(category);
		    } else if (category.equals("console")){
			categoryString = rs.getString("console_type");
		    } else {
			categoryString = rs.getString(category);
		    }
		    int count = rs.getInt("count");

		    String categoryFormat = String.format("|%-15s|", categoryString);
		    String countFormat = String.format("%-5d|", count);


		    gameList.add(categoryFormat + countFormat);
		}
		hasResults = cs.getMoreResults();
	    }
	    return gameList;
	} catch (Exception e) {
	    // e.printStackTrace();
	    return null;
	}

    }
    
    public ArrayList<String> ratingGreaterThanAvg(String category) {
	try {
	    ArrayList<String> gameList = new ArrayList<String>();
	    CallableStatement cs = conn.prepareCall("{CALL ratingGreaterThanAvgBy" + category + "()}");
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
    
    public void sumOfTransactionsByTwoDates(String date1, String date2) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL sumOfTransactionsByTwoDates(?, ?)}");
	    cs.setString(1, date1);
	    cs.setString(2, date2);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
		    System.out.println(rs.getInt("revenue"));
		}
		hasResults = cs.getMoreResults();
	    }
	} catch (Exception e) {
	    // e.printStackTrace();
	}
    }
}
