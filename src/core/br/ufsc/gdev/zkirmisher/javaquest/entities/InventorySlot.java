package br.ufsc.gdev.zkirmisher.javaquest.entities;


/**
 * Inventory Slot for holding Items / stacks of Items.
 */
public class InventorySlot implements Comparable<InventorySlot> {

	// ATTRIBUTES
	private Item item = null;
	private int stack = 0;


	// CONSTRUCTORS
	public InventorySlot() {}

	public InventorySlot(final Item item, int number) {
		this.item = item;
		stack = number;
	}

	public InventorySlot(final InventorySlot other) {
		this(
			other.item != null ? new Item(other.item) : null,
			other.stack
		);
	}


	// METHODS
	/**
	 * @return Which item is stored on this slot, null if empty.
	 */
	public Item item() {
		return item;
	}

	/**
	 * @return How many items there are on this slot.
	 */
	public int stack() {
		return stack;
	}

	public boolean isEmpty() {
		return item == null && stack == 0;
	}

	public boolean isFull() {
		if (isEmpty()) {
			return false;
		} else {
			return (stack >= item.maxCarry()) ? true : false;
		}
	}

	/**
	 * Clears this slot, gets called when stack is empty.
	 */
	public void clear() {
		item = null;
		stack = 0;
	}

	/**
	 * Removes a specific number of stored Items from this slot.
	 * If the stack then falls down to zero or less, the slot is cleared.
	 */
	public void remove(int number) {
		if (isEmpty()) return;

		if (number >= 0) {
			stack -= number;

			if (stack <= 0) {
				clear();
			}

		} else {
			add(-1 * number);
		}
	}

	/**
	 * Adds a specific number of items to this slot.
	 * Doesn't work after reaching the Item's maximum stack size.
	 */
	public void add(int number) {
		if (isEmpty()) return;

		if (number >= 0) {
			stack += number;

			if (stack > item.maxCarry()) {
				stack = item.maxCarry();
			}

		} else {
			remove(-1 * number);
		}
	}

	/**
	 * Adds a number of specific items to this slot,
	 * overwriting what was there originally if the items are different and
	 * adding some more if they are the same.
	 */
	public void add(final Item item, int number) {
		if (item == null) {
			clear();
			return;
		}

		if (!item.equals(this.item)) {
			clear();
			this.item = item;
		}

		add(number);
	}

	public boolean contains(final Item item) {
		return item == null ? isEmpty() : item.equals(this.item);
	}

	/**
	 * Check if this slot holds an item of a specific ID.
	 */
	public boolean contains(int itemID) {
		return !isEmpty() ? item.id() == itemID : false;
	}

	@Override
    public String toString() {
    	if (isEmpty()) {
    		return "EMPY";
    	} else {
	        return String.format("x%d %s", stack, item.name());
    	}
    }

	public boolean equals(final InventorySlot otherSlot) {
		return otherSlot == null ? false : contains(otherSlot.item) &&
											 stack == otherSlot.stack;
	}

	/**
	 * NOTE: Compares slot by item(id), breaks tie by stack size.
	 */
    @Override
	public int compareTo(final InventorySlot otherSlot) {
    	if (isEmpty()) {
    		return otherSlot.isEmpty() ? 0 : -1;

    	} else if (otherSlot.isEmpty()) {
    		return 1;

    	} else {
    		int i = item.compareTo(otherSlot.item);
    		return i == 0 ? Integer.valueOf(stack).compareTo(Integer.valueOf(otherSlot.stack)) : i;
    	}
    }

}
