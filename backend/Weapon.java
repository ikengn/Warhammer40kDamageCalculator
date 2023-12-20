/**
 * Class representing a Weapon to be held by an individual unit.
 *
 */
public class Weapon {

	private final String name;
	private final int range;
	private final Type type;
	private final int numHit;
	private final Modifier strengthMod;
	private final Integer strength;		// (S)
	private final Integer armorPenetration;// (AP)
	private final int damage;			 // (D)
	private final String specialAbility;

	/**
	 * Constructor for the weapon class
	 *
	 * @param name name of weapon; immutable
	 * @param range range of the weapon
	 * @param type type of the weapon
	 * @param numHit number of times the weapon can hit
	 * @param strengthMod modifier of the strength stat(user, +, -, *)
	 * @param strength how likely to inflict damage
	 * @param armorPenetration how good it is to penetrate armore
	 * @param damage the amount of damage inflicted
	 * @param specialAbility ability of the weapon
	 */
	public Weapon(String name, int range, Type type, int numHit, Modifier strengthMod,
			Integer strength, Integer armorPenetration, int damage, String specialAbility) {
		this.name = name;
		// Constructor will not handle error with ID formatting
		this.range = range;
		this.type = type;
		this.numHit = numHit;
		this.strengthMod = strengthMod;
		this.strength = strength;
		this.armorPenetration = armorPenetration;
		this.damage = damage;
		this.specialAbility = specialAbility;
	}

	///////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}

	public int getRange() {
		return range;
	}

	public Type getType() {
		return type;
	}

	public int getNumHit() {
		return numHit;
	}

	public Modifier getStrengthModifier(){
		return strengthMod;
	}

	public int getStrength() {
		return strength;
	}

	public int getArmorPenetration() {
		return armorPenetration;
	}

	public int getDamage() {
		return damage;
	}

	public String getSpecialAbility() {
		return specialAbility;
	}
}




