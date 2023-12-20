import java.util.ArrayList;
import java.util.zip.DataFormatException;

public interface HelpersInterface {
	int getDiceOfWeapon( String AttackerName,String AttackerWeapon) throws DataFormatException;
	
	ArrayList<Integer> getRollDice(int NumberOfAttacks);
	
	ArrayList<Boolean> getHitRollResult(String AttackerName, String AttackerWeapon, ArrayList<Integer> dicerolls) throws DataFormatException;

	ArrayList<Boolean> getWoundRollResult(String AttackerName,String AttackerWeapon,String DefenderName, ArrayList<Integer> dicerolls) throws DataFormatException;
	
	ArrayList<Boolean> getSavingRollResult(String AttackerName,String AttackerWeapon,String DefenderName, ArrayList<Integer> dicerolls) throws DataFormatException;
	
	int getDamage(String AttackerName,String AttackerWeapon,String DefenderName, int dicenumbers ) throws DataFormatException;

	boolean getIfOverKilled(String AttackerName,String AttackerWeapon,String DefenderName, int dicenumbers ) throws DataFormatException;
	
	ArrayList<String> getUnitWeapon(String UnitName);
	
	ArrayList<String>  getAllUnitslist();

	//front end calls webhelper
	//Webhelper webh =  new Webhelper ();
	// webh.getWoundRollResult(String AttackerName,String DefenderName, ArrayList<Integer> dicerolls);
	
	//webhelper calls backendhelper
	//BackendHelper.getWoundRollResult(String AttackerName,String DefenderName, ArrayList<Integer> dicerolls);
}
