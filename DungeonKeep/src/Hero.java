import java.util.Scanner;

public class Hero extends MovingObject {

    private char Symbol = 'H';

    private boolean hasKey = false;

    public boolean hasKey(){ return hasKey;}


    public char getSymbol(){ return this.Symbol;}

    public void setSymbol(char symbol){
    	if(symbol == 'K')
    		hasKey = true;

    	this.Symbol = symbol;
    }
	
	public Hero(char map[][])
	{
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length;j++)
				if(map[i][j]== Symbol)
				{
					position.setX(i);
					position.setY(j);
					map[i][j] = ' ';
				}
			
		}
	}
	public MovingObject.MOVEMENT_TYPE getMove()
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
