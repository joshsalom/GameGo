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
    
    public String admin_addNewSale(int gid, double discount) {
    	try {
    	    CallableStatement cs = conn.prepareCall("{CALL admin_addNewSale(?, ?)}");
    	    cs.setInt(1, gid);
    	    cs.setDouble(2, discount);
    	    
    	    ResultSet rs = cs.executeQuery();
    	    rs.next();
    	    return "You added the game with gid " + gid + " to sales with a " + discount + " discount.";
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    return "Something went wrong with adding the sale";
    	}
    }
        
        public String admin_removeSale(int gid){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL admin_removeSale(?)}");
        		cs.setInt(1, gid);
        		ResultSet rs = cs.executeQuery();
        	    rs.next();
        	    return "You removed the game with gid " + gid;
        	} catch (Exception e){
        		e.printStackTrace();
        		return "Something went wrong with removing the sake";
        	}
        }
        
        public String admin_updateSaleGid(int gidInt, int newGid){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL admin_updateSaleGid(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setInt(2, newGid);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You've updated this sale's gid";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the sale";
        	}
        }
        
        public String admin_updateSaleDiscount(int gidInt, double newDiscount){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL admin_updateSaleDiscount(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setDouble(2, newDiscount);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this sale's discount";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the sale";
        	}
        }
        
        public String admin_updateSaleOriginalPrice(int gidInt, double newOriginalPrice){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL admin_updateSaleOriginalPrice(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setDouble(2, newOriginalPrice);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this sale's original price";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the sale";
        	}
        }
        
        
        /********* Modifying inventory********/
        public double inventoryInsertGame(String title, String author, String genre, 
        		String console_type, int rating, double price, int stock){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL inventoryInsertGame(?, ?, ?, ?, ?, ?, ?)}");
        		cs.setString(1, title);
        		cs.setString(2, author);
        		cs.setString(3, genre);
        		cs.setString(4, console_type);
        		cs.setInt(5, rating);
        		cs.setDouble(6, price);
        		cs.setInt(7, stock);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return rs.getDouble("price");
        	} catch(Exception e) {
        		//e.printStackTrace();
        		return -1.0;
        	}
        }
        
        public String inventoryDeleteGame(int gid, String title){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL inventoryDeleteGame(?, ?)}");
        		cs.setInt(1, gid);
        		cs.setString(2, title);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You've deleted " + title + " from the game listings.";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with deleting the game";
        	}
        }
        
        public double inventoryInsertConsole(String name, double price, int stock){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL inventoryInsertConsole(?, ?, ?)}");
        		cs.setString(1, name);
        		cs.setDouble(2, price);
        		cs.setInt(3, stock);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return rs.getDouble("price");
        	} catch(Exception e) {
        		//e.printStackTrace();
        		return -1.0;
        	}
        }
        
        public String inventoryDeleteConsole(String name){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL inventoryDeleteConsole(?)}");
        		cs.setString(1, name);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You've deleted " + name + " from the console list.";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with deleting the console";
        	}
        }
        
        /*public String updateGameGid(int gidInt, int newGid){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateGameGid(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setInt(2, newGid);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's gid";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the gid";
        	}
        }*/
        
        public String updateGameTitle(int gidInt, String newTitle){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateGameTitle(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setString(2, newTitle);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's title";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the title";
        	}
        }
        
        public String updateGameAuthor(int gidInt, String newAuthor){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateGameAuthor(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setString(2, newAuthor);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's author";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the author";
        	}
        }
        
        public String updateGameGenre(int gidInt, String newGenre){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateGameGenre(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setString(2, newGenre);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's genre";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the genre";
        	}
        }
        
        public String updateGameConsoleType(int gidInt, String newConsoleType){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateConsoleType(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setString(2, newConsoleType);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's console type";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the console type";
        	}
        }
        
        public String updateGameRating(int gidInt, int newRating){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateGameRating(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setInt(2, newRating);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's rating";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the rating";
        	}
        }
        
        public String updateGamePrice(int gidInt, double newPrice){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateGamePrice(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setDouble(2, newPrice);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's price";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the price";
        	}
        }
        
        public String updateGameStock(int gidInt, int newStock){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateGameStock(?, ?)}");
        		cs.setInt(1, gidInt);
        		cs.setInt(2, newStock);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this game's stock";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the stock";
        	}
        }
        
        
        public String updateConsoleName(String name, String newName){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateConsoleName(?, ?)}");
        		cs.setString(1, name);
        		cs.setString(2, newName);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this console's name";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the name";
        	}
        }
        
        public String updateConsolePrice(String name, double newPrice){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateConsolePrice(?, ?)}");
        		cs.setString(1, name);
        		cs.setDouble(2, newPrice);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this console's price";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the price";
        	}
        }
        
        public String updateConsoleStock(String name, int newStock){
        	try{
        		CallableStatement cs = conn.prepareCall("{CALL updateConsoleStock(?, ?)}");
        		cs.setString(1, name);
        		cs.setInt(2, newStock);
        		
        		ResultSet rs = cs.executeQuery();
        		rs.next();
        		return "You updated this console's stock";
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Something went wrong with updating the stock";
        	}
        }
}
