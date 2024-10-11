package br.ufsc.gdev.zkirmisher.javaquest.entities;


import java.lang.Math;
import java.util.function.Function;

import br.ufsc.gdev.zkirmisher.javaquest.statistics.*;


/**
 * RPG Character abstraction for JavaQuest.
 */
public class Character {

	// CONSTANTS
	//stats
	public static final String STR = "STR";
	public static final String DEX = "DEX";
	public static final String INT = "INT";


	// ATTRIBUTES
	private String name;
	private int level = 0;

	private String role = "None";
	private StatCalculator calculator = new NullStatCalculator();

	private int healthPoints = 1;
	private int magicPoints = 0;

	private int poison = 0;

	private int strength = 0;
	private int dexterity = 0;
	private int intelligence = 0;

	private int baseStrength = 0;
	private int baseDexterity = 0;
	private int baseIntelligence = 0;

	private int armourModifier = 0;
	private int negationModifier = 0;

	//NOTE equipment array's size/indexing depend on subtype No./item subtype available at some Item.EQUIP_SUBTYPE
	private Equip[] equipment = new Equip[Item.EQUIP_SUBTYPES];

	//private ArrayList<Ability> abilities; //TODO Abilities
	//private ArrayList<Skill> skills; //TODO Skills


	// CONSTRUCTORS
	/**
	 * @param role  - fills role as "None" if null.
	 */
	public Character (
		final String name,
		int level,
		final String role
	) {
		setName(name);
		this.level = level;
		setRole(role);
	}

	/**
	 * @param role  - fills role as "None" if null.
	 */
	public Character(
		final String name,
		int level,
		final String role,
		final StatCalculator calculator,
		int str,
		int dex,
		int intel
	) {
		this(name, level, role);

		this.calculator = calculator;

		healthPoints = calculator.hp(level);
		magicPoints = calculator.mp(level);

		baseStrength = str;
		baseDexterity = dex;
		baseIntelligence = intel;

		strength = baseStrength;
		dexterity = baseDexterity;
		intelligence = baseIntelligence;
	}

	public Character(final Character other) {
		this(
			other.name,
			other.level,
			other.role,
			other.calculator,
			other.baseStrength,
			other.baseDexterity,
			other.baseIntelligence
		);

		healthPoints = other.healthPoints;
		magicPoints = other.magicPoints;

		poison = other.poison;

		strength = other.strength;
		dexterity = other.dexterity;
		intelligence = other.intelligence;

		armourModifier = other.armourModifier;
		negationModifier = other.negationModifier;

		equipment = other.equipment.clone();
	}


	// METHODS
	public String name() {
		return name;
	}

	/**
	 * @throws NullPointerException when name is null.
	 */
	public void setName(final String name) {
		if (name == null) {
			throw new NullPointerException("Character name can't be null.");
		}
		this.name = name;
	}

	public int level() {
		return level;
	}

	/**
	 * Increases level and fully restores Character.
	 */
	public void levelUp() {
		++level;
		rest();
	}

	public String role() {
		return role;
	}

	/**
	 * Sets Character's role.
	 * @param role  - fills role as "None" if null.
	 */
	public void setRole(final String role) {
		this.role = role != null ? role : "None";
	}

	public StatCalculator calculate() {
		return calculator;
	}

	/**
	 * Changes this Character Statistics Calculator.
	 * @param calculator  - uses NullObject pattern for default returns if null.
	 */
	public void setStatCalculator(final StatCalculator calculator) {
		if (calculator == null) {
			this.calculator = new NullStatCalculator();
		} else {
			int hpChange = calculator.hp(level) - this.calculator.hp(level);
			int mpChange = calculator.mp(level) - this.calculator.mp(level);

			healthPoints += hpChange;
			magicPoints += mpChange;

			this.calculator = calculator;
		}
	}

	public long experience() {
		return calculator.exp(level);
	}

	public int attackDamage() {
		return calculator.attack( strength() ) + attackEquipBonus();
	}

	public int spellDamage() {
		return calculator.spell( intelligence() ) + spellEquipBonus();
	}

	/**
	 * @return Raw critical value in mili%, use criticalRate() to get percentage value (0 to 100).
	 */
	public int critical() {
		return calculator.crit( dexterity() ) + criticalEquipBonus();
	}

	/**
	 * Returns critical chance going from 0 to 100.
	 */
	public float criticalRate() {
		return critical() / 1000f;
	}

	public int maxHealth() {
		return calculator.hp(level) + healthEquipBonus();
	}

	public int maxMagic() {
		return calculator.mp(level) + magicEquipBonus();
	}

	public int health() {
		return Math.min(healthPoints, maxHealth());
	}

	/**
	 * Increases the Character's current HP.
	 */
	public void heal(int healing) {
		setHealth(health() + healing);
	}

	/**
	 * Decreases the Character's current HP.
	 */
	public void wound(int damage) {
		setHealth(health() - damage);
	}

	public boolean isDead() {
		return health() <= 0 || poison >= 10;
	}

	public int magic() {
		return Math.min(magicPoints, maxMagic());
	}

	/**
	 * Increases the Character's current MP.
	 */
	public void restore(int mana) {
		setMagic(magic() + mana);
	}

	/**
	 * Decreases the Character's current MP.
	 */
	public void deplete(int manaCost) {
		setMagic(magic() - manaCost);
	}

	public int poisonCount() {
		return poison;
	}

	/**
	 * Increases the Character's poison count by one.
	 */
	public void wither() {
		poison = poison < 10 ? poison + 1 : poison;
	}

	/**
	 * Removes all poison from Character.
	 */
	public void remedy() {
		poison = 0;
	}

	/**
	 * @return Character's base stat without equipment or modifiers.
	 */
	public int baseStrength() {
		return baseStrength;
	}

	public void setBaseStrength(int str) {
		int change = str - baseStrength;
		baseStrength = str;
		strength += change;
	}

	/**
	 * @return Character's base stat withouth equipment or modifiers.
	 */
	public int baseDexterity() {
		return baseDexterity;
	}

	public void setBaseDexterity(int dex) {
		int change = dex - baseDexterity;
		baseDexterity = dex;
		dexterity += change;
	}

	/**
	 * @return Character's base stat withouth equipment or modifiers.
	 */
	public int baseIntelligence() {
		return baseIntelligence;
	}

	public void setBaseIntelligence(int intel) {
		int change = intel - baseIntelligence;
		baseIntelligence = intel;
		intelligence += change;
	}

	/**
	 * @return Current STR with any modifier.
	 */
	public int strength() {
		return strength + strengthEquipBonus();
	}

	/**
	 * @return Current DEX with any modifier.
	 */
	public int dexterity() {
		return dexterity + dexterityEquipBonus();
	}

	/**
	 * @return Current INT with any modifier.
	 */
	public int intelligence() {
		return intelligence + intelligenceEquipBonus();
	}

	/**
	 * Modifies attributes by given argument values.
	 */
	public void addModifier(int str, int dex, int intel, int armour, int negation) {
		strength += str;
		dexterity += dex;
		intelligence += intel;
		armourModifier += armour;
		negationModifier += negation;
	}

	public void removeModifier(int str, int dex, int intel, int armour, int negation) {
		addModifier(-1*str, -1*dex, -1*intel, -1*armour, -1*negation);
	}

	/**
	 * Fully restores the Character:
	 * <p>
	 * Recovers HP and MP,
	 * removes all poison counters and
	 * any non-equipment modifiers.
	 */
	public void rest() {
		healthPoints = maxHealth();
		magicPoints = maxMagic();
		poison = 0;
		strength = baseStrength;
		dexterity = baseDexterity;
		intelligence = baseIntelligence;
		armourModifier = 0;
		negationModifier = 0;
	}

	/**
	 * Uses an Item.
	 */
	public void use(Item item) {
		item.getUseFunction().accept(this);
	}

	public boolean canEquip(final Equip equip) {
		return equip.canBeEquiped(this);
	}

	/**
	 * @return Physical Damage reduction.
	 */
	public int armour() {
		return equipBonus(Equip::armourBonus) + armourModifier;
	}

	/**
	 * @return Magic Damage reduction.
	 */
	public int negation() {
		return equipBonus(Equip::negationBonus) + negationModifier;
	}

	public int strengthEquipBonus() {
		return equipBonus(Equip::strengthBonus);
	}

	public int dexterityEquipBonus() {
		return equipBonus(Equip::dexterityBonus);
	}

	public int intelligenceEquipBonus() {
		return equipBonus(Equip::intelligenceBonus);
	}

	public int attackEquipBonus() {
		return equipBonus(Equip::attackBonus);
	}

	public int spellEquipBonus() {
		return equipBonus(Equip::spellBonus);
	}

	public int criticalEquipBonus() {
		return equipBonus(Equip::criticalBonus);
	}

	public int healthEquipBonus() {
		return equipBonus(Equip::healthBonus);
	}

	public int magicEquipBonus() {
		return equipBonus(Equip::magicBonus);
	}

	/**
	 * @param position - uses Item.EQUIP_SUBTYPE as index.
	 * @return Item equipped at the specified position, null when empty.
	 *
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Equip getEquipAt(int position) {
		return equipment[position];
	}

	/**
	 * @return Whether or not Character is equipping a specific item.
	 */
	public boolean isEquipping(final Equip item) {
		for (Equip equip : equipment) {
			if (item == null && equip == null) {
				return true;
			} else if (item != null && item.equals(equip)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Equips an item to the Character if and only if the Character meets all the Equip's requirements
	 * and there is nothing else already equipped on that item's equipment slot.
	 *
	 * @return true if character actually equips the item, otherwise false.
	 *
	 * @throws ArrayIndexOutOfBoundsException when the subtype digit of the Item's ID is illegal.
	 */
	public boolean equip(final Equip item) {
		if (!canEquip(item)) {
			return false;
		}

		try {
			if (getEquipAt(item.subtype()) != null) {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Item ID equip subtype indicator digit is invalid.");
		}

		equipment[item.subtype()] = item;
		return true;
	}

	/**
	 * Unequips item at the specified index.
	 *
	 * @param position - uses Item.EQUIP_SUBTYPE as index.
	 *
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void unequip(int position) {
		equipment[position] = null;
	}

	/**
	 * Unequips a specific item.
	 *
	 * @return true if the item was equipped on the first place.
	*/
	public boolean unequip(final Equip item) {
		if (item == null || !isEquipping(item)) {
			return false;
		}

		unequip(item.subtype());
		return true;
	}

    @Override
	public String toString() {
		return String.format(//TODO translate back
			"%s" +
			"%s - LVL %d %s\n" +
			"HP: %d / %d\n" +
			"MP: %d / %d\n" +
			"%d contadores de veneno\n" +
			"STR: %d (%d) (%d)\tAD: %d\n" +
			"DEX: %d (%d) (%d)\tCR: %.2f%%\n" +
			"INT: %d (%d) (%d)\tSD: %d\n" +
			"Armadura: %d (%d)\tNegação Mágica: %d (%d)",
			isDead() ? "[MORTO]\n" : "",
			/*"%s" +
			"%s - LVL %d %s\n" +
			"HP: %d / %d\n" +
			"MP: %d / %d\n" +
			"%d poison\n" +
			"STR: %d (%d) (%d)\tAD: %d\n" +
			"DEX: %d (%d) (%d)\tCR: %.2f%%\n" +
			"INT: %d (%d) (%d)\tSD: %d\n" +
			"Armour: %d (%d)\tNegation: %d (%d)",
			isDead() ? "[DEAD]\n" : "",*/
			name, level, role,
			health(), maxHealth(),
			magic(), maxMagic(),
			poison,
			strength(), strengthEquipBonus(), strength() - (baseStrength + strengthEquipBonus()), attackDamage(),
			dexterity(), dexterityEquipBonus(), dexterity() - (baseDexterity + dexterityEquipBonus()), criticalRate(),
			intelligence(), intelligenceEquipBonus(), intelligence() - (baseIntelligence + intelligenceEquipBonus()), spellDamage(),
			armour(), armourModifier, negation(), negationModifier
		);
	}


	// PRIVATE METHODS
	private void setHealth(int hp) {
		healthPoints = limit(hp, 0, maxHealth());
	}

	private void setMagic(int mp) {
		magicPoints = limit(mp, 0, maxMagic());
	}

	/**
	 * @param bonusFunction - getter function from Equip class for specific bonus.
	 * @return Sum of all the bonus of specified type given from equipment.
	 */
	private int equipBonus(Function<Equip, Integer> bonusFunction) {
		int total = 0;

		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += bonusFunction.apply(item);
		}

		return total;
	}


	// CLASS FUNCTIONS
	/**
	 * Limits value to a specific range.
	 *
	 * @param floor  - lower bound.
	 * @param ceil  - upper bound.
	 * @param value  - original value.
	 *
	 * @return If value is already in range, returns it as it is;
	 * else, returns the limit it has gone through.
	 */
	private static int limit(int value, int floor, int ceil) {
		if (value <= ceil && value >= floor) {
			return value;

		} else if (value < floor) {
			return floor;
		}

		return ceil;
	}

}
