package dkeep.logic;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Represents the hero's enemies in the keep level.
 *
 */
public class Ogre extends MovingObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 620343092351729404L;

	private char Symbol = 'O';

	private char stunnedSymbol = '8';

	private int roundsLeftStunned = 0;

	private boolean isStunned = false;

	private Weapon club;

	/**
	 * @return ogre club object
	 */
	public Weapon getClub() {
		return club;
	}

	/**
	 * Gets a new random ogre movement
	 * 
	 * @return ogre movement
	 */
	public MOVEMENT_TYPE getMove() {

		int x = ThreadLocalRandom.current().nextInt(0, 3 + 1);

		switch (x) {

		case 0:
			return MOVEMENT_TYPE.UP;
		case 1:
			return MOVEMENT_TYPE.DOWN;
		case 2:
			return MOVEMENT_TYPE.RIGHT;
		case 3:
			return MOVEMENT_TYPE.LEFT;
		default:
			return MOVEMENT_TYPE.NONE;

		}

	}

	/**
	 * Gets ogre symbol, also comtemplates stunned mode
	 * 
	 * @return ogre movement
	 */
	public char getSymbol() {
		if (isStunned)
			return this.stunnedSymbol;
		return this.Symbol;
	}

	/**
	 * Ogre constructor
	 * 
	 * @param x
	 *        - ogre x position
	 * @param y
	 *        - ogre y position
	 * 
	 */
	public Ogre(int x, int y) {

		position.setX(x);
		position.setY(y);

		club = new Weapon();
	}

	/**
	 * Moves the ogre in the desired map in the choosen direction
	 * 
	 * @param movement
	 *            -choosen direction
	 * @param map
	 *            -map to move ogre
	 */
	@Override
	public void move(MOVEMENT_TYPE movement, char map[][]) {
		int x = position.getX();
		int y = position.getY();

		if (this.roundsLeftStunned > 0) {
			roundsLeftStunned--;
			if (roundsLeftStunned == 0)
				this.isStunned = false;
			return;
		}

		switch (movement) {

		case UP: {
			if (map[x - 1][y] != 'X' && map[x - 1][y] != 'I') {
				position.setX(--x);
			}
			break;
		}
		case DOWN: {
			if (map[x + 1][y] != 'X' && map[x + 1][y] != 'I') {
				position.setX(++x);
			}
			break;
		}
		case LEFT: {
			if (map[x][y - 1] != 'X' && map[x][y - 1] != 'I') {
				position.setY(--y);
			}
			break;
		}
		case RIGHT: {
			if (map[x][y + 1] != 'X' && map[x][y + 1] != 'I') {
				position.setY(++y);
			}
			break;
		}
		case NONE:
			break;
		}
	}

	/**
	 * @return the stunnedSymbol
	 */
	public char getStunnedSymbol() {
		return stunnedSymbol;
	}

	/**
	 * @param stunnedSymbol
	 *            the stunnedSymbol to set
	 */
	public void setStunnedSymbol(char stunnedSymbol) {
		this.stunnedSymbol = stunnedSymbol;
	}

	/**
	 * @return the roundsLeftStunned
	 */
	public int getRoundsLeftStunned() {
		return roundsLeftStunned;
	}

	/**
	 * @param roundsLeftStunned
	 *            the roundsLeftStunned to set
	 */
	public void setRoundsLeftStunned(int roundsLeftStunned) {
		this.roundsLeftStunned = roundsLeftStunned;
	}

	/**
	 * @return the isStunned
	 */
	public boolean isStunned() {
		return isStunned;
	}

	/**
	 * @param isStunned
	 *            the isStunned to set
	 */
	public void setStunned(boolean isStunned) {
		this.isStunned = isStunned;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(char symbol) {
		Symbol = symbol;
	}

	/**
	 * @param club
	 *            the club to set
	 */
	public void setClub(Weapon club) {
		this.club = club;
	}

}
