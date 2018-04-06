package dkeep.logic.levels;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import dkeep.logic.Hero;
import dkeep.logic.MovingObject;
import dkeep.logic.Pair;

/**
 * Represents the different levels that constitute a dungeon. All the game flow
 * and handling will occur here.
 *
 */ 
public abstract class Level implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8341778413317578800L;

	/**
	 * All the possible level states.
	 *
	 */
	public enum LEVEL_STATE {
		PASSED_LEVEL, DEATH, NONE
	}

	protected char map[][];

	protected Pair heroOriginalPos = new Pair(0, 0);

	protected Hero hero;

	protected Vector<Pair> passageDoors = new Vector<Pair>(0);

	/**
	 * Finds a random empty position in the map that's not adjacent to the hero.
	 * Useful for placing randomly ogres in the map.
	 * 
	 * @return A pair containing the empty position
	 * @see Pair.java
	 */
	public Pair getRandomEmptyPositions() {

		Pair randomPos = new Pair(0, 0);

		boolean foundIt = false;

		while (!foundIt) {
			int x = ThreadLocalRandom.current().nextInt(0, map.length - 2);
			int y = ThreadLocalRandom.current().nextInt(0, map[x].length - 2);

			if (this.map[x][y] == ' ' && Math.abs(x - this.hero.getX()) > 2 && Math.abs(y - this.hero.getY()) > 2) {

				foundIt = true;

				randomPos.setX(x);
				randomPos.setY(y);
			}
		}

		return randomPos;
	}

	/**
	 * Finds the passage Doors of a level. By passage doors we mean the doors
	 * adjacent to the edges of the map.
	 */
	public void findPassageDoors() {

		for (int i = 0; i < map.length; i++) {

			if (i == 0 || i == (map.length - 1)) {
				for (int j = 0; j < map[i].length; j++) {

					if (map[i][j] == 'I')
						passageDoors.add(new Pair(i, j));
				}
			} else {
				if (map[i][0] == 'I') {
					Pair newDoor = new Pair(i, 0);
					passageDoors.add(newDoor);
				}
				if (map[i][map[i].length - 1] == 'I')
					passageDoors.add(new Pair(i, map[i].length - 1));
			}

		}
	}

	/**
	 * Reset keep level elements to original
	 */
	public abstract void resetGameElements();

	/**
	 * Identifies the game elements on the map, generating some elements randomly if
	 * needed. Some random behaviour is needed, for example, to generate ogres.
	 * 
	 * @see Ogre.java
	 */
	public abstract void findGameElements();

	/**
	 * Creates a map with game elements to be printed
	 * 
	 * @return map to be printed
	 */
	public abstract char[][] createMapToPrint();

	/**
	 * Level state machine, responsible for the level game flow
	 * 
	 * @param move
	 *            - hero movement
	 * @return current level state
	 */
	public abstract LEVEL_STATE updateLevel(MovingObject.MOVEMENT_TYPE move);

	/**
	 * Checks collision between any moving object and the hero, with a given allowed
	 * spacing between them.
	 * 
	 * @param A
	 *            - the moving object
	 * @param spacing
	 *            - allowed spacing between them
	 * @return true in case of collision, false otherwise.
	 */
	public boolean collision(MovingObject A, int spacing) {

		int hero_x = hero.getX();
		int hero_y = hero.getY();

		int obj_x = A.getX();
		int obj_y = A.getY();

		if ((Math.abs(hero_x - obj_x) + Math.abs(hero_y - obj_y)) <= spacing)
			return true;

		return false;

	}

	/**
	 * Default constructor
	 */
	public Level() {
	}

	/**
	 * Resets the Hero back to it's original state
	 */
	public void resetElements() {
		hero.setX(heroOriginalPos.getX());
		hero.setY(heroOriginalPos.getY());
		hero.setSymbol('H');
	}

	/**
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * @param hero
	 *            the hero to set
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * 
	 * @return the level map
	 */
	public char[][] getMap() {
		return this.map;
	}

}
