package dkeep.logic.levels;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import dkeep.logic.Hero; 
import dkeep.logic.MovingObject;
import dkeep.logic.Pair;


public abstract class Level implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8341778413317578800L;

	public enum LEVEL_STATE {
		PASSED_LEVEL, DEATH, NONE
	}

	protected char map[][];

	protected Pair heroOriginalPos = new Pair(0, 0);

	protected Hero hero;

	protected Vector<Pair> passageDoors = new Vector<Pair>(0);



	public Pair getRandomEmptyPositions() {

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

	public abstract void resetGameElements();
	
	public abstract void findGameElements();
	
	public abstract char[][] createMapToPrint();
	
	public abstract LEVEL_STATE updateLevel(MovingObject.MOVEMENT_TYPE move);

	public boolean collision(MovingObject A, int spacing) {

		int hero_x = hero.getX();
		int hero_y = hero.getY();

		int obj_x = A.getX();
		int obj_y = A.getY();

		if ((Math.abs(hero_x - obj_x) + Math.abs(hero_y - obj_y)) <= spacing)
			return true;

		return false;

	} 

	public Level() {
	}

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
	 * @param hero the hero to set
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public char[][] getMap() {
		return this.map;
	}
	

}
