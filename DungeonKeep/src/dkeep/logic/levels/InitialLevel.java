package dkeep.logic.levels;

import dkeep.logic.Hero;
import dkeep.logic.MovingObject;
import dkeep.logic.Pair;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.guards.*;

public class InitialLevel extends Level {
	
	public InitialLevel(char[][] level, Hero globalHero) {
		super(level, globalHero);

		findGameElements();
		findPassageDoors();
	}

	private boolean guardDefined = false;
	
	private Guard guard;

	private Pair Lever = new Pair(0, 0);
	
	public void findGameElements()
	{
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

		// ---- guards ----

		if (guardDefined) {
			i = this.guard.getX();
			j = this.guard.getY();

			mapToPrint[i][j] = this.guard.getSymbol();
		}


		return mapToPrint;
	}
	
	public LEVEL_STATE updateLevel(MovingObject.MOVEMENT_TYPE move) {

		int x = hero.getX();
		int y = hero.getY();

		if (x == Lever.getX() && y == Lever.getY()) {
			
			
			for(int i = 0; i < this.passageDoors.size(); i++) {
				int door_x = passageDoors.elementAt(i).getX();
				int door_y = passageDoors.elementAt(i).getY();
				
				map[door_x][door_y] = 'S';
			}
			
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

		

		if (map[hero.getX()][hero.getY()] == 'S')
			return LEVEL_STATE.PASSED_LEVEL;
        	//Needs to test with 1 space difference for general guard and 
        	//With 0 space difference (adjacent) for a sleeping guard
        	if(guard.getSymbol() == 'G' && collision(guard, 1)) return LEVEL_STATE.DEATH;
        	else if(guard.getSymbol() == 'g' && collision(guard, 0)) return LEVEL_STATE.DEATH;
       
		
		return LEVEL_STATE.NONE;
	}

}
