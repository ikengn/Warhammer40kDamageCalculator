import java.util.zip.DataFormatException;

/**
 * This class represents the Warhammer unit object.
 */
public class Unit {

    private final String name;
	private final int movement;	        // (M)
	private final Integer weaponSkill;      // (WS)
	private final Integer ballisticSkill;   // (BS)
	private final int strength;		    // (S)
	private final int toughness;	    // (T)
	private final int wounds;		    // (W)
	private final int attacks;          // (A)
	private final int leaderShip;		// (Ld)
	private final Integer save;			    // (Sv)
	/*
	 * Weapon that the model is equippped with. Default value is NULL. Weapons must be
	 * manually equipped using the equipWeapon() method below.
	 */
	private Weapon weapon;

    /**
     * Default constructor for the unit class.
     *
     * @param name name of unit; immutable
     * @param movement speed at which a model moves
     * @param weaponSkill skill level at close combat, 0 for ranged unit
     * @param ballisticSkill accuracy when using ranged weapon, 0 for melee unit
     * @param strength strength of the unit
     * @param toughness how tough is the unit
     * @param wounds the amount of damage the unit can sustain
     * @param attacks number of times the unit can strike
     * @param leaderShip leadership point of the unit
     * @param save protection of the unit
     */
	public Unit(String name, int movement, Integer weaponSkill, Integer ballisticSkill,
                int strength, int toughness, int wounds, int attacks, int leaderShip,
                Integer save) {
        this.name = name;
        // Constructor will not handle error with ID formatting
        this.movement = movement;
        this.weaponSkill = weaponSkill;
        this.ballisticSkill = ballisticSkill;
        this.strength = strength;
        this.toughness = toughness;
        this.wounds = wounds;
        this.attacks = attacks;
        this.leaderShip = leaderShip;
        this.save = save;
        /*
         *  By default, Unit does not hold a weapon upon instantiation, and the weapon
         *  field is set to null by the constructor.
         */
        this.weapon = null;
    }

    /**
     * Equip this unit with a weapon.
     *
     * @param weapon weapon to be equipped
     */
	public void equipWeapon(Weapon weapon) throws DataFormatException {
        // checking if the weapon is equipable
        if (weapon.getType() == Type.Melee && this.getWeaponSkill() == null) {
            throw new DataFormatException("Unable to equip melee weapon to this unit");
        } else if (weapon.getType() != Type.Melee && this.getBallisticSkill() == null) {
            throw new DataFormatException("Unable to equip ranged weapon to this unit");
        }
		this.weapon = weapon;
	}
	
	/**
	 * Removes the Weapon that the Unit is currently holding. The Unit's weapon field
	 * is set to null when this method is called.
	 */
	public void removeWeapon() {
		this.weapon = null;
	}

    ///////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public int getMovement() {
        return movement;
    }

    public Integer getWeaponSkill() {
        return weaponSkill;
    }

    public Integer getBallisticSkill() {
        return ballisticSkill;
    }

    public int getStrength() {
        return strength;
    }

    public int getToughness() {
        return toughness;
    }

    public int getWounds() {
        return wounds;
    }

    public int getAttacks() {
        return attacks;
    }

    public int getLeaderShip() {
        return leaderShip;
    }

    public Integer getSave() {
        return save;
    }

    public Weapon getWeapon() {
        return weapon;
    }
	
}




