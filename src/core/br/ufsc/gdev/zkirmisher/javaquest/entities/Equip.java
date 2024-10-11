package br.ufsc.gdev.zkirmisher.javaquest.entities;


/**
 * Equip subtype of Item.
 * <p>
 * NOTE: By default, using an equip will equip it to the character;
 * using the same one will then unequip it.
 */
public class Equip extends Item implements Cloneable {

	// ATTRIBUTES
	//bonuses
	private int strengthBonus, dexterityBonus, intelligenceBonus = 0;
	private int attackBonus, spellBonus, criticalBonus = 0;
	private int armourBonus, negationBonus = 0;
	private int healthBonus, magicBonus = 0;

	//requirements
	private int levelRequirement = 0;
	private String roleRequirement = "";
	private int strengthRequirement, dexterityRequirement, intelligenceRequirement = 0;


	// CONSTRUCTORS
	/**
	 * @param id - must be adequate for equipment, use Item.EQUIP as type digit.
	 * @param description - fills description as empty String if null.
	 */
	public Equip(
		final String name, int id,
		final String description
	) {
		super(name, id, description);

		//default equipment use function
		super.setUseFunction(
			user -> {
				if (user.isEquipping(this)) {
					user.unequip(this);
				} else {
					user.equip(this);
				}
			}
		);
	}

	/**
	 * @param id - must be adequate for equipment, use Item.EQUIP as type digit.
	 */
	public Equip(
		final String name, int id,
		final String description,
		int strengthBonus, int dexterityBonus, int intelligenceBonus,
		int attackBonus, int spellBonus, int criticalBonus,
		int armourBonus, int negationBonus,
		int healthBonus, int magicBonus,
		int levelRequirement, final String roleRequirement,
		int strengthRequirement, int dexterityRequirement, int intelligenceRequirement
	) {
		this(name, id, description);

		this.strengthBonus = strengthBonus;
		this.dexterityBonus = dexterityBonus;
		this.intelligenceBonus = intelligenceBonus;
		this.attackBonus = attackBonus;
		this.spellBonus = spellBonus;
		this.criticalBonus = criticalBonus;
		this.armourBonus = armourBonus;
		this.negationBonus = negationBonus;
		this.healthBonus = healthBonus;
		this.magicBonus = magicBonus;

		this.levelRequirement = levelRequirement;
		setRoleRequirement(roleRequirement);
		this.strengthRequirement = strengthRequirement;
		this.dexterityRequirement = dexterityRequirement;
		this.intelligenceRequirement = intelligenceRequirement;
	}

	//copy constructor
	public Equip(final Equip other) {
		this(
			other.name(), other.id(),
			other.description(),
			other.strengthBonus, other.dexterityBonus, other.intelligenceBonus,
			other.attackBonus, other.spellBonus, other.criticalBonus,
			other.armourBonus, other.negationBonus,
			other.healthBonus, other.magicBonus,
			other.levelRequirement, other.roleRequirement,
			other.strengthRequirement, other.dexterityRequirement, other.intelligenceRequirement
		);
		setUseFunction(other.getUseFunction());
	}


	// METHODS
	public boolean canBeEquiped(final Character character) {
		return	character.strength() >= strengthRequirement &&
				character.dexterity() >= dexterityRequirement &&
				character.intelligence() >= intelligenceRequirement &&
				character.level() >= levelRequirement &&
				character.role().toLowerCase().contains(roleRequirement.toLowerCase());
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

	// automatically generated getters & setters

	public int strengthBonus() {
		return strengthBonus;
	}

	public void setStrengthBonus(int strengthBonus) {
		this.strengthBonus = strengthBonus;
	}

	public int dexterityBonus() {
		return dexterityBonus;
	}

	public void setDexterityBonus(int dexterityBonus) {
		this.dexterityBonus = dexterityBonus;
	}

	public int intelligenceBonus() {
		return intelligenceBonus;
	}

	public void setIntelligenceBonus(int intelligenceBonus) {
		this.intelligenceBonus = intelligenceBonus;
	}

	public int attackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}

	public int spellBonus() {
		return spellBonus;
	}

	public void setSpellBonus(int spellBonus) {
		this.spellBonus = spellBonus;
	}

	/**
	 * NOTE: expresses criticalBonus as raw critical (mili%) integer.
	 */
	public int criticalBonus() {
		return criticalBonus;
	}

	/**
	 * @param criticalBonus - raw critical (mili%) int value.
	 */
	public void setCriticalBonus(int criticalBonus) {
		this.criticalBonus = criticalBonus;
	}

	public int armourBonus() {
		return armourBonus;
	}

	public void setArmourBonus(int armourBonus) {
		this.armourBonus = armourBonus;
	}

	public int negationBonus() {
		return negationBonus;
	}

	public void setNegationBonus(int negationBonus) {
		this.negationBonus = negationBonus;
	}

	public int healthBonus() {
		return healthBonus;
	}

	public void setHealthBonus(int healthBonus) {
		this.healthBonus = healthBonus;
	}

	public int magicBonus() {
		return magicBonus;
	}

	public void setMagicBonus(int magicBonus) {
		this.magicBonus = magicBonus;
	}

	public int levelRequirement() {
		return levelRequirement;
	}

	public void setLevelRequirement(int levelRequirement) {
		this.levelRequirement = levelRequirement;
	}

	public String roleRequirement() {
		return roleRequirement;
	}

	/**
	 * Sets Equip's required Role;
	 * @param description - fills description as empty String if null.
	 */
	public void setRoleRequirement(final String roleRequirement) {
		this.roleRequirement = roleRequirement != null ? roleRequirement : "";
	}

	public int strengthRequirement() {
		return strengthRequirement;
	}

	public void setStrengthRequirement(int strengthRequirement) {
		this.strengthRequirement = strengthRequirement;
	}

	public int dexterityRequirement() {
		return dexterityRequirement;
	}

	public void setDexterityRequirement(int dexterityRequirement) {
		this.dexterityRequirement = dexterityRequirement;
	}

	public int intelligenceRequirement() {
		return intelligenceRequirement;
	}

	public void setIntelligenceRequirement(int intelligenceRequirement) {
		this.intelligenceRequirement = intelligenceRequirement;
	}

}
