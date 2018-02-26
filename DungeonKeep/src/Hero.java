import java.util.Scanner;

public class Hero extends MovingObject {

	
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
	public MovingObject.MOVEMENT_TYPE getMove(char map[][])
	{
		System.out.print("Move: ");
		Scanner s = new Scanner(System.in);
		char movement = s.next().charAt(0);
		
		
		switch(movement) {
			case 'd':
				return MOVEMENT_TYPE.RIGHT;
			case 'w':
				return MOVEMENT_TYPE.UP;
			case 's':
				return MOVEMENT_TYPE.DOWN;
			case 'a':
				return MOVEMENT_TYPE.LEFT;
			default:
				return MOVEMENT_TYPE.NONE;
				
		}	
	}
}
