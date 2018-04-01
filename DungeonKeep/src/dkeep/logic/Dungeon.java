package dkeep.logic;

import java.util.Vector;

import dkeep.logic.levels.Level;

public class Dungeon {

	public enum GAME_STATE {
		GAME_OVER, VICTORY, PLAYING
	}

	// static private GAME_STATE game_state = GAME_STATE.PLAYING;

	public enum GAME_LEVEL {
		INITIAL_LEVEL, KEEP_LEVEL
	}

	public Level getCurrentLevel() {

		return this.levels.elementAt(currentLevel);

	}

	private Vector<Level> levels;
	private int currentLevel = 0;

	public Dungeon(Vector<Level> vLevels) {
		this.levels = vLevels;
	}

	public GAME_STATE game(MovingObject.MOVEMENT_TYPE move) {

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
		case NONE:
			return GAME_STATE.PLAYING;
		default:
			return GAME_STATE.PLAYING;
		}

	}

	public char[][] getMap() {
		return levels.elementAt(currentLevel).createMapToPrint();
	}

}
