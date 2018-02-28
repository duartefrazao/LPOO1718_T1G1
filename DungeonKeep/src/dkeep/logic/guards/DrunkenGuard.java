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

		if(roundsLeftSleeping > 1) {
			roundsLeftSleeping--;
			return MovingObject.MOVEMENT_TYPE.NONE;
		}
		else if (roundsLeftSleeping== 1)
		{
			roundsLeftSleeping--;
			this.Symbol= 'G';
			return MovingObject.MOVEMENT_TYPE.NONE;
		}
		
		MovingObject.MOVEMENT_TYPE move = this.guardMovement.elementAt(currentMovPos);

		
		Random rand = new Random();
		int fallAsleep = rand.nextInt(2); // Value from 0 to 1
		
		
		if(fallAsleep == 1) 
		{
			roundsLeftSleeping = rand.nextInt(6)+1;
			Symbol = 'g';
			return MovingObject.MOVEMENT_TYPE.NONE;
		}else {
			
			int directionChanger = rand.nextInt(2);
			
			if (directionChanger == 0)
				positiveDirection = (!positiveDirection);

			if (positiveDirection)
				currentMovPos++;
			else
				currentMovPos--;

			currentMovPos = (currentMovPos % guardMovement.size());

			return move;
		}
		
	}

}
