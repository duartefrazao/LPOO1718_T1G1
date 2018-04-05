package dkeep.logic.guards;


public class RookieGuard extends Guard {

	public RookieGuard(int x, int y) {
		super(x, y);
	}

	@Override
	public MOVEMENT_TYPE getMove() {
		MOVEMENT_TYPE move = this.guardMovement.get(currentMovPos);
		
		currentMovPos++;
		currentMovPos = (currentMovPos % guardMovement.size());
		
		return move;
		
	}

}
