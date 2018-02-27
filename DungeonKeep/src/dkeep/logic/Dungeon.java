package dkeep.logic;

import java.util.Vector;

public class Dungeon {

	public enum GAME_STATE {
		GAME_OVER, VICTORY, PLAYING
	}

	static private GAME_STATE game_state = GAME_STATE.PLAYING;

	private Vector<Level> levels;
	static private int currentLevel = 0;

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

	public void printMap() {
		levels.elementAt(currentLevel).printMap();
	}

}
