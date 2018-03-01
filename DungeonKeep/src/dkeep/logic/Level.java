package dkeep.logic;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import dkeep.logic.guards.*;

public class Level {

	public enum LEVEL_STATE {
		PASSED_LEVEL, DEATH, NONE
	}

	private char map[][];

	private Pair heroOriginalPos = new Pair(0, 0);

	private Hero hero;
	
	private boolean guardDefined = false;
	
	private Guard guard;

	private Pair Lever = new Pair(0, 0);

	private Vector<Pair> passageDoors = new Vector<Pair>(0);

	private Vector<Ogre> crazyHorde = new Vector<Ogre>(0);

	private boolean hordeDefined = false;

	// will be used next
	private int hordeSize = ThreadLocalRandom.current().nextInt(1, 3 + 1);

	private Pair getRandomEmptyPositions() {

		Pair randomPos = new Pair(0, 0);

		boolean foundIt = false;

		while (!foundIt) {
			int x = ThreadLocalRandom.current().nextInt(0, map.length - 2);
			int y = ThreadLocalRandom.current().nextInt(0, map[x].length - 2);

			if (this.map[x][y] == ' ') {

				foundIt = true;

				randomPos.setX(x);
				randomPos.setY(y);
			}
		}

		return randomPos;
	}

	private boolean checkOgreCollision() {

		if (hordeDefined) {

			for (int i = 0; i < this.hordeSize; i++) {

               
				Ogre tempOgre = this.crazyHorde.elementAt(i);

				if (this.collision(tempOgre, 1) || this.collision(tempOgre.getClub(), 1))
					return true;
			}

		}

		return false;
	}

	private void findPassageDoors() {

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

	private void findGameElements() {
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
				case 'O':
					map[i][j] = ' ';

					for (int k = 0; k < this.hordeSize; k++) {

						Pair randomPos = this.getRandomEmptyPositions();

						Ogre ogre = new Ogre(randomPos.getX(), randomPos.getY());

						/*
						 * now we need to move the weapon one time to place it in a random valid
						 * position
						 */
						ogre.getClub().move(map, ogre.getPosition());

						this.crazyHorde.add(ogre);

					}

					this.hordeDefined = true;

					break;
				case 'H':
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

	private boolean collision(MovingObject A, int spacing) {

		int hero_x = hero.getX();
		int hero_y = hero.getY();

		int obj_x = A.getX();
		int obj_y = A.getY();

		if ((Math.abs(hero_x - obj_x) + Math.abs(hero_y - obj_y)) <= spacing)
			return true;

		return false;

	}

	public Level(char level[][], Hero globalHero) {
		this.map = level;
		this.hero = globalHero;

		findGameElements();
		findPassageDoors();
	}

	public void resetElements() {
		hero.setX(heroOriginalPos.getX());
		hero.setY(heroOriginalPos.getY());
		hero.setSymbol('H');
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

		// ---- crazy horde ----

		if (hordeDefined) {

			for (int k = 0; k < this.hordeSize; k++) {

				Ogre tempOgre = this.crazyHorde.elementAt(k);

				i = tempOgre.getX();
				j = tempOgre.getY();

				if (i == this.Lever.getX() && j == this.Lever.getY())
					mapToPrint[i][j] = '$';
				else
					mapToPrint[i][j] = tempOgre.getSymbol();

				// his club must be stored as well
				i = tempOgre.getClub().getX();
				j = tempOgre.getClub().getY();

				if (i == this.Lever.getX() && j == this.Lever.getY())
					mapToPrint[i][j] = '$';
				else
					mapToPrint[i][j] = tempOgre.getClub().getSymbol();

			}

		}

		return mapToPrint;
	}

	public LEVEL_STATE updateLevel(MovingObject.MOVEMENT_TYPE move) {

		int x = hero.getX();
		int y = hero.getY();

		if (x == Lever.getX() && y == Lever.getY()) {

			hero.setSymbol('K');
			map[x][y] = ' ';

		}

		hero.move(move, map);

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

		if (guardDefined)
			guard.move(guard.getMove(), map);

		if (hordeDefined) {

			for (int i = 0; i < this.hordeSize; i++) {

				Ogre tempOgre = this.crazyHorde.elementAt(i);

				tempOgre.move(tempOgre.getMove(), map);
				tempOgre.getClub().move(map, tempOgre.getPosition());
			}

		}

		if (map[hero.getX()][hero.getY()] == 'S')
			return LEVEL_STATE.PASSED_LEVEL;
		else if (this.checkOgreCollision()) 
        	return LEVEL_STATE.DEATH;
        else if(guardDefined) {
        	
        	//Needs to test with 1 space difference for general guard and 
        	//With 0 space difference (adjacent) for a sleeping guard
        	if(guard.getSymbol() == 'G' && collision(guard, 1)) return LEVEL_STATE.DEATH;
        	else if(guard.getSymbol() == 'g' && collision(guard, 0)) return LEVEL_STATE.DEATH;
        }
		
		return LEVEL_STATE.NONE;
	}

}
