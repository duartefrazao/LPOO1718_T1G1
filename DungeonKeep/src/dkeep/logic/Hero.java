package dkeep.logic;

import java.io.Serializable;

public class Hero extends MovingObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private char Symbol = 'H';

	private boolean hasKey = false;

	private Weapon weapon;
	
	public void resetHero() {
		this.Symbol = 'H';
		this.hasKey = false;
		this.weapon = null;
	}

	public boolean hasKey() {
		return hasKey;
	}

	public void setKey(boolean key) {
		this.hasKey = key;
	}

	public char getSymbol() {
		return this.Symbol;
	} 

	public void setSymbol(char symbol) {
		if (symbol == 'K')
			hasKey = true; 

		this.Symbol = symbol;
	}

	public Hero() {
		super();
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public boolean isArmed() {

		return this.weapon != null;

	}

}
