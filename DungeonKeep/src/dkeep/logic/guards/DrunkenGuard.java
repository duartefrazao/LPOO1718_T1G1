package dkeep.logic.guards;

import java.util.Random;


public class DrunkenGuard extends Guard {

	static private int roundsLeftSleeping = 0;

	static private boolean positiveDirection = true;

	static private boolean wokeUp = false;

	public boolean isSleeping() {
		return this.roundsLeftSleeping != 0;
	}

	public DrunkenGuard(int x, int y) {
		super(x, y);
	}

	public boolean getDirection() {
		return positiveDirection;
		
	}

	public void wokeUpHandler() {
		if (wokeUp) {
			Random randDirectionChanger = new Random();
			int directionChanger = randDirectionChanger.nextInt(4); // Value from 0 to 3 (1/4 chance of changing
																	// direction)

			if (directionChanger == 0) {
				positiveDirection = (!positiveDirection);

				// When changing direction it need's to change to the last/next directions
				if (positiveDirection)
					currentMovPos--;
				else
					currentMovPos++;
			}

			wokeUp = false;
		}

	}
	public void moveHandler() {
		Random randSleep = new Random();
		int fallAsleep = randSleep.nextInt(7); // Value from 0 to 6 (1/6 chance of falling asleep)

		if (fallAsleep == 0) {
			roundsLeftSleeping = randSleep.nextInt(5) + 1; // Value from 1 to 5 (Up to 5 rounds asleep)
			Symbol = 'g';
		}

		if (positiveDirection)
			currentMovPos++;
		else {
 
			currentMovPos--;
			if (currentMovPos < 0)
				currentMovPos = guardMovement.size() - 1;// Handle negative values
		}

		currentMovPos = (currentMovPos % guardMovement.size());
	}

	@Override
	public MOVEMENT_TYPE getMove() {

		// Handle negative values
		if (currentMovPos < 0)
			currentMovPos = guardMovement.size() - 1;

		// Handle circular iteration through possible movements
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
