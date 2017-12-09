import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;

public class SqlProc {
    private static Connection conn;

    public SqlProc() {
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

    public int createUser(String name, int age, String email, String password) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL createUser(?, ?, ?, ?)}");
	    cs.setString(1, name);
	    cs.setInt(2, age);
	    cs.setString(3, email);
	    cs.setString(4, password);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return rs.getInt("uid");
	} catch (Exception e) {
	    //e.printStackTrace();
	    return -1;
	}

    }
    public int loginUser(String email, String password) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL loginUser(?, ?)}");
	    cs.setString(1, email);
	    cs.setString(2, password);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return rs.getInt("uid");
	} catch (Exception e) {
	    //e.printStackTrace();
	    return -1;
	}
    }
    public int loginAdmin(String email, String password) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL loginAdmin(?, ?)}");
	    cs.setString(1, email);
	    cs.setString(2, password);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return rs.getInt("uid");
	} catch (Exception e) {
	    //e.printStackTrace();
	    return -1;
	}
    }
    public int createMember(String email, String password) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL createMember(?, ?)}");
	    cs.setString(1, email);
	    cs.setString(2, password);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return rs.getInt("mid");
	} catch (Exception e) {
	    //e.printStackTrace();
	    return -1;
	}

    }
    public String buyGame(int uid, int gid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL buyGame(?, ?)}");
	    cs.setInt(1, uid);
	    cs.setInt(2, gid);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return "You bought the game \"" + rs.getString("title") + "\" by " + rs.getString("author") + " for $" + rs.getDouble("price");
	} catch (Exception e) {
	    //e.printStackTrace();
	    return "Something went wrong with buying the game";
	}
    }
    public String buyConsole(int uid, int cid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL buyConsole(?, ?)}");
	    cs.setInt(1, uid);
	    cs.setInt(2, cid);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return "You bought the console \"" + rs.getString("name") + "\" for $" + rs.getDouble("price");
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with buying the console";
	}
    }
    
    public String redeemPrize(int mid, int pid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL redeemPrize(?, ?)}");
	    cs.setInt(1, mid);
	    cs.setInt(2, pid);
	    
	    return "Enjoy your prize!\nThank you for being a valued GameGo Member!";
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with redeeming your prize.";
	}
    }
    
    public int getMemberId(int uid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL getMemberId(?)}");
	    cs.setInt(1, uid);
	    boolean hasResults = cs.execute();
	    while (hasResults) {
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
		    return rs.getInt("mid");
		}
		hasResults = cs.getMoreResults();
	    }
	    return -1;
	} catch (Exception e) {
	    // e.printStackTrace();
	    return -1;
	}
    }
    
    public String rentGame(int uid, int gid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL rentGame(?, ?)}");
	    cs.setInt(1, uid);
	    cs.setInt(2, gid);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return "You rented the game \"" + rs.getString("title") + "\" by " + rs.getString("author") + " for $" + rs.getDouble("price");
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with renting the game";
	}
    }
    
    public String endMembership(int mid){
	try {
	    CallableStatement cs = conn.prepareCall("{CALL endMembership(?)}");
	    cs.setInt(1, mid);
	    
	    cs.executeQuery();
	    
	    return "Sorry to see you go!\nThanks for being a valued GameGo Member!";
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with canceling your membership";
	}
    }
    
    public String returnGameRental(int mid, int gid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL returnGameRental(?, ?)}");
	    cs.setInt(1, mid);
	    cs.setInt(2, gid);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return "You returned your rental \"" + rs.getString("title") + "\" by " + rs.getString("author");
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with returning the rental game";
	}
    }
    
    public String sumOfTransactions() {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL sumOfTransactions()}");
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return "The Net Gross of current Transactions is: " + rs.getInt("revenue");
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with archiving the transactions";
	}
    }
    
    public String archiveAllTransactions() {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL archiveAllTransactions()}");
	    
	    cs.executeQuery();
	    
	    return "All current transactions has been archived.";
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with archiving the transactions";
	}
    }
    
    public String archiveTransactionsByTwoDates(String date1, String date2) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL archiveTransactionsByTwoDates(?, ?)}");
	    cs.setString(1, date1);
	    cs.setString(2, date2);
	    
	    cs.executeQuery();
	    
	    return "All current transactions between " + date1 + " and " + date2 + " has been archived.";
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with archiving the transactions";
	}
    }
    
    public int admin_addNewAdmin(int uid, String email) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL admin_addNewAdmin(?, ?)}");
	    cs.setInt(1, uid);
	    cs.setString(2, email);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return rs.getInt("uid");
	} catch (Exception e) {
	    //e.printStackTrace();
	    return -1;
	}
    }
}
