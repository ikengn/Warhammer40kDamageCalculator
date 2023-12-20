package db;
import java.util.*;
import  java.sql.* ;

/**
 * DBManager class to manage the warhammer database through SQL queries.
 */
public class DBManager {

	//instantiate WarhammerDB 
	private final WarhammerDB db = new WarhammerDB();

	/**
	 * Default constructor, setting the connection with the database
	 */
	public DBManager(){
		//inital testing connection
		Connection connection = db.getConnection();
		db.closeConnection(connection);
	}

	/**
	 * Private helper method to return a column from a table
	 *
	 * @param col column name in string format
	 * @param table table name in string format
	 * @return a list of string values from the specified column
	 */
	private ArrayList<String> myquerycol(String col, String table){
		
		ArrayList<String> queryresults = new ArrayList<>();
		
		try {
			//Create a SQL statement
			Connection con = db.getConnection();
			Statement stmt = con.createStatement();

			//Make a query
			String query1;
				
			//premade query and insert based on passing parameters
			//queryeg = " select Name from units";		
			query1 = " SELECT " + col + " FROM " + table;
			PreparedStatement ps1 = con.prepareStatement(query1);
			
			//excute the query 
			ResultSet result1 = ps1.executeQuery();
					
			//process the resultset
			while(result1.next()){
				//testing output
				//System.out.println(result1.getString(col));
				
				//collects all results
				queryresults.add(result1.getString(col));
			}
			result1.close();
			// don't forget close db connection
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return queryresults;
	}

	/**
	 * Private helper method to get records from the table with conditions
	 *
	 * @param col column name to get values from
	 * @param table name of the table to select from
	 * @param cond conditions for variables
	 * @param val variables to compare with
	 * @return a list of strings that satisfies the specified queries
	 */
	private ArrayList<String> myqueryrow(String col, String table, String cond, String val){
		
		ArrayList<String> queryresults = new ArrayList<>();
		
		try {
			//Create a SQL statement
			Connection con = db.getConnection();
			Statement stmt = con.createStatement();
				
			//Make a query
			String query1;
			
			//premade query and insert based on passing parameters
			//queryeg = SELECT * FROM units where Name = "Captain";
				
			query1 = "SELECT " + col + " FROM " + table +" where " + cond + " = " + "\"" + val +"\"";
			//System.out.println(query1);
			
			PreparedStatement ps1 = con.prepareStatement(query1);
			
			//excute the query 
			ResultSet result1 = ps1.executeQuery();
					
			//process the resultset
			ResultSetMetaData meta = result1.getMetaData();
			int colcount = meta.getColumnCount();
			while(result1.next()){
				for(int i = 1; i<= colcount; i++) {
					//no column name included 
					//queryresults.add(meta.getColumnName(i));
					queryresults.add(result1.getString(meta.getColumnName(i)));
				}
			}
			result1.close();
			// don't forget close db connection
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return queryresults;
	}

	/**
	 * Getting the names of all units from the Unit table.
	 *
	 * @return list of unit names in string format
	 */
	public ArrayList<String>  getAllUnitslist(){
		return myquerycol("Name","units");
	}

	/**
	 * Getting the weapons of all units from the Weapon table.
	 *
	 * @return list of weapon names in string format
	 */
	public ArrayList<String>  getAllWeaponlist(){
		return myquerycol("Weapon","weapon");
	}

	/**
	 * Getting a record from the Units table
	 *
	 * @param UnitName name of the unit record
	 * @return a string contains all attributes of the record
	 */
	public ArrayList<String>  getUnitStat(String UnitName){
		return myqueryrow("*","units","Name",UnitName);
	}

	/**
	 * Getting a record from the Weapons table
	 *
	 * @param WeaponName name of the weapon record
	 * @return a string contains all attributes of the record
	 */
	public ArrayList<String>  getWeaponStat(String WeaponName){
		return myqueryrow("*","weapon","Weapon",WeaponName);
	}

	/**
	 * Getting all equipable weapons of a unit given unit's name
	 *
	 * @param UnitName the name of the unit
	 * @return all weapons that belong to this unit
	 */
	public ArrayList<String> getUnitWeapon(String UnitName){
		return myqueryrow("Wepaon", "weaponuser" ,"Name", UnitName);
	}

	/**
	 * Main method for simple testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DBManager dbm = new DBManager();
		System.out.print("Dbmanger connected:");
		
		System.out.println(dbm.getAllUnitslist());	
		System.out.println(dbm.getUnitStat("Captain"));
		
		System.out.println(dbm.getAllWeaponlist());	
		System.out.println(dbm.getWeaponStat("Xenophase blade"));	
		
		System.out.println(dbm.getUnitWeapon("Imperial Space Marine"));
	}
}

