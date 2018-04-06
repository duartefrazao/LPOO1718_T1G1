package dkeep.logic;

import java.io.Serializable;
import java.util.Vector;

import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

/**
 * The place where all the levels are set and the action take place.
 *
 */
public class Dungeon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Game States to make the game work as a state machine
	 */
	public enum GAME_STATE {
		GAME_OVER, VICTORY, PLAYING
	}

	/**
	 * Retrieves the current level object
	 * 
	 * @return the current level object
	 */
	public Level getCurrentLevel() {

		return this.levels.elementAt(currentLevel);

	}

	/**
	 * Retrieves the hero object from the current level
	 * 
	 * @return the hero
	 */
	public Hero getHero() {
		return this.levels.elementAt(currentLevel).getHero();
	}

	private Vector<Level> levels;
	private int currentLevel = 0;

	/**
	 * Substitutes the keep level, used to creat new maps
	 * 
	 * @param customLevel
	 *            - the keep level substitution
	 */
	public void substituteKeepLevel(KeepLevel customLevel) {

		levels.set(1, customLevel);

	}

	/**
	 * Resets the level and all the elements
	 */
	public void resetCurrentLevel() {
		this.currentLevel = 0;

		for (Level e : this.levels)
			e.resetGameElements();
	}

	/**
	 * Dungeon constructor
	 * 
	 * @param vLevels
	 *            - levels to construct dungeon
	 */
	public Dungeon(Vector<Level> vLevels) {
		this.levels = vLevels;
	}

	/**
	 * State machine of the game logic Takes a step in the game logic
	 * 
	 * @param move
	 *            - hero movement 
	 * @return current game state
	 */
	public GAME_STATE game(MOVEMENT_TYPE move) {

		switch (levels.elementAt(currentLevel).updateLevel(move)) {
		case PASSED_LEVEL:

			if (currentLevel == levels.size() - 1)
				return GAME_STATE.VICTORY;
			else {
				currentLevel++;
				levels.elementAt(currentLevel).resetElements();
				return GAME_STATE.PLAYING;
			}
		case DEATH:
			return GAME_STATE.GAME_OVER;
		default:
			return GAME_STATE.PLAYING;
		}

	}

	/**
	 * @return map array
	 */
	public char[][] getMap() {
		return levels.elementAt(currentLevel).createMapToPrint();
	}

	/**
	 * @return keep level object
	 */
	public KeepLevel getKeepLevel() {
		return (KeepLevel) this.levels.get(1);
	}

}
