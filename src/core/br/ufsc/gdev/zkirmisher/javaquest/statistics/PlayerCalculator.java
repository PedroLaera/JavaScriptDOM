package br.ufsc.gdev.zkirmisher.javaquest.statistics;


public abstract class PlayerCalculator implements StatCalculator {

	// METHODS
	public long exp(int level) {
		return 10 * PlayerCalculator.fibonacci(level); //XXX Since longs are signed, overflows after level 40.
	}

	public abstract int hp(int level);

	public abstract int mp(int level);

	public abstract int attack(int str);

	public abstract int spell(int intel);

	public abstract int crit(int dex);


	// CLASS FUNCTIONS
	private static int fibonacci(int n) {
		int previous = 0;
		int next = 1;
		int result = 1;

		for (int i = 0; i < n; i++) {
			result = previous + next;
			previous = next;
			next = result;
		}

		return result;
	}

}
