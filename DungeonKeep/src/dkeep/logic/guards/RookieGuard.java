package dkeep.logic.guards;

import java.io.Serializable;

public class RookieGuard extends Guard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5253836972117491759L;

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
