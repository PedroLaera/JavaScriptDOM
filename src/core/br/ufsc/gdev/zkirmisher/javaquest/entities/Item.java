package br.ufsc.gdev.zkirmisher.javaquest.entities;


import java.util.function.Consumer;


/**
 * Base Item class for JavaQuest.
 * <p>
 * NOTE: Item ID number formatted as tsxx;
 * t: Item.TYPE; s: Item.SUBTYPE; xx -> Number.
 */
public class Item implements Comparable<Item>, Cloneable {

	// CONSTANTS
	/**
	 * Item Types
	 */
	public static final int TYPES = 3;
		public static final int ETC = 0;
		public static final int USE = 1;
		public static final int EQUIP = 2;

	/**
	 * Equip Subtypes
	 */
	public static final int EQUIP_SUBTYPES = 7;
		public static final int EQUIP_WEAPON = 0;
		public static final int EQUIP_BODY = 1;
		public static final int EQUIP_HEAD = 2;
		public static final int EQUIP_LEGS = 3;
		public static final int EQUIP_BACK = 4;
		public static final int EQUIP_HAND = 5;
		public static final int EQUIP_FEET = 6;


	// ATTRIBUTES
	private String name;
	private int id;
	private String description = "";

	//functional attribute
	private Consumer<Character> useFunction = u -> { return; };


	// CONSTRUCTORS
	public Item(
		final String name, int id
	) {
		setName(name);
		this.id = id;
	}

	/**
	 * @param description - fills description as empty String if null.
	 */
	public Item(
		final String name, int id,
		final String description
	) {
		this(name, id);
		setDescription(description);
	}

	/**
	 * @param useFunction - void lambda expression that takes a Character as single argument.
	 */
	public Item(
		final String name, int id,
		final String description,
		Consumer<Character> useFunction
	) {
		this(name, id, description);
		this.useFunction = useFunction;
	}

	public Item(final Item other) {
		this(
			other.name,
			other.id,
			other.description,
			other.useFunction
		);
	}


	// METHODS
	public int id() {
		return id;
	}

	public int type() {
		return id / 1000;
	}

	public int subtype() {
		return id % 1000 / 100;
	}

	public int num() {
		return id % 100;
	}

	public int maxCarry() {
		switch( type() ) {
			case Item.EQUIP:
				return 1;

			case Item.USE:
				return 15;

			case Item.ETC: default:
				//see below
				break;
		}

		return 30;
	}

	public String name() {
		return name;
	}

	public void setName(final String name) {
		if (name == null) {
			throw new NullPointerException("Item name can't be null.");
		}
		this.name = name;
	}

	public String description() {
		return description;
	}

	/**
	 * Sets Item description.
	 * @param description - fills description as empty String if null.
	 */
	public void setDescription(final String description) {
		this.description = description != null ? description : "";
	}

	/**
	 * @return lambda expression that takes a Character as single argument.
	 */
	public Consumer<Character> getUseFunction() {
		return useFunction;
	}

	/**
	 * @param useFunction - lambda expression that takes a Character as single argument.
	 */
	public void setUseFunction(Consumer<Character> useFunction) {
		this.useFunction = useFunction;
	}

    @Override
    public String toString() {
		return String.format(
			"[%d.%d.%d] %s\n" +
			"%s",
			type(), subtype(), num(), name,
			description
		);
	}

	/**
	 * Compares by ID number.
	 */
	public boolean equals(final Item otherItem) {
		return otherItem == null ? false : id == otherItem.id;
	}

	/**
	 * Compares by ID number.
	 */
    @Override
	public int compareTo(final Item otherItem) {
		return Integer.valueOf(id).compareTo(Integer.valueOf(otherItem.id));
    }

    @Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

}
