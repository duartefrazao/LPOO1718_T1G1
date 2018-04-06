package dkeep.logic.guards;

import java.io.Serializable;

/**
 * The rookie guard. The "classic" guard that only follows the path without any deviation.
 * 
 */
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
	 */
	public RookieGuard(int x, int y) {
		super(x, y);
	}

	 /**
     * Gets a new guard movement respecting restrictions
     * @return new guard movement
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
