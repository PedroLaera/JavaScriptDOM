package br.ufsc.gdev.zkirmisher.javaquest.entities;


import java.lang.Math;

import br.ufsc.gdev.zkirmisher.util.Arrays;


/**
 * A bag for holding Items.
 */
public class Inventory {

	// ATTRIBUTES
	private InventorySlot[] content;


	// CONSTRUCTORS
	public Inventory(int size) {
		content = new InventorySlot[size];

		//array needs initialization
		for (int i = 0; i < content.length; ++i) {
			content[i] = new InventorySlot();
		}
	}

	public Inventory() {
		this(Item.TYPES);
	}


	// METHODS
	public int size() {
		return content.length;
	}

	/**
	 * Sorts and rescales the inventory to a new size, may remove some items.
	 */
	public void setSize(int newSize) {
		sort();

		InventorySlot[] newContent = new InventorySlot[newSize];

		for (int i = 0; i < newContent.length; ++i) {
			if (i < content.length) {
				newContent[i] = content[i];
			} else {
				newContent[i]= new InventorySlot();
			}
		}

		content = newContent;
	}

	/**
	 * @return Item at this inventory index. Null if empty.
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Item get(int index) {
		return content[index].item();
	}

	/**
	 * @return How many items there are on this slot.
	 */
	public int getStack(int index) {
		return content[index].stack();
	}

	/**
	 * Swaps two items' position on the inventory. Works with empty slots.
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void swap(int firstIndex, int secondIndex) {
		InventorySlot temp = content[firstIndex];
		content[firstIndex] = content[secondIndex];
		content[secondIndex] = temp;
	}

	/**
	 * Fills inventory with a number of items WHILE THEY FIT.
	 * Does not work with negative values or null Items.
	 * <p>
	 * It is safer to use add(item) to check changes after each addition.
	 *
	 * @return true if the inventory was changed as a result, otherwise false.
	 */
	public boolean add(final Item item, int number) {
		if (number <= 0 || item == null) {
			return false;
		}

		int count = number;

		for (int s = 0; s < content.length && count > 0; ++s) {
			if ( (!content[s].isEmpty() && !content[s].contains(item)) || content[s].isFull()) {
				continue;
			}

			int change = 0;

			if (content[s].isEmpty()) {
				change = Math.min(count, item.maxCarry());
			} else if (content[s].contains(item)) {
				change = Math.min(count, item.maxCarry() - content[s].stack());
			}

			content[s].add(item, change);
			count -= change;
		}

		if (count < number) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds a single item to the inventory if it fits.
	 * @return true if the inventory was changed as a result, otherwise false.
	 */
	public boolean add(final Item item) {
		return add(item, 1);
	}

	//TODO addAll() @https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html#addAll-java.util.Collection-

	/**
	 * Empties this slot.
	 */
	public void remove(int index) {
		content[index].clear();
	}

	/**
	 * Removes a specific number of stored items from this slot.
	 */
	public void remove(int index, int number) {
		content[index].remove(number);
	}

	/**
	 * Removes the first occurrence of the specified Item, if there are any.
	 * @return True if the Inventory contained the specified Item.
	 */
	public boolean remove(final Item item) {
		int index = indexOf(item);

		if (index < 0) {
			return false;
		} else {
			remove(index, 1);
			return true;
		}
	}

	/**
	 * Removes a certain number of an Item from inventory until either
	 * there are none left or the desired number of removals is reached.
	 */
	public void remove(final Item item, int number) {
		if (number <= 0) {
			return;
		}

		int removed = 0;
		while (removed < number) {
			if (!remove(item)) {
				break;
			}
			++removed;
		}
	}

	//TODO removeAll() @https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html#removeAll-java.util.Collection-

	public void sort() {
		Arrays.bubbleSort(content);
		Arrays.reverse(content);
	}

	/**
	 * @return True if inventory contains at least one Item with specified ID.
	 */
	public boolean contains(int itemID) {
		for (InventorySlot slot : content) {
			if (slot.contains(itemID)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return True if inventory contains at least one Item that equals the specified one.
	 */
	public boolean contains(final Item item) {
		return (item != null) ? contains(item.id()) : contains(item, 1);
	}

	/**
	 * @return True if inventory contains at least the specified number of an item.
	 */
	public boolean contains(final Item item, int number) {
		return count(item) >= number;
	}

	//TODO containsAll() @https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html#containsAll-java.util.Collection-

	/**
	 * @return The index of the first occurrence of the specified element, -1 if it's not present.
	 */
	public int indexOf(final Item item) {
		for (int i = 0; i < content.length; ++i) {
			if (content[i].contains(item)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @return The index of the first occurrence of an Item with the specified ID, -1 if not found.
	 */
	public int indexOf(int itemID) {
		for (int i = 0; i < content.length; ++i) {
			if (content[i].contains(itemID)) {
				return i;
			}
		}
		return -1;
	}

	public boolean isEmpty() {
		for (InventorySlot slot : content) {
			if (!slot.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
    public String toString() {
		String list = "";

		for (int s = 0; s < content.length; ++s) {
			if (!content[s].isEmpty()) {
				list += String.format("[%d] %s\n%s\n", s, content[s].toString(), content[s].item().description());
			}
		}

		return list;
    }


	// PRIVATE FUNCTIONS
	/**
	 * Counts how many of a specific item there are on the inventory.
	 * @return Number of times it appears (considering stacks of items).
	 */
	private int count(final Item item) {
		int n = 0;
		for (InventorySlot slot : content) {
			if (slot.contains(item)) {
				n += slot.stack();
			}
		}
		return n;
	}

}
