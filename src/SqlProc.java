import java.sql.*;
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
	    return "You bought the game " + rs.getString("title") + " by " + rs.getString("author");
	} catch (Exception e) {
	    //e.printStackTrace();
	    return "Something went wrong with buying the game";
	}
    }
}
