package db;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * WarhammerDB class for attempting connections with the main warhammer database.
 */
public class WarhammerDB {

	/**
	 * Default constructor.
	 */
	public WarhammerDB(){
	}

	/**
	 * Carry out a connection with the database
	 *
	 * @return a connection object to communicate with the database
	 */
	public Connection getConnection(){
		
		//Create a connection string
		//String connectionUrl = "jdbc:mysql://localhost:3306/WarhammerDB";
		//String connectionUrl = "http://localhost:33306/warhammerdb";
		//String connectionUrl = "jdbc:mysql://mysql_container:33306/warhammerdb";
		//String connectionUrl = "	jdbc:mysql://container-mysql:3306/warhammerdb";
		//String connectionUrl = "jdbc:damage_calculator_l1_08-1-mysql_container-1://localhost:33306/warhammerdb";
		//String connectionUrl = "	jdbc:mysql://db:3306/warhammerdb";
		//String connectionUrl = "jdbc:mysql://127.0.0.1:33306/warhammerdb";
		String connectionUrl = "jdbc:mysql://127.0.0.1:3306/WarhammerDB";

		Connection connection = null;
		//Check if the driver exist
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//Create a connection to the DB, password is cs506
			connection = DriverManager.getConnection(connectionUrl,"root", "cs506");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Close the connection with the database
	 *
	 * @param connection connection object to be closed
	 */
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method for testing connection.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		WarhammerDB db = new WarhammerDB();
		Connection connection = db.getConnection();
		
		System.out.print("Database connected:");
		System.out.println(connection);	
		db.closeConnection(connection);
	}
}
