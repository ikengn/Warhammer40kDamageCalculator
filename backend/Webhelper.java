import java.util.*;
import db.*;

public class Webhelper{

	//instantiate DBM and ErrorMessager
	private final DBManager dbm = new DBManager();
	private final ArrayList<String> errorm = new  ArrayList<>();
	ArrayList<Boolean> errorNotificationBool = new ArrayList<Boolean>(List.of(false));
	ArrayList<Integer> errorNotificationInt= new ArrayList<Integer>(List.of(-1));
	ArrayList<String> errorNotificationStr= new ArrayList<String>(List.of("false"));

	/*
	public void SaveUserSession() {
		//todo
	}
	public void RestoreUserSession() {
		//todo
	}
	
	public void InsertionValidation () {
		//todo
	}*/

	//////////////////////////////////////////////////////////
	//for future adds-on feature and for db tab only 
	public ArrayList<String> getAllWeaponlist(){
		try {
			return dbm.getAllWeaponlist();
		}
		catch(Exception e){
			return errorNotificationStr;
		}
	}

	public ArrayList<String>  getUnitStat(String UnitName){
		if(InputValidation(UnitName))
			return dbm.getUnitStat(UnitName);
		return errorNotificationStr;
	}
	
	public ArrayList<String> getWeaponStat(String WeaponName){
		if(InputValidation(WeaponName))
			return dbm.getWeaponStat(WeaponName);
		return errorNotificationStr;
	}

	//////////////////////////////////////////////////////////
		
	private boolean InputValidation(String Userinput) {
		if(Userinput.isEmpty()) {
			errorm.add("No Input");
			return false;
		}
		if(Userinput.contains("Select")||Userinput.contains("Union")||Userinput.contains("=")) {
			errorm.add("Invalid UserInput");
			return false;
		}
		return true;
	}

	public ArrayList<String> getErrorMessage(){
		return errorm;
	}
	
	public ArrayList<String> getAllUnitslist(){
		try {
			ArrayList<String> sucessAns = BackendHelper.getAllUnitslist();
			if(sucessAns.size()==0){
				return errorNotificationStr;
			}
			sucessAns.add(0,"true");
			return sucessAns;
		}
		catch(Exception e) {
			return errorNotificationStr;
		}
	}
	
	public ArrayList<String> getUnitWeapon(String UnitName){
		if(InputValidation(UnitName))
		{
			ArrayList<String> sucessAns = BackendHelper.getUnitWeapon(UnitName);
			if(sucessAns.size()==0){
				return errorNotificationStr;
			}
			sucessAns.add(0,"true");
			return sucessAns;
		}
		return errorNotificationStr;
	}

	public ArrayList<Integer> getDiceOfWeapon( String AttackerName,String AttackerWeapon) {
		try {
			ArrayList<Integer> sucessAns = new ArrayList<Integer>();
			sucessAns.add(BackendHelper.getDiceOfWeapon(AttackerName, AttackerWeapon));
			sucessAns.add(0,1);
			return sucessAns;
		}
		catch (Exception e) {
			errorm.add("Can't get dice of weapon because"+ e.getMessage());
			return errorNotificationInt;
		}
	}

	public ArrayList<Boolean> getHitRollResult(String AttackerName, String AttackerWeapon, ArrayList<Integer> dicerolls){
		try {
			ArrayList<Boolean> sucessAns = BackendHelper.getHitRollResult(AttackerName, AttackerWeapon,  dicerolls);
			sucessAns.add(0, true);
			return sucessAns;
		}
		catch (Exception e) {
			errorm.add("Can't get hit roll because"+e.getMessage());
			return errorNotificationBool;
		}
	}

	public ArrayList<Boolean> getWoundRollResult(String AttackerName,String AttackerWeapon,String DefenderName, ArrayList<Integer> dicerolls){
		try {
			ArrayList<Boolean> sucessAns = BackendHelper.getWoundRollResult(AttackerName,AttackerWeapon,DefenderName, dicerolls);
			sucessAns.add(0, true);
			return sucessAns;
			}
		catch (Exception e) {
			errorm.add("Can't get wound roll because"+e.getMessage());
			return errorNotificationBool;
		}
	}

	public ArrayList<Boolean> getSavingRollResult(String AttackerName,String AttackerWeapon,String DefenderName, ArrayList<Integer> dicerolls){
		try {
			ArrayList<Boolean> sucessAns =BackendHelper.getSavingRollResult(AttackerName,AttackerWeapon,DefenderName, dicerolls);
			sucessAns.add(0, true);
			return sucessAns;
			}
		catch (Exception e)
		{
			errorm.add("Can't get saving roll because"+e.getMessage());
			return errorNotificationBool;
		}
	}

	public ArrayList<Integer> getRollDice(int NumberOfAttacks) {
		try {
			ArrayList<Integer> sucessAns =BackendHelper.getRollDice(NumberOfAttacks);	
			sucessAns.add(0, 1);
			return sucessAns;
			}
		catch (Exception  e)
		{
			errorm.add("Can't roll because"+e.getMessage());
			return errorNotificationInt;
		}

	}

	public ArrayList<Integer> getDamage(String AttackerName, String AttackerWeapon, String DefenderName, int dicenumbers) {
		try {
			ArrayList<Integer> sucessAns = new ArrayList<Integer>();
			sucessAns.add(BackendHelper.getDamage( AttackerName, AttackerWeapon, DefenderName, dicenumbers));
			sucessAns.add(0,1);
			return sucessAns;
		}
		catch (Exception e)
		{
			errorm.add("Can't get Damage because"+ e.getMessage());
			return errorNotificationInt;
		}
	}

	public ArrayList<Boolean>  getIfOverKilled(String AttackerName, String AttackerWeapon, String DefenderName, int dicenumbers) {
		try {
			ArrayList<Boolean> sucessAns = new ArrayList<Boolean>();
			sucessAns.add( BackendHelper.getIfOverKilled(AttackerName,  AttackerWeapon, DefenderName,  dicenumbers));
			sucessAns.add(0,true);
			return sucessAns;
		}
		catch (Exception e)
		{
			errorm.add("Can't get if over killed because" +e.getMessage());
			return errorNotificationBool;
		}
	}

	public static void main(String[] args) {
		//testing driver
		Webhelper web = new Webhelper();
		System.out.println("Webhelper created:");

		//for front end only
		//all return now added one element at the first     
		//         1/true/"true"
		//or     -1/ false/"false"
		
		///warning !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
		//System.out.println(web.getHitRollResult("Captain on bike", "Hunting lance", web.getRollDice(5)));
		//it returns  [true, false, true, true, true, true, true] which is wrong because web.getRollDice(5) return true + output 
		
		//should use 
		//ArrayList<Integer> tempdice = web.getRollDice(5);
		//tempdice.remove(0);
		
		//System.out.println(web.getHitRollResult("Captain on bike", "Hunting lance", tempdice));
		//[true, true, true, true, true, true]
		// which first true indicates the success and [true, true, true, true, true] are actual output
		
		//System.out.println(web.getAllUnitslist());
		//[true, Captain, Captain on Bike, Imperial Space Marine, Space Marine]
		
		//System.out.println(web.getUnitWeapon("Space Marine"));
		//[true, Bolt pistol, Laspistol]
		
		//System.out.println(web.getUnitWeapon("Space"));
		//[false]
		
		//System.out.println(web.getUnitWeapon(""));
		//[false]

		//System.out.println(web.getDiceOfWeapon("Space Marine", "Bolt pistol"));
		//[1, 1]
		
		//System.out.println(web.getDiceOfWeapon("Space Marine", "Laspistol"));
		//[1,1]
		
		//System.out.println(web.getDiceOfWeapon("Captain on Bike", "Hunting lance"));
		//[1,4]
		
		//System.out.println(web.getDiceOfWeapon("Captain on Bike",""));
		//[-1]
		//System.out.println(web.getErrorMessage());
		//[Can't get dice of weapon becauseIndex 0 out of bounds for length 0]
		
		//System.out.println(web.getRollDice(5));
		//[1, 3, 2, 2, 3, 5]
		//System.out.println(web.getRollDice(1));
		//[1,6]
		//System.out.println(web.getRollDice(Integer.MAX_VALUE));
		//java.lang.OutOfMemoryError: Java heap space
		
		//System.out.println(web.getHitRollResult(null, null, null));
		//[false]
		//System.out.println(web.getErrorMessage());
		//[Can't get dice of weapon becauseIndex 0 out of bounds for length 0, Can't get hit roll becauseIndex 0 out of bounds for length 0]
		
		//System.out.println(web.getIfOverKilled(null, null, null, 0));
		//[false]
		
		////////////////////////////////////////////////////////////////////////////////
		//int numRoll = web.getDiceOfWeapon("Captain on bike", "Hunting lance");
//		System.out.println(numRoll);
//		ArrayList<Integer> randomRoll = web.getRollDice(numRoll);
//		System.out.println(randomRoll);
//		ArrayList<Boolean> hit = web.getHitRollResult("Space marine", "Bolt pistol", randomRoll);
//		System.out.println(hit);
//
//		ArrayList<Integer> randomRoll2 = web.getRollDice(3);
//		System.out.println(randomRoll2);
//		System.out.println(web.getWoundRollResult("Space marine", "Bolt pistol", "Captain", randomRoll2));
//		
		//excpeation handler hasn't fully implemented
		//can't pass following  "main" java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
		//	System.out.println(web.getDiceOfWeapon("Captain", "Bolt"));
		//	System.out.println(web.getErrorMessage());
		//System.out.println(web.getDiceOfWeapon(" ", " "));
		//System.out.println(web.getErrorMessage());
		//System.out.println(web.getDiceOfWeapon("", ""));
		//System.out.println(web.getErrorMessage());
		//System.out.println(web.getDiceOfWeapon(null, null));
		//System.out.println(web.getErrorMessage());
	}
}

