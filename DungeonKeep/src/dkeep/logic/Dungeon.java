package dkeep.logic;

import java.io.Serializable;
import java.util.Vector;

import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

public class Dungeon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum GAME_STATE {
		GAME_OVER, VICTORY, PLAYING
	}


	public Level getCurrentLevel() {

		return this.levels.elementAt(currentLevel);

	}
	
	public Hero getHero() {
		return this.levels.elementAt(currentLevel).getHero();
	}

	private Vector<Level> levels;
	private int currentLevel = 0;

	public void substituteKeepLevel(KeepLevel customLevel) {

		levels.set(1, customLevel);

	}
	 
	public void resetCurrentLevel() {
		this.currentLevel = 0;
		

		for(Level e : this.levels)
			e.resetGameElements();
	}

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
		default:
			return GAME_STATE.PLAYING;
		}

	}

	public char[][] getMap() {
		return levels.elementAt(currentLevel).createMapToPrint();
	}

	public KeepLevel getKeepLevel() {
		// TODO Auto-generated method stub
		return (KeepLevel) this.levels.get(1);
	}


}
