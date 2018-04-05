package dkeep.logic.guards;

import java.io.Serializable;
import java.util.Random;


public class DrunkenGuard extends Guard implements Serializable{

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1593400630606381521L;

	static private int roundsLeftSleeping = 0;

	static private boolean positiveDirection = true;

	static private boolean wokeUp = false;

	/**
	 * @returns true if ogre is sleeping, false otherwise
	 */
	public boolean isSleeping() {
		return this.roundsLeftSleeping != 0;
	}

	/**
	 * Drunken Guard constructor
	 * @returns new Drunken Guard object
	 */
	public DrunkenGuard(int x, int y) {
		super(x, y);
	}

	/**
	 * @returns true if moving in possible direction, false otherwise 
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
