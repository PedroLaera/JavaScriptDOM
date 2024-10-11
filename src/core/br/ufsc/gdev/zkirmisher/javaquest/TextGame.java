package br.ufsc.gdev.zkirmisher.javaquest;


import br.ufsc.gdev.zkirmisher.javaquest.entities.Player;


/**
 * Contains the current state of the game.
 */
public class TextGame {

	// ATTRIBUTES
	private Room currentRoom;
	private Player player;
	private Room finalRoom;


	// CONSTRUCTORS
	public TextGame(
		final Room start,
		final Room end,
		final Player player
	) {
		this.currentRoom = start;
		this.finalRoom = end;
		this.player = player;
	}

	public TextGame(final Room start, final Room end) {
		this(start, end, null);
	}


	// METHODS
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(final Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	/**
	 * @param direction - use Room.DIRECTION as direction.
	 * @return true if the player could move to that direction.
	 */
	public boolean goToAdjacent(int direction) {
		try {
			if (currentRoom.getAdjacent(direction) == null) {
				return false;
			} else {
				currentRoom = currentRoom.getAdjacent(direction);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

	public boolean hasLost() {
		return player.isDead();
	}

	public boolean hasWon() {
		return currentRoom == finalRoom;
	}

	public Room getFinalRoom() {
		return finalRoom;
	}

	public void setFinalRoom(final Room finalRoom) {
		this.finalRoom = finalRoom;
	}

}
