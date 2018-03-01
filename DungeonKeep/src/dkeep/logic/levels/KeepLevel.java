package dkeep.logic.levels;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import dkeep.logic.Hero;
import dkeep.logic.MovingObject;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;


public class KeepLevel extends Level {

	public KeepLevel(char[][] level, Hero globalHero) {
		super(level, globalHero);

		findGameElements();
		findPassageDoors();
	}

	private Vector<Ogre> crazyHorde = new Vector<Ogre>(0);

	private Pair Lever = new Pair(0, 0);

	// will be used next
	private int hordeSize = ThreadLocalRandom.current().nextInt(1, 3 + 1);

	private boolean checkOgreCollision() {

		for (int i = 0; i < this.hordeSize; i++) {

			Ogre tempOgre = this.crazyHorde.elementAt(i);

			if (this.collision(tempOgre, 1) || this.collision(tempOgre.getClub(), 1))
				return true;
		}

		return false;
	}

	public void findGameElements() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				switch (map[i][j]) {
				case 'k':
					Lever.setX(i);
					Lever.setY(j);
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

		// ---- crazy horde ----
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

		for (int i = 0; i < this.hordeSize; i++) {

			Ogre tempOgre = this.crazyHorde.elementAt(i);

			tempOgre.move(tempOgre.getMove(), map);
			tempOgre.getClub().move(map, tempOgre.getPosition());
		}

		if (map[hero.getX()][hero.getY()] == 'S')
			return LEVEL_STATE.PASSED_LEVEL;
		else if (this.checkOgreCollision())
			return LEVEL_STATE.DEATH;

		return LEVEL_STATE.NONE;
	}

}
