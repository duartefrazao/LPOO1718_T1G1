package dkeep.logic.guards;

import java.io.Serializable;

public class RookieGuard extends Guard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5253836972117491759L;

	/**
	 * Rookie Guard constructor
	 * @param x
	 * 		- rookie guard x position
	 * @param y
	 * 		- rookie guard y position
	 * 
	 * @return new rookie guard object
	 */
	public RookieGuard(int x, int y) {
		super(x, y);
	}

	 /**
     * Gets a new guard movement respecting restrictions
     * @returns new guard movent
     *
     */
	@Override
	public MOVEMENT_TYPE getMove() {
		MOVEMENT_TYPE move = this.guardMovement.get(currentMovPos);
		
		currentMovPos++;
		currentMovPos = (currentMovPos % guardMovement.size());
		
		return move;
		
	}

}
