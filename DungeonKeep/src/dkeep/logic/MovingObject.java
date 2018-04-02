package dkeep.logic;

/**
 * MovingObject.java - Base class for every moving object of the game
 * @author pedro
 *
 */
public abstract class MovingObject {
	
	protected Pair position = new Pair(0,0);
	
	/**
	 * Possible movements of the object in the game area.
	 * @author pedro
	 *
	 */
	public enum MOVEMENT_TYPE {
	    UP, DOWN, LEFT, RIGHT, NONE
	}
	
	/**
	 * Sets the new x position of the object
	 * @param x - the new x position
	 */
	public void setX(int x){
		position.setX(x);
	}
	
	/**
	 * Sets the new y position of the object
	 * @param y - the new y position
	 */
	public void setY(int y){
		position.setY(y);
	}
	
	
	/**
	 * Retrieve the x position of the object
	 * @return An integer representing the x position of the object
	 */
	public int getX(){
		return position.getX();
	}
	/**
	 * Retrieve the y position of the object.
	 * @return An integer representing the y position of the object
	 */
	public int getY(){
		return position.getY();
	}
	/**
	 * Returns the current position of and object represented by a pair (x, y)
	 * @see Pair.java
	 * @return Position pair
	 */
	public Pair getPosition() { return position; }

	/**
	 * Tries to move the object with a given direction and a given map.
	 * If the movement is not valid, i.e., against a wall or closed door, 
	 * it won't do nothing
	 * @param movement - the given direction
	 * @param map - the map in which the object will move
	 */
	public void move(MovingObject.MOVEMENT_TYPE movement, char map[][])
	{
		int x=position.getX();
		int y= position.getY();
		
		switch (movement) {

	       case UP: {
	            if (map[x - 1][y] != 'X' && map[x - 1][y] != 'I') {
	                position.setX(--x); 
	            }
	            break;
	        }
	        case DOWN: {
	            if (map[x + 1][y] != 'X' && map[x + 1][y] != 'I') {
	                position.setX(++x);
	            }
	            break;
	        }
	        case LEFT: {
	            if (map[x][y - 1] != 'X' && map[x][y - 1] != 'I') {
	                position.setY(--y);
	            }
	            break;
	        }
	        case RIGHT: {
	            if (map[x][y + 1] != 'X' && map[x][y + 1] != 'I') {
	                position.setY(++y);
	            }
	            break;
	        }
	        case NONE:
	        	break;
	    }
	}
}
