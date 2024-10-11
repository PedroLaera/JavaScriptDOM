package br.ufsc.gdev.zkirmisher.javaquest;


import br.ufsc.gdev.zkirmisher.javaquest.entities.Inventory;
import br.ufsc.gdev.zkirmisher.javaquest.entities.Character;


/**
 * Text-based interactive JavaQuest room.
 */
public class Room {

	// CONSTANTS
	private static final int DIRECTIONS = 4;
		public static final int NORTH = 0;
		public static final int EAST = 1;
		public static final int SOUTH = 2;
		public static final int WEST = 3;


	// ATTRIBUTES
	private String description;
	private Room[] adjacent;

	private Inventory loot = new Inventory(20); //XXX Perhaps not the best inventory size for a room.
	private Character occupant;


	// CONSTRUCTORS
	public Room(
		final String description,
		final Room[] adjacent,
		final Character occupant
	) {
		setDescription(description);
		this.adjacent = adjacent;
		this.occupant = occupant;
	}

	public Room(
		final String description,
		final Room[] adjacent
	) {
		this(description, adjacent, null);
	}

	public Room(final String description) {
		this(description, new Room[DIRECTIONS]);
	}


	// METHODS
	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = (description == null) ? "" : description;
	}

	/**
	 * @param direction - use Room.DIRECTION as direction.
	 * @return adjacent Room on that direction.
	 *
	 * @throws ArrayIndexOutOfBoundsException When direction is invalid.
	 */
	public Room getAdjacent(int direction) {
		return adjacent[direction];
	}

	/**
	 * Creates a new path from this room to another.
	 *
	 * @param room - new room to go to.
	 * @param direction - use Room.DIRECTION as direction.
	 *
	 * @throws ArrayIndexOutOfBoundsException When direction is invalid.
	 */
	public void setAdjacent(final Room room, int direction) {
		adjacent[direction] = room;
	}

	public Inventory loot() {
		return loot;
	}

	public Character getOccupant() {
		return occupant;
	}

	public void setOccupant(final Character occupant) {
		this.occupant = occupant;
	}

}
