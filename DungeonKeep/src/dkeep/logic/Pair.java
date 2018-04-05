package dkeep.logic;

import java.io.Serializable;

/**
 * Pair.class - a simple class representing a 2D position (x, y).
 * @author pedro
 *
 */
public class Pair implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	/**
	 * Retrieves the x position of the pair
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x position of the pair
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Retrieves the y position of the pair
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y position of the pair
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public Pair(int a, int b) {
		this.x = a;
		this.y = b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
  
	@Override
	/**
	 * Equal operator
	 * @return true if the two pairs are the same, i.e. equal x and y, false otherwise
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
