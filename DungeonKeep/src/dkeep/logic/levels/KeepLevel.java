package dkeep.logic.levels;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;
import dkeep.logic.Weapon;

/**
 * The second level of the dungeon, the one with ogres.
 *
 */
public class KeepLevel extends Level implements Serializable {

	private int hordeSize;
	
	private static final long serialVersionUID = -7873755346330412465L;

	private Vector<Ogre> crazyHorde = new Vector<Ogre>(0);

	private Pair Key = new Pair(0, 0);

	private Weapon heroWeapon;

	private boolean isRandomlyGenerated;
 
	/**
	 * Keep Level constructors on default map with a random number of ogres between 1 and 4
	 */
	public KeepLevel() {
		this(ThreadLocalRandom.current().nextInt(1, 3 + 1));
	}

	private char[][] initialMap;

	/**
	 * Keep level constructor on default map with a desired number of ogres
	 * @param hordeSize
	 * 		-number of ogres
	 */
	public KeepLevel(int hordeSize) {
		super();

		char keepLevelMap[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'I', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'k', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		this.map = keepLevelMap;
		this.heroWeapon = new Weapon();
		this.setHordeSize(hordeSize);   

		this.isRandomlyGenerated = true;

		initialMap = new char[keepLevelMap.length][];

		for (int i = 0; i < keepLevelMap.length; i++) {
			char[] aMatrix = keepLevelMap[i];
			int aLength = aMatrix.length;
			initialMap[i] = new char[aLength];
			System.arraycopy(aMatrix, 0, initialMap[i], 0, aLength);
		}

		findGameElements();
		findPassageDoors();
	}

	/**
	 * Keep level constructor with custom map
	 * @param new_map
	 * 		- custom map to apply to keep level
	 */
	public KeepLevel(char[][] new_map) {
		super();

		this.map = new_map;
		this.heroWeapon = new Weapon();

		initialMap = new char[new_map.length][];
		for (int i = 0; i < new_map.length; i++) {
			char[] aMatrix = new_map[i];
			int aLength = aMatrix.length;
			initialMap[i] = new char[aLength];
			System.arraycopy(aMatrix, 0, initialMap[i], 0, aLength);
		}

		this.isRandomlyGenerated = false;

		findGameElementsOnlyInMap();
		findPassageDoors();
	}

	/**
	 * Reset keep level elements to original
	 */
	@Override
	public void resetGameElements() {

		this.hero.resetHero();

		this.crazyHorde.clear();

		map = new char[initialMap.length][];
		for (int i = 0; i < initialMap.length; i++) {
			char[] aMatrix = initialMap[i];
			int aLength = aMatrix.length;
			map[i] = new char[aLength];
			System.arraycopy(aMatrix, 0, map[i], 0, aLength);
		}

		if (this.isRandomlyGenerated)
			this.findGameElements();
		else
			this.findGameElementsOnlyInMap();

		this.findPassageDoors();

	}

	/**
	 * 
	 * @return number of ogres
	 */
	public Vector<Ogre> getCrazyHorde() {
		return crazyHorde;
	}


	/**
	 * Set number of ogres on the map
	 * @param size
	 * 		- number of ogres to apply to keep level
	 */
	public void setHordeSize(int size) {
		this.hordeSize = size;
	}

	/**
	 * Checks collision between hero and ogres, also deals with stunning ogres
	 * @return true if collision is detected, false otherwise
	 */
	public boolean checkOgreCollision() {

		for (int i = 0; i < this.hordeSize; i++) {

			Ogre tempOgre = this.crazyHorde.elementAt(i);

			if (this.collision(tempOgre.getClub(), 1) || this.collision(tempOgre, 0)) {
				return true;
			}

			else if (hero.isArmed() && this.collision(tempOgre, 1)) {
				tempOgre.setStunned(true);
				tempOgre.setRoundsLeftStunned(2);
				return false;

			} else if (this.collision(tempOgre, 1))
				return true;
		}

		return false;
	}

	/**
	 * Sets a ogre in a desired position and the club on a valid random position
	 * @param i
	 * 		- ogre x position
	 * @param j
	 * 		- ogre y position
	 */
	public void setOgrePos(int i, int j) {
		map[i][j] = ' ';

		Ogre ogre = new Ogre(i, j);

		MOVEMENT_TYPE clubMov = ogre.getClub().getMove(map, ogre.getPosition());

		ogre.getClub().move(clubMov, map);

		this.crazyHorde.add(ogre);

		for (int k = 1; k < this.hordeSize; k++) {

			Pair randomPos = this.getRandomEmptyPositions();

			ogre = new Ogre(randomPos.getX(), randomPos.getY());

			clubMov = ogre.getClub().getMove(map, ogre.getPosition());

			ogre.getClub().move(clubMov, map);

			this.crazyHorde.add(ogre);

		}
	}

	/**
	 * Identifies the passage doors
	 */
	@Override
	public void findPassageDoors() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				if (map[i][j] == 'I')
					passageDoors.add(new Pair(i, j));
			}
		}

	}

	/**
	 * Sets the hero position and generates the sword on a valid position
	 * @param i
	 * 		- hero x position
	 * @param j
	 * 		- hero y position
	 */
	public void setHeroPos(int i, int j) {
		hero.setX(i);
		hero.setY(j);
		map[i][j] = ' ';

		heroOriginalPos.setX(i);
		heroOriginalPos.setY(j);

		MOVEMENT_TYPE swordMove = heroWeapon.getMove(map, hero.getPosition());

		heroWeapon.move(swordMove, map);
	}

	/**
	 * Identifies the game elements on the map, generating a random number of ogres
	 */
	public void findGameElements() {

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'H') {
					this.hero = new Hero();
					setHeroPos(i, j);
					break;
				}
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				switch (map[i][j]) {
				case 'k':
					Key.setX(i);
					Key.setY(j);
					break;
				case 'O':
					setOgrePos(i, j);
					break;

				}
			}

		}
	}

	/**
	 * Identifies only the game elements on the map (doesn't generate random ogres)
	 */
	public void findGameElementsOnlyInMap() {

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'H') {
					this.hero = new Hero();
					setHeroPos(i, j);
					break;
				}
			}
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				switch (map[i][j]) {  
				case 'k':
					Key.setX(i);
					Key.setY(j);
					break;
				case 'O':
					hordeSize++;
					map[i][j] = ' ';

					Ogre ogre = new Ogre(i, j);

					MOVEMENT_TYPE clubMov = ogre.getClub().getMove(map, ogre.getPosition());

					ogre.getClub().move(clubMov, map);

					this.crazyHorde.add(ogre);
					break;

				}
			}
		}
	}

	/**
	 * Handles hero and weapon part of creating a map to print
	 * @param mapToPrint
	 * 		- Map to be printed
	 */
	public void createMapToPrintHeroPart(char[][] mapToPrint) {

		int i, j;
		// ---- hero ----
		i = this.hero.getX();
		j = this.hero.getY();

		mapToPrint[i][j] = this.hero.getSymbol();

		if (!hero.isArmed()) {

			i = heroWeapon.getX();
			j = heroWeapon.getY();

			mapToPrint[i][j] = heroWeapon.getSymbol();
		}
	}

	/**
	 * Handles ogres and clubs part of creating a map to print
	 * @param mapToPrint
	 * 		- Map to be printed
	 */
	public void createMapToPrintOgrePart(char[][] mapToPrint) {

		int i, j;

		for (int k = 0; k < this.hordeSize; k++) {

			Ogre tempOgre = this.crazyHorde.elementAt(k);

			i = tempOgre.getX();
			j = tempOgre.getY();

			if (i == this.Key.getX() && j == this.Key.getY())
				mapToPrint[i][j] = '$';
			else
				mapToPrint[i][j] = tempOgre.getSymbol();

			i = tempOgre.getClub().getX();
			j = tempOgre.getClub().getY();

			if (map[i][j] == 'k')
				mapToPrint[i][j] = '$';
			else
				mapToPrint[i][j] = tempOgre.getClub().getSymbol();

		}
	}

	/**
	 * Creates a map with game elements to be printed 
	 * @return map to be printed
	 */
	public char[][] createMapToPrint() {

		char[][] mapToPrint = new char[map.length][];

		for (int i = 0; i < map.length; i++) {
			char[] line = map[i];
			int line_size = line.length;
			mapToPrint[i] = new char[line_size];
			System.arraycopy(line, 0, mapToPrint[i], 0, line_size);
		}

		createMapToPrintHeroPart(mapToPrint);

		createMapToPrintOgrePart(mapToPrint);

		return mapToPrint;
	}

	/**
	 * Moves the ogre and the club
	 */
	public void ogreMovement() {
		for (int i = 0; i < this.hordeSize; i++) {

			Ogre tempOgre = this.crazyHorde.elementAt(i);

			tempOgre.move(tempOgre.getMove(), map);

			MOVEMENT_TYPE clubMov = tempOgre.getClub().getMove(map, tempOgre.getPosition());

			tempOgre.getClub().move(clubMov, map);
		}
	}

	/**
	 * Handles the hero and weapon actions related to the performed movement
	 * @param x
	 * 		- hero x position
	 * @param y
	 * 		- hero y position
	 */
	public void heroActions(int x, int y) {
		if (x == Key.getX() && y == Key.getY()) {

			hero.setSymbol('K');

			hero.setKey(true);
			map[x][y] = ' ';

		} else if (x == heroWeapon.getX() && y == heroWeapon.getY()) {

			hero.setWeapon(heroWeapon);

			if (!hero.hasKey())
				hero.setSymbol('A');
			map[x][y] = ' ';

		}
	}

	/**
	 * Handles the hero movement
	 * 
	 * @param move
	 * 		- performed hero movement
	 * @param x
	 * 		- hero x position
	 * @param y
	 * 		- hero y position
	 */
	public void heroMovement(MOVEMENT_TYPE move, int x, int y) {

		if (hero.hasKey()) {

			switch (move) {
			case UP: {
				x--;
				break;
			}
			case DOWN: {
				x++;
				break;
			}
			case LEFT: {
				y--;
				break;
			}
			case RIGHT: {
				y++;
				break;
			}
			case NONE:
				break;
			}

			for (int i = 0; i < passageDoors.size(); i++) {

				int a = passageDoors.elementAt(i).getX();
				int b = passageDoors.elementAt(i).getY();

				if (x == a && y == b)
					map[a][b] = 'S';

			}

		}

	}

	/**
	 * Level state machine, responsible for the level game flow
	 * @param move
	 * 		- hero movement
	 * @return current level state
	 */
	public LEVEL_STATE updateLevel(MOVEMENT_TYPE move) {

		hero.move(move, map);

		ogreMovement();

		int x = hero.getX();
		int y = hero.getY();

		heroActions(x, y);

		heroMovement(move, x, y);

		if (map[hero.getX()][hero.getY()] == 'S')
			return LEVEL_STATE.PASSED_LEVEL;
		else if (this.checkOgreCollision())
			return LEVEL_STATE.DEATH;

		return LEVEL_STATE.NONE;
	}

	/**
	 * @return the key
	 */
	public Pair getKey() {
		return Key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Pair key) {
		Key = key;
	}

	/**
	 * @return the heroWeapon
	 */
	public Weapon getHeroWeapon() {
		return heroWeapon;
	}

	/**
	 * @param heroWeapon
	 *            the heroWeapon to set
	 */
	public void setHeroWeapon(Weapon heroWeapon) {
		this.heroWeapon = heroWeapon;
	}

	/**
	 * @return the hordeSize
	 */
	public int getHordeSize() {
		return hordeSize;
	}

	/**
	 * @param crazyHorde
	 *            the crazyHorde to set
	 */
	public void setCrazyHorde(Vector<Ogre> crazyHorde) {
		this.crazyHorde = crazyHorde;
	}

}
