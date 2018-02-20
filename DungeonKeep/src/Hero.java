import java.util.Scanner;

public class Hero {
	private int x;
	private int y;
	
	public enum MOVEMENT_TYPE {
	    UP, DOWN, LEFT, RIGHT, NONE
	}
	public Hero(char map[][])
	{
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length;j++)
				if(map[i][j]== 'H') 
				{
					x=i;
					y=j;
				}
			
		}
	}
	
	
	
	public int getX() { 
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int a) {
		x=a;
	}
	public void setY(int a) {
		y=a;
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
