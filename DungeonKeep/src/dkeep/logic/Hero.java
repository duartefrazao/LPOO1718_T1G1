package dkeep.logic;

import java.io.Serializable;

/**
 * The main character of the game, the one the user interacts with.
 *
 */
public class Hero extends MovingObject implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -7517163043808963297L;

	private char Symbol = 'H';

	private boolean hasKey = false;

	private Weapon weapon;

	/**
	 * resets hero properties
	 */
	public void resetHero() {
		this.Symbol = 'H';
		this.hasKey = false;
		this.weapon = null;
	}

	/**
	 * @return true if hero has key, false otherwise
	 */
	public boolean hasKey() {
		return hasKey;
	}

	/**
	 * Sets hero has key flag
	 * 
	 * @param key - true to signal that hero has key - false to signal that hero
	 * doesn't have key
	 */
	public void setKey(boolean key) {
		this.hasKey = key;
	}

	/**
	 * @return hero symbol
	 */
	public char getSymbol() {
		return this.Symbol;
	}

	/**
	 * Sets a new hero symbol
	 * 
	 * @param symbol
	 *            - new hero symbol
	 */
	public void setSymbol(char symbol) {
		if (symbol == 'K')
			hasKey = true;

		this.Symbol = symbol;
	}

	/**
	 * hero constructor
	 * 
	 */
	public Hero() {
		super();
	}

	/**
	 * @return the hero weapon object
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Sets a new hero weapon object
	 * 
	 * @param weapon
	 *            - new hero weapon
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * State of hero weapon
	 * 
	 * @return true is hero is armed, false otherwise
	 */
	public boolean isArmed() {

		return this.weapon != null;

	}

}
