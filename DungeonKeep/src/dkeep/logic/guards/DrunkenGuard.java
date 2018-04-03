package dkeep.logic.guards;

import java.util.Random;

import dkeep.logic.MovingObject;

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

	public void handleNextMove(MOVEMENT_TYPE move) {
		if (positiveDirection)
			move = this.guardMovement.elementAt(currentMovPos);
		else
			move = contrary(this.guardMovement.elementAt(currentMovPos));

		if(wokeUp) {
            Random randDirectionChanger = new Random();
            int directionChanger = randDirectionChanger.nextInt(4);

            if (directionChanger == 0) {
                positiveDirection = (!positiveDirection);

                if (positiveDirection) currentMovPos--;
                else currentMovPos++;
            }

            wokeUp= false;
        }

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
	
	
	
	@Override
	public MOVEMENT_TYPE getMove() {

        //Handle negative values
        if (currentMovPos < 0)
            currentMovPos = guardMovement.size() - 1;

        //Handle circular iteration through possible movements
        currentMovPos = (currentMovPos % guardMovement.size());


		if (roundsLeftSleeping > 1) {
			roundsLeftSleeping--;
			return MovingObject.MOVEMENT_TYPE.NONE;
		} else if (roundsLeftSleeping == 1) {
			roundsLeftSleeping--;
			this.Symbol = 'G';

            wokeUp = true;
			return MovingObject.MOVEMENT_TYPE.NONE;
		}

		MovingObject.MOVEMENT_TYPE move = this.guardMovement.elementAt(currentMovPos);
		
		handleNextMove(move);
		
		return move;

	}

}
