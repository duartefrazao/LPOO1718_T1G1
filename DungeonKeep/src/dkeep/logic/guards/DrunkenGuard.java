package dkeep.logic.guards;

import java.io.Serializable;
import java.util.Random;

/**
 * The DrunkenGuard has drinking problems and will randomly fall asleep during
 * it's job. While asleep he might forget what he was doing and go in the
 * contrary path he was taking
 *
 */
public class DrunkenGuard extends Guard implements Serializable {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1593400630606381521L;

	static private int roundsLeftSleeping = 0;

	static private boolean positiveDirection = true;

	static private boolean wokeUp = false;

	/**
	 * @return true if ogre is sleeping, false otherwise
	 */
	public boolean isSleeping() {
		return this.roundsLeftSleeping != 0;
	}

	/**
	 * Drunken Guard constructor
	 * 
	 * @param x
	 *            -Guard x position
	 * @param y
	 *            -Guard y position
	 */
	public DrunkenGuard(int x, int y) {
		super(x, y);
	}

	/**
	 * @return true if moving in possible direction, false otherwise
	 */
	public boolean getDirection() {
		return positiveDirection;

	}

	/**
	 * Handles movement where guard wakes up
	 */
	public void wokeUpHandler() {
		if (wokeUp) {
			Random randDirectionChanger = new Random();
			int directionChanger = randDirectionChanger.nextInt(4);

			if (directionChanger == 0) {
				positiveDirection = (!positiveDirection);

				if (positiveDirection)
					currentMovPos--;
				else
					currentMovPos++;
			}

			wokeUp = false;
		}

	}

	/**
	 * Handles movement, changing direction and rounds left sleeping
	 */
	public void moveHandler() {
		Random randSleep = new Random();
		int fallAsleep = randSleep.nextInt(7);

		if (fallAsleep == 0) {
			roundsLeftSleeping = randSleep.nextInt(5) + 1;
			Symbol = 'g';
		}

		if (positiveDirection)
			currentMovPos++;
		else {

			currentMovPos--;
			if (currentMovPos < 0)
				currentMovPos = guardMovement.size() - 1;
		}

		currentMovPos = (currentMovPos % guardMovement.size());
	}

	/**
	 * Gets a new Drunken Guard Movement
	 * 
	 * @return new Drunken Guard movement
	 */
	@Override
	public MOVEMENT_TYPE getMove() {

		if (currentMovPos < 0)
			currentMovPos = guardMovement.size() - 1;

		currentMovPos = (currentMovPos % guardMovement.size());

		if (roundsLeftSleeping > 1) {
			roundsLeftSleeping--;
			return MOVEMENT_TYPE.NONE;
		} else if (roundsLeftSleeping == 1) {
			roundsLeftSleeping--;
			this.Symbol = 'G';

			wokeUp = true;
			return MOVEMENT_TYPE.NONE;
		}

		MOVEMENT_TYPE move = this.guardMovement.elementAt(currentMovPos);

		if (positiveDirection)
			move = this.guardMovement.elementAt(currentMovPos);
		else
			move = contrary(this.guardMovement.elementAt(currentMovPos));

		wokeUpHandler();

		moveHandler();

		return move;

	}

}
