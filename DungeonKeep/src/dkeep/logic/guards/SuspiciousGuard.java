package dkeep.logic.guards;

import java.io.Serializable;
import java.util.Random;

/**
 * The Suspicious Guard; a zealous and insecure guard that keeps checking his back in order to make sure he catchs the hero.
 * Will randomly reverse his path. 
 *
 */
public class SuspiciousGuard extends Guard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4999885784312383644L;

	static private boolean positiveDirection = true;

	/**
	 * Suspicious Guard constructor with position
	 * 
	 * @param x
	 *            - the x position in the map
	 * @param y
	 *            - the y position in the map
	 */
	public SuspiciousGuard(int x, int y) {
		super(x, y);
	}

	/**
	 * Gets a new guard movement respecting restrictions
	 * 
	 * @return new guard movement
	 *
	 */
	@Override
	public MOVEMENT_TYPE getMove() {

		MOVEMENT_TYPE move;
		if (positiveDirection)
			move = this.guardMovement.elementAt(currentMovPos);
		else
			move = contrary(this.guardMovement.elementAt(currentMovPos));

		Random rand = new Random();
		int directionChanger = rand.nextInt(10);

		if (directionChanger == 0) {
			positiveDirection = (!positiveDirection);

			if (positiveDirection)
				currentMovPos--;
			else
				currentMovPos++;
		}

		if (positiveDirection)
			currentMovPos++;
		else {
			currentMovPos--;
			if (currentMovPos < 0)
				currentMovPos = guardMovement.size() - 1;
		}

		currentMovPos = (currentMovPos % guardMovement.size());

		return move;
	}

	/**
	 * 
	 * @return true if is going in general direction, false otherwise
	 */
	public boolean getDirection() {
		return positiveDirection;
	}

}
