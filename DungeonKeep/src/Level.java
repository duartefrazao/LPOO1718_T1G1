
public class Level {
	private char map[][];
	private Hero hero;
	private boolean leverOn = false;
	private boolean winningCondition = false;
	private Pair Lever = new Pair(0,0);


	private void findLever(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length;j++)
                if(map[i][j]== 'k')
                {
                    Lever.setX(i);
                    Lever.setY(j);
                }

        }
    }

	public Level(char level[][]) {
		map = level;
		hero = new Hero(map);
		findLever();
	}

	public void printMap() {


	    for (int i = 0; i < map.length; i++){
	        for (int j = 0; j < map[i].length; j++){

	            if(hero.getX() == i && hero.getY() == j){
	                System.out.print("H ");
                }
                else
                    System.out.print(map[i][j] + " " );

            }

            System.out.println();
        }
	}

	public void updateGame() {
		Hero.MOVEMENT_TYPE move = hero.move(map);

		int x = hero.getX();
		int y = hero.getY();


		if( x == Lever.getX() && y == Lever.getY()){


        }

		switch (move) {

		case UP: {
			if (map[x - 1][y] != 'X' && map[x - 1][y] != 'I') {
				hero.setX(--x);
			}
			break;
		}
		case DOWN: {
			if (map[x + 1][y] != 'X' && map[x + 1][y] != 'I') {
				hero.setX(++x);
			}
			break;
		}
		case LEFT: {
			if (map[x][y - 1] != 'X' && map[x][y - 1] != 'I') {
				hero.setY(--y);
			}
			break;
		}
		case RIGHT: {
			if (map[x][y + 1] != 'X' && map[x][y + 1] != 'I') {
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
