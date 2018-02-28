package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Ogre extends MovingObject {

	private char Symbol = 'O';

	private Weapon club;

	public Weapon getClub() {
		return club;
	}

	public MovingObject.MOVEMENT_TYPE getMove() {

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

}
