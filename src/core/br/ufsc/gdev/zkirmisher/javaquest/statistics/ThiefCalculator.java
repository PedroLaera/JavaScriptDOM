package br.ufsc.gdev.zkirmisher.javaquest.statistics;


public class ThiefCalculator extends PlayerCalculator {

	// METHODS
	public int hp(int level) {
		return 40 + 74 * level;
	}

	public int mp(int level) {
		return 50 * level;
	}

	public int attack(int str) {
		return (int)(str * 7.5);
	}

	public int spell(int intel) {
		return (int)(intel * 2.5);
	}

	public int crit(int dex) {
		return (int)(dex * 666.667);
	}

}
