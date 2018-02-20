
public class Level {
	private char map[][];
	private Hero hero;
	private boolean leverOn = false;
	private boolean winningCondition = false;

	public Level(char level[][]) {
		map = level;
		hero = new Hero(map);
	}

	public void printMap() {
		for (char[] row : map) {
			for (char i : row)
				System.out.print(i + " ");
			System.out.println();
		}
	}

	public void updateGame() {
		Hero.MOVEMENT_TYPE move = hero.move(map);

		int x = hero.getX();
		int y = hero.getY();

		switch (move) {

		case UP: {
			if (map[x - 1][y] != 'X' && map[x - 1][y] != 'I') {
				map[x][y] = ' ';
				map[x - 1][y] = 'H';
				hero.setX(--x);
			}
			break;
		}
		case DOWN: {
			if (map[x + 1][y] != 'X' && map[x + 1][y] != 'I') {
				map[x][y] = ' ';
				map[x + 1][y] = 'H';
				hero.setX(++x);
			}
			break;
		}
		case LEFT: {
			if (map[x][y - 1] != 'X' && map[x][y - 1] != 'I') {
				map[x][y] = ' ';
				map[x][y - 1] = 'H';
				hero.setY(--y);
			}
			break;
		}
		case RIGHT: {
			if (map[x][y + 1] != 'X' && map[x][y + 1] != 'I') {
				map[x][y] = ' ';
				map[x][y + 1] = 'H';
				hero.setY(++y);
			}
			break;
		}

		}
	}

	public void game() {
		do {
			printMap();
			updateGame();
		} while (!winningCondition);

	}

}
