import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class Main
{

	public static void main(String[] argv)
	{

		DataSource ds = DataSourceFactory.getMySQLDataSource();

		Connection conn = null;
		try
		{
			conn = ds.getConnection();
		} catch (SQLException e)
		{
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (conn != null)
		{
			System.out.println("You made it, take control your database now!");
		} else
		{
			System.out.println("Failed to make connection!");
		}
	}
}