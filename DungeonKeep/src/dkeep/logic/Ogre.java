package dkeep.logic;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

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

	public char getSymbol() {
		if (isStunned)
			return this.stunnedSymbol;
		return this.Symbol;
	}

	public void setSymbol(char symbol) {

		this.Symbol = symbol;
	}

	public Ogre(int x, int y) {

		position.setX(x);
		position.setY(y);

		club = new Weapon();
	}

	public int getRoundsLeftStunned() {
		return roundsLeftStunned;
	}

	public void setRoundsLeftStunned(int roundsLeftStunned) {
		this.roundsLeftStunned = roundsLeftStunned;
	}

	public boolean isStunned() {
		return isStunned;
	}

	public void setStunned(boolean isStunned) {
		this.isStunned = isStunned;
	}

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

}
