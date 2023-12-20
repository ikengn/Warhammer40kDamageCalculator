import db.DBManager;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

/**
 * Public class that receives input from the Webhelper, call corresponding backend methods, and returning outputs needed
 * to show to the users.
 *
 */
public class BackendHelper {

    private static final DBManager dbm = new DBManager();

    public static int getDiceOfWeapon(String AttackerName, String AttackerWeapon) throws DataFormatException {
        Unit attacker = Parser.unitParser(dbm.getUnitStat(AttackerName));
        Weapon weap = Parser.weaponParser(dbm.getWeaponStat(AttackerWeapon));
        try {
            attacker.equipWeapon(weap);
        } catch (DataFormatException e) {
            throw new DataFormatException("This unit cannot equip this weapon.");
        }
        if (attacker.getWeapon().getType() == Type.Melee) {
            return attacker.getAttacks();
        } else {
            return weap.getNumHit();
        }
    }

    public static ArrayList<Integer> getRollDice(int NumberOfAttacks) {
        Dice dice = new Dice();
        return dice.rollDiceMul(NumberOfAttacks);
    }

    public static ArrayList<Boolean> getHitRollResult(String AttackerName, String AttackerWeapon, ArrayList<Integer> dicerolls) throws DataFormatException {
        Unit attacker = Parser.unitParser(dbm.getUnitStat(AttackerName));
        Weapon weap = Parser.weaponParser(dbm.getWeaponStat(AttackerWeapon));
        try {
            attacker.equipWeapon(weap);
        } catch (DataFormatException e) {
            throw new DataFormatException("This unit cannot equip this weapon.");
        }
        return DamageCalculator.hitRoll(attacker, dicerolls);
    }

    public static ArrayList<Boolean> getWoundRollResult(String AttackerName, String AttackerWeapon, String DefenderName, ArrayList<Integer> dicerolls) throws DataFormatException {
        Unit attacker = Parser.unitParser(dbm.getUnitStat(AttackerName));
        Unit target = Parser.unitParser(dbm.getUnitStat(DefenderName));
        Weapon weap = Parser.weaponParser(dbm.getWeaponStat(AttackerWeapon));
        try {
            attacker.equipWeapon(weap);
        } catch (DataFormatException e) {
            throw new DataFormatException("This unit cannot equip this weapon.");
        }
        return DamageCalculator.woundRoll(attacker, target, dicerolls);
    }

    public static ArrayList<Boolean> getSavingRollResult(String AttackerName, String AttackerWeapon, String DefenderName, ArrayList<Integer> dicerolls) throws DataFormatException {
        Unit attacker = Parser.unitParser(dbm.getUnitStat(AttackerName));
        Unit target = Parser.unitParser(dbm.getUnitStat(DefenderName));
        Weapon weap = Parser.weaponParser(dbm.getWeaponStat(AttackerWeapon));
        try {
            attacker.equipWeapon(weap);
        } catch (DataFormatException e) {
            throw new DataFormatException("This unit cannot equip this weapon.");
        }
        return DamageCalculator.savingThrow(attacker, target, dicerolls);
    }

    public static int getDamage(String AttackerName, String AttackerWeapon, String DefenderName, int dicenumbers) throws DataFormatException {
        Unit attacker = Parser.unitParser(dbm.getUnitStat(AttackerName));
        Unit target = Parser.unitParser(dbm.getUnitStat(DefenderName));
        Weapon weap = Parser.weaponParser(dbm.getWeaponStat(AttackerWeapon));
        try {
            attacker.equipWeapon(weap);
        } catch (DataFormatException e) {
            throw new DataFormatException("This unit cannot equip this weapon.");
        }
        return DamageCalculator.inflictDamage(attacker, target, dicenumbers);
    }

    public static boolean getIfOverKilled(String AttackerName, String AttackerWeapon, String DefenderName, int dicenumbers) throws DataFormatException {
        Unit attacker = Parser.unitParser(dbm.getUnitStat(AttackerName));
        Unit target = Parser.unitParser(dbm.getUnitStat(DefenderName));
        Weapon weap = Parser.weaponParser(dbm.getWeaponStat(AttackerWeapon));
        try {
            attacker.equipWeapon(weap);
        } catch (DataFormatException e) {
            throw new DataFormatException("This unit cannot equip this weapon.");
        }
        return DamageCalculator.checkOverKill(attacker, target, dicenumbers);
    }

    public static ArrayList<String> getUnitWeapon(String UnitName) {
        return dbm.getUnitWeapon(UnitName);
    }

    public static ArrayList<String> getAllUnitslist() {
        return dbm.getAllUnitslist();
    }
}
