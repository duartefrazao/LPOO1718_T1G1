package dkeep.logic.guards;

import java.util.Random;

import dkeep.logic.MovingObject;

public class DrunkenGuard extends Guard {

	static private int roundsLeftSleeping = 0;

	static private boolean positiveDirection = true;
	
	public DrunkenGuard(int x, int y) {
		super(x, y);
	}

	@Override
	public MOVEMENT_TYPE getMove() {

		if (roundsLeftSleeping > 1) {
			roundsLeftSleeping--;
			return MovingObject.MOVEMENT_TYPE.NONE;
		} else if (roundsLeftSleeping == 1) {
			roundsLeftSleeping--;
			this.Symbol = 'G';
			return MovingObject.MOVEMENT_TYPE.NONE;
		}

		MovingObject.MOVEMENT_TYPE move = this.guardMovement.elementAt(currentMovPos);

		if (positiveDirection)
			move = this.guardMovement.elementAt(currentMovPos);
		else
			move = contrary(this.guardMovement.elementAt(currentMovPos));

		Random randSleep = new Random();
		int fallAsleep = randSleep.nextInt(10); // Value from 0 to 9 (1/10 chance of falling asleep)

		if (fallAsleep == 0) {
			roundsLeftSleeping = randSleep.nextInt(5) + 1; //Value from 1 to 5 (Up to 5 rounds asleep)
			Symbol = 'g';
		}
 
		Random randDirectionChanger = new Random();
		int directionChanger = randDirectionChanger.nextInt(10); // Value from 0 to 9 (1/10 chance of changing direction)

		if (directionChanger == 0) {
			positiveDirection = (!positiveDirection);
			
			//When changing direction it need's to change to the last/next directions
			if(positiveDirection) currentMovPos--;
			else currentMovPos++;
		}

		if (positiveDirection)
			currentMovPos++;
		else {

			currentMovPos--;
			if (currentMovPos < 0)
				currentMovPos = guardMovement.size() - 1;//Handle negative values
		}

		currentMovPos = (currentMovPos % guardMovement.size());

		return move;

	}

}
