package br.ufsc.gdev.zkirmisher.util;


/**
 * OUT OF DATE, SHOULD IMPORT FROM .../POO/utilities
 */


/**
 * Custom Arrays implementation with static utility functions.
 */
public class Arrays {

	/**
	 * Worst case: O(n^2)
	 * <p>
	 * Best case: O(n)
	 * <p>
	 * Space: O(1)
	 */
	public static <T extends Comparable<T>> void bubbleSort(T[] array) {
		boolean done = false;

		for (int sorted = 0; sorted < array.length - 1 && !done; ++sorted) {
			done = true;

			for (int i = 0; i < array.length - 1 - sorted; ++i) {
				if (array[i].compareTo(array[i + 1]) > 0) {
					done = false;

					T temp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = temp;
				}
			}

		}
	}

	/**
	 * Reverses the order of a one-dimensional array.
	 */
	public static <T> void reverse (T[] array) {
		int mid = array.length / 2;
		for (int i = 0; i < mid; ++i) {
			T temp = array[i];
			array[i] = array[array.length - 1 - i];
			array[array.length - 1 - i] = temp;
		}
	}

}
