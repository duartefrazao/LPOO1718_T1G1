package dkeep.logic.guards;

import java.util.Random;

import dkeep.logic.MovingObject;

public class SuspiciousGuard extends Guard{

	static private boolean positiveDirection=true;
	
	public SuspiciousGuard(int x, int y) {
		super(x, y);
	}

	@Override 
	public MOVEMENT_TYPE getMove() {
		
		MovingObject.MOVEMENT_TYPE move;
		if(positiveDirection) move = this.guardMovement.elementAt(currentMovPos);
		else move = contrary(this.guardMovement.elementAt(currentMovPos));
		
		Random rand = new Random(); 
		int directionChanger = rand.nextInt(10); // Value from 0 to 9 (1/10 chance of changing direction)
		
		if(directionChanger == 0) {
			positiveDirection= (!positiveDirection);
			
			//When changing direction it need's to change to the last/next directions
			if(positiveDirection) currentMovPos--;
			else currentMovPos++;
		}
		

		
		if(positiveDirection ) currentMovPos++;
		else
		{
			currentMovPos--;
			if(currentMovPos<0) currentMovPos=guardMovement.size()-1; //Handle negative values
		}
		
		currentMovPos = (currentMovPos % guardMovement.size());
		
		return move;
	}

	public boolean getDirection() {
		return positiveDirection;
	}
	


}
