import java.util.Scanner;

public class Hero {

	private Pair position = new Pair(0,0);
	
	public enum MOVEMENT_TYPE {
	    UP, DOWN, LEFT, RIGHT, NONE
	}
	public Hero(char map[][])
	{
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length;j++)
				if(map[i][j]== 'H') 
				{
					position.setX(i);
					position.setY(j);
					map[i][j] = ' ';
				}
			
		}
	}
	
	
	
	public int getX() { 
		return position.getX();
	}
	
	public int getY() {
		return position.getY();
	}
	
	public void setX(int a) {
		position.setX(a);
	}
	public void setY(int a) {
		position.setY(a);
	}
	
	public Hero.MOVEMENT_TYPE move(char map[][])
	{
		System.out.print("Move: ");
		Scanner s = new Scanner(System.in);
		char movement = s.next().charAt(0);
		
		
		switch(movement) {
			case 'd':
				return Hero.MOVEMENT_TYPE.RIGHT;
			case 'w':
				return Hero.MOVEMENT_TYPE.UP;
			case 's':
				return Hero.MOVEMENT_TYPE.DOWN;
			case 'a':
				return Hero.MOVEMENT_TYPE.LEFT;
			default:
				return Hero.MOVEMENT_TYPE.NONE;
				
		}	
	}
}
