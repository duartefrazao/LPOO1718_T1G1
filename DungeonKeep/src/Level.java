
public class Level {
	private char map[][];
	private Hero hero;
	private boolean leverOn=false;
	private boolean winningCondition =false;
	
	
	public Level(char level[][])
	{
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
	
	public void updateGame()
	{
		hero.move(map);
	}
	
	public void game()
	{
		do {
			printMap();
			updateGame();
		}while(!winningCondition);
		
	}
	
}
