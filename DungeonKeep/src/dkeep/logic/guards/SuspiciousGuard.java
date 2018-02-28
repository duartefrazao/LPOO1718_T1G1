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
		MovingObject.MOVEMENT_TYPE move = this.guardMovement.elementAt(currentMovPos);
		
		Random rand = new Random(); 
		int directionChanger = rand.nextInt(2); //Value from 0 to 1 
		
		if(directionChanger == 0) positiveDirection= (!positiveDirection);
		
		if(positiveDirection) currentMovPos++;
		else currentMovPos--;
		
		currentMovPos = (currentMovPos % guardMovement.size());
		
		return move;
	}
	


}
