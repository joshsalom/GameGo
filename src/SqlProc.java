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
	    return "Something went wrong with buying the game";
	}
    }
    public String returnGameRental(int uid, int gid) {
	try {
	    CallableStatement cs = conn.prepareCall("{CALL returnGameRental(?, ?)}");
	    cs.setInt(1, uid);
	    cs.setInt(2, gid);
	    
	    ResultSet rs = cs.executeQuery();
	    rs.next();
	    return "You returned your rental \"" + rs.getString("title") + "\" by " + rs.getString("author") + " for $" + rs.getDouble("price");
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Something went wrong with buying the game";
	}
    }
}
