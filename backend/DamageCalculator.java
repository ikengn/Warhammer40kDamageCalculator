import java.util.ArrayList;

/**
 * This is a class for performing the various calculations needed for calculating
 * attack damage in Warhammer 40K. For now, there will be one overarching method
 * called calculateDamage that takes two Units as arguments. The first Unit will be
 * the attacker, and the second Unit will be the defender. There will also be several
 * methods as part of this calculateDamage method, per the Warhammer 40K Attack Rules
 * found at:
 * https://www.warhammer-community.com/wp-content/uploads/2020/07/Lw4o3USx1R8sU7cQ.pdf
 *
 * These methods will be:
 * 1. hitRoll
 * 2. woundRoll
 * 3. allocateAttack
 * 4. savingThrow
 * 5. inflictDamage
 */
public class DamageCalculator {
	
	/*
	 * Hit Roll Phase Methods
	 * 
	 * From Warhammer 40K Rules PDF:
	 * "When a model makes an attack, make one hit roll for that attack by rolling one
	 * D6 (6-sided dice). If the result of the hit roll is equal to or greater than the
	 * attacking model's Ballistic Skill (BS) characteristic (if the attack is being
	 * made with a ranged weapon) or its Weapon Skill (WS) characteristic (if the attack
	 * is being made with a melee weapon), then that attack scores a hit against the
	 * target unit. If not, the attack fails and the attack sequence ends."
	 */
	/** 
	 * Perform a hit roll simulation given a list of dice rolls as input.
	 * 
	 * @param attacker - Unit that is performing the attacking
	 * @param diceRolls - ArrayList of int values representing dice rolls
	 * @return an ArrayList<Boolean> representing which dice rolls in the provided were
	 * high enough for the attacking Unit to successfully land a hit
	 */
	public static ArrayList<Boolean> hitRoll(Unit attacker, ArrayList<Integer> diceRolls){

		// ArrayList to be returned as described above
		ArrayList<Boolean> successes = new ArrayList<>();
		// If attacker is not holding a weapon, cannot attack, and every value of the
		// successes ArrayList should be false
		if(attacker.getWeapon() == null) {
			for(int i = 0; i < diceRolls.size(); i++) {
				successes.add(false);
			}
			return successes;
		}

		// perform hitRoll calculation for each diceRoll passed in by user
		for (Integer diceRoll : diceRolls) {
			// If attacker is holding a melee weapon, use attacker's Weapon Skill
			if (attacker.getWeapon().getType() == Type.Melee) {
				successes.add(diceRoll >= attacker.getWeaponSkill());
			}
			// If attacker is holding a non-melee weapon, use attacker's Ballistic Skill
			else {
				successes.add(diceRoll >= attacker.getBallisticSkill());
			}
		}
		// Return ArrayList containing which dice rolls resulted in successful hit rolls
		return successes;
	}

	/*
	 * Wound Roll Phase Methods
	 * 
	 * From Warhammer 40K Rules PDF:
	 * "Each time an attack scores a hit against a target unit, make a wound roll for
	 * that attack by rolling one D6 (6-sided die) to see if that attack successfully
	 * wounds the target. The result required is determined by comparing the attacking
	 * Unit's Strength (S) characteristic with the target's Toughness (T) characteristic."
	 */
	/**
	 * Private method to get the modified strength of the attacking unit.
	 *
	 * @param attacker attacking unit
	 * @return modified strength based on the unit's weapon
	 */
	private static int modifyStrength (Unit attacker) {
		int strength = attacker.getStrength();
		int wStrength = attacker.getWeapon().getStrength();
		Modifier strengthModifier = attacker.getWeapon().getStrengthModifier();
		switch (strengthModifier) {
			case user:
				break;
			case plus:
				strength += wStrength;
				break;
			case minus:
				strength -= wStrength;
				break;
			case mul:
				strength *= wStrength;
				break;
			case div:
				if(wStrength != 0){
					strength /= wStrength;
				}
				break;
			// When there is no modifier and the Weapon strength does not say "User",
			// you use the strength of the Weapon itself
			case noModifier:
				strength = wStrength;
				break;
		}
		return strength;
	}

	/**
	 * Perform a wound roll simulation given a list of dice rolls as input (assuming from the hitroll phase).
	 * 
	 * @param attacker - attacking Unit
	 * @param target - defending Unit
	 * @param diceRolls - ArrayList of int values representing dice rolls manually entered
	 * by the user
	 * @return an ArrayList<Boolean> representing which dice rolls provided a value high
	 * enough for the attacker's hit to successfully wound the target
	 */
	public static ArrayList<Boolean> woundRoll(Unit attacker, Unit target, ArrayList<Integer> diceRolls) {
		
		// ArrayList to be returned as described above
		ArrayList<Boolean> successes = new ArrayList<>();

		// getting modified strength
		int strength = modifyStrength(attacker);

		// Perform woundRoll calculation for each diceRoll passed in by user
		for (Integer diceRoll : diceRolls) {
			/*
			 * If the attacker's Strength is TWICE (or more than) the target's Toughness, a 2 or
			 * higher must be rolled to successfully wound the target.
			 */
			if(target.getToughness() == 0 || strength / target.getToughness() >= 2) {
				successes.add(diceRoll >= 2);
			}
			/*
			 * If the attacker's Strength is GREATER than the target's Toughness, a 3 or higher
			 * must be rolled to successfully wound the target.
			 */
			else if (strength > target.getToughness()) {
				successes.add(diceRoll >= 3);
			}
			/*
			 * If the attacker's Strength is HALF (or less than) the target's Toughness, a 6 or
			 * higher must be rolled to successfully wound the target.
			 */
			else if (strength == 0 || target.getToughness() / strength >= 2) {
				successes.add(diceRoll >= 6);
			}
			/*
			 * If the attacker's Strength is LOWER than the target's Toughness, a 5 or higher must
			 * be rolled to successfully wound the target.
			 */
			else if (strength < target.getToughness()) {
				successes.add(diceRoll >= 5);
			}
			/*
			 * If the attacker's Strength is EQUAL to the target's Toughness, a 4 or higher must
			 * be rolled to successfully wound the target.
			 */
			else if (strength == target.getToughness()){
				successes.add(diceRoll >= 4);
			}
		}
		// Return ArrayList containing which dice rolls resulted in successful wound rolls
		return successes;
	}
	
	/*
	 * Saving Throw Phase Methods
	 * 
	 * From Warhammer 40K Rules PDF:
	 * "The player commanding the target unit then makes one saving throw by rolling
	 * one D6 (6-sided die) and modifying the roll by the Armour Penetration (AP)
	 * characteristic of the weapon that the attack was made with. For example, if
	 * the weapon has an AP of -1, then 1 is subtracted from the saving throw roll.
	 * If the result is equal to, or greater than, the Save (Sv) characteristic of
	 * the model the attack was allocated to, then the saving throw is successful and
	 * the attack sequence ends. If the result is less than the model's Save
	 * characteristic, then the saving throw fails and the model suffers damage. An
	 * unmodified roll of 1 always fails."
	 */
	/**
	 * Perform a saving throw roll simulation given a list of dice rolls as input (assuming from the wound roll phase).
	 * 
	 * @param attacker - attacking Unit
	 * @param target - defending Unit
	 * @param diceRolls - ArrayList of int values representing dice rolls manually entered
	 * by the user
	 * @return an ArrayList<Boolean> representing which dice rolls provided a value high
	 * enough for the target to prevent damage for a given wound
	 */
	public static ArrayList<Boolean> savingThrow(Unit attacker, Unit target, ArrayList<Integer> diceRolls){
		// ArrayList to be returned as described above
		ArrayList<Boolean> successes = new ArrayList<Boolean>();

		// Perform savingThrow calculation for each diceRoll passed in by user
		for (Integer diceRoll : diceRolls) {
			/*
			 * If the Saving Throw dice roll modified by the Armor Penetration stat of the weapon
			 * the attacker's holding is greater than or equal to the Save stat of the target,
			 * then the Saving Throw is successful.
			 */
			successes.add(diceRoll + attacker.getWeapon().getArmorPenetration() >= target.getSave());
		}
		// Return ArrayList containing which dice rolls resulted in successful saving throws
		return successes;

	}

	/*
	 * Inflict Damage Phase Methods
	 * 
	 * From Warhammer 40K Rules PDF:
	 * "The damage inflicted is equal to the Damage (D) characteristic of the weapon making
	 * the attack. A model loses one wound for each point of damage it suffers. If a model's
	 * wounds are reduced to 0 or less, it is destroyed and removed from play. If a model
	 * loses several wounds from an attack and is destroyed, any excess damage inflicted by
	 * that attack is lost and has no effect."
	 */
	/**
	 * The inflictDamage method requires no dice to be rolled, so there is only this version
	 * of the method. If the attacker's weapon's damage characteristic is greater than the
	 * number of wounds the target has, then the damage dealt is set to the target's wounds
	 * (and any excess damage from the weapon disappears). Otherwise, the damage dealt is
	 * set to the weapon's damage characteristic
	 * 
	 * @param attacker - attacking Unit
	 * @param target - defending Unit
	 * @param hitCount number of times the attacker hit
	 * @return int value representing the amount of damage dealt to the target Unit
	 */
	public static int inflictDamage(Unit attacker, Unit target, int hitCount) {
		return Math.min(hitCount * attacker.getWeapon().getDamage(), target.getWounds());
	}

	/**
	 * Checking if the damage dealt is an overkill.
	 *
	 * @param attacker the attacking unit
	 * @param target the defending unit
	 * @param hitCount number of times the attacker hit
	 * @return true if the damage dealt is an overkill, false otherwise
	 */
	public static boolean checkOverKill(Unit attacker, Unit target, int hitCount) {
		int damageDealt = hitCount * attacker.getWeapon().getDamage();
		return damageDealt > target.getWounds();
	}

	/**
	 * This method calculate the total damage dealt from the shooting phase and the fight phase of one turn using
	 * the instructions from the Making Attack guide on page 18 of the pdf. (Have not accounted for special abilities).
	 * // TODO: incorporate abilities
	 *
	 * @param attacker the attacking unit
	 * @param target the defending unit
	 * @param hitRolls ArrayList of int values representing dice hit rolls manually entered by the user
	 * @param woundRolls ArrayList of int values representing dice wound rolls manually entered by the user
	 * @param savingRolls ArrayList of int values representing dice saving rolls manually entered by the user
	 * @return the total damage dealt during a turn, assuming the target is at full health
	 */
	public static int totalDamage(Unit attacker, Unit target, ArrayList<Integer> hitRolls,
								  ArrayList<Integer> woundRolls, ArrayList<Integer> savingRolls) {
		ArrayList<Boolean> hitSucc = hitRoll(attacker, hitRolls);
		ArrayList<Boolean> woundSucc = woundRoll(attacker, target, woundRolls);
		ArrayList<Boolean> savingSucc = savingThrow(attacker, target, savingRolls);
		assert hitSucc.size() == woundSucc.size() && woundSucc.size() == savingSucc.size();
		int dCount = 0;
		for (int i = 0; i < hitSucc.size(); i++) {
			if (hitSucc.get(i) && woundSucc.get(i) && !savingSucc.get(i)) {
				dCount++;
			}
		}
		return Math.min(dCount * attacker.getWeapon().getDamage(), target.getWounds());
	}

	/**
	 * This method calculate the total damage dealt from the shooting phase and the fight phase of one turn using
	 * the instructions from the Making Attack guide on page 18 of the pdf. (Have not accounted for special abilities).
	 * This method differs by letting the program simulates the dice rolls.
	 * // TODO: incorporate abilities
	 *
	 * @param attacker the attacking unit
	 * @param target the defending unit
	 * @return the total damage dealt during a turn, assuming the target is at full health
	 */
	public static int totalDamage(Unit attacker, Unit target) {
		int numDice = attacker.getWeapon().getNumHit();
		Dice dice1 = new Dice(numDice);
		ArrayList<Integer> hitRolls = dice1.rollDiceMul(numDice);
		Dice dice2 = new Dice(numDice + 1);
		ArrayList<Integer> woundRolls = dice2.rollDiceMul(numDice);
		Dice dice3 = new Dice(numDice + 2);
		ArrayList<Integer> savingRolls = dice3.rollDiceMul(numDice);
		return totalDamage(attacker, target, hitRolls, woundRolls, savingRolls);
	}

}
