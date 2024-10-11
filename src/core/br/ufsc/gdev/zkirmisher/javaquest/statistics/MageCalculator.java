package br.ufsc.gdev.zkirmisher.javaquest.statistics;


public class MageCalculator extends PlayerCalculator {

	// METHODS
	public int hp(int level) {
		return 80 + 48 * level;
	}

	public int mp(int level) {
		return 150 * level;
	}

	public int attack(int str) {
		return (int)(str * 2.5);
	}

	public int spell(int intel) {
		return (int)(intel * 7.5);
	}

	public int crit(int dex) {
		return (int)(dex * 166.667);
	}

}
