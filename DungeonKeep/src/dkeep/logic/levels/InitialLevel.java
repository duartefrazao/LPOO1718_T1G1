package dkeep.logic.levels;

import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Pair;
import dkeep.logic.guards.*;

public class InitialLevel extends Level {

	public InitialLevel() {

		super();

		char initialLevelMap[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		this.map = initialLevelMap;

		initialMap = new char[initialLevelMap.length][];
		
		for (int i = 0; i < initialLevelMap.length; i++) {
			char[] aMatrix = initialLevelMap[i];
			int aLength = aMatrix.length;
			initialMap[i] = new char[aLength];
			System.arraycopy(aMatrix, 0, initialMap[i], 0, aLength);
		}


		findGameElements();
		findPassageDoors();
	}

	@Override 
	public void resetGameElements() {
		
		

		map = new char[initialMap.length][0];

		map = new char[initialMap.length][];
		for (int i = 0; i < initialMap.length; i++) {
			char[] aMatrix = initialMap[i];
			int aLength = aMatrix.length;
			map[i] = new char[aLength];
			System.arraycopy(aMatrix, 0, map[i], 0, aLength);
		}

		this.findGameElements();

		this.findPassageDoors();
		
		this.leverOff = true;

	}

	public InitialLevel(char[][] new_map) {

		super();

		this.map = new_map;


		initialMap = new char[new_map.length][];
		for (int i = 0; i < new_map.length; i++) {
			char[] aMatrix = new_map[i];
			int aLength = aMatrix.length;
			initialMap[i] = new char[aLength];
			System.arraycopy(aMatrix, 0, initialMap[i], 0, aLength);
		}

		findGameElements();
		findPassageDoors();
	}

	private boolean guardDefined = false;

	private Guard guard;

	private char[][] initialMap;

	private Pair Lever = new Pair(0, 0);

	private boolean leverOff = true;

	public void findGameElements() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				switch (map[i][j]) {
				case 'k':
					Lever.setX(i);
					Lever.setY(j);
					break;
				case 'G':
					guard = new DrunkenGuard(i, j);
					map[i][j] = ' ';
					guardDefined = true;
					break;
				case 'H':
					this.hero = new Hero();
					hero.setX(i);
					hero.setY(j);
					map[i][j] = ' ';
					heroOriginalPos.setX(i);
					heroOriginalPos.setY(j);
					break;

				}
			}

		}
	}

	public void setGuard(Guard newG) {
		this.guard = newG;
	}

	public char[][] createMapToPrint() {

		char[][] mapToPrint = new char[map.length][];

		for (int i = 0; i < map.length; i++) {
			char[] line = map[i];
			int line_size = line.length;
			mapToPrint[i] = new char[line_size];
			System.arraycopy(line, 0, mapToPrint[i], 0, line_size);
		}

		int i, j;

		// ---- hero ----
		i = this.hero.getX();
		j = this.hero.getY();

		mapToPrint[i][j] = this.hero.getSymbol();

		// ---- guards ----

		if (guardDefined) {
			i = this.guard.getX();
			j = this.guard.getY();

			mapToPrint[i][j] = this.guard.getSymbol();
		}

		return mapToPrint;
	}

	public LEVEL_STATE updateLevel(MOVEMENT_TYPE move) {

		hero.move(move, map);
		guard.move(guard.getMove(), map);

		int x = hero.getX();
		int y = hero.getY();

		if (this.leverOff && x == Lever.getX() && y == Lever.getY()) {

			this.leverOff = false;

			for (int i = 0; i < this.passageDoors.size(); i++) {
				int door_x = passageDoors.elementAt(i).getX();
				int door_y = passageDoors.elementAt(i).getY();

				map[door_x][door_y] = 'S';
			}

		}

		if (map[hero.getX()][hero.getY()] == 'S')
			return LEVEL_STATE.PASSED_LEVEL;
		// Needs to test with 1 space difference for general guard and
		// With 0 space difference (adjacent) for a sleeping guard
		if (guard.getSymbol() == 'G' && collision(guard, 1))
			return LEVEL_STATE.DEATH;
		else if (guard.getSymbol() == 'g' && collision(guard, 0))
			return LEVEL_STATE.DEATH;

		return LEVEL_STATE.NONE;
	}

	public Guard getGuard() {
		return guard;
	}

	/**
	 * @return the leverOff
	 */
	public boolean isLeverOff() {
		return leverOff;
	}

	/**
	 * @param leverOff
	 *            the leverOff to set
	 */
	public void setLeverOff(boolean leverOff) {
		this.leverOff = leverOff;
	}

	/**
	 * @return the lever
	 */
	public Pair getLever() {
		return Lever;
	}

	/**
	 * @param lever
	 *            the lever to set
	 */
	public void setLever(Pair lever) {
		Lever = lever;
	}

}
