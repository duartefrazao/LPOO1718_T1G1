package dkeep.logic.levels;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;
import dkeep.logic.Weapon;

public class KeepLevel extends Level {

	private boolean isRandomlyGenerated;

	public KeepLevel() {
		this(ThreadLocalRandom.current().nextInt(1, 3 + 1));
	}
 
	private char[][] initialMap;

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

	private Vector<Ogre> crazyHorde = new Vector<Ogre>(0);

	private Pair Key = new Pair(0, 0);

	private Weapon heroWeapon;

	public Vector<Ogre> getCrazyHorde() {
		return crazyHorde;
	}

	private int hordeSize;

	public void setHordeSize(int size) {
		this.hordeSize = size;
	}

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

	@Override
	public void findPassageDoors() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				if (map[i][j] == 'I')
					passageDoors.add(new Pair(i, j));
			}
		}

	}

	public void setHeroPos(int i, int j) {
		hero.setX(i);
		hero.setY(j);
		map[i][j] = ' ';

		heroOriginalPos.setX(i);
		heroOriginalPos.setY(j);

		MOVEMENT_TYPE swordMove = heroWeapon.getMove(map, hero.getPosition());

		heroWeapon.move(swordMove, map);
	}

	public void findGameElements() {
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
				case 'H':
					this.hero = new Hero();
					setHeroPos(i, j);
					break;

				}
			}

		}
	}

	public void findGameElementsOnlyInMap() {
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
				case 'H':
					this.hero = new Hero();
					setHeroPos(i, j);
					break;

				}
			}
		}
	}

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

	public void ogreMovement() {
		for (int i = 0; i < this.hordeSize; i++) {

			Ogre tempOgre = this.crazyHorde.elementAt(i);

			tempOgre.move(tempOgre.getMove(), map);

			MOVEMENT_TYPE clubMov = tempOgre.getClub().getMove(map, tempOgre.getPosition());

			tempOgre.getClub().move(clubMov, map);
		}
	}

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
