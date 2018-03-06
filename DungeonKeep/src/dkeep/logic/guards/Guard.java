package dkeep.logic.guards;
import java.util.Vector;

import dkeep.logic.MovingObject;

public abstract class Guard extends MovingObject{
	
	
    protected char Symbol = 'G';

    public char getSymbol(){ return this.Symbol;};

    public void setSymbol(char symbol){ this.Symbol = symbol;  };

    protected Vector<MOVEMENT_TYPE> guardMovement = new Vector<MovingObject.MOVEMENT_TYPE>(0);

    public static int currentMovPos = 0;

    public abstract MovingObject.MOVEMENT_TYPE getMove();

    private void fillMovement(){

        guardMovement.add(MovingObject.MOVEMENT_TYPE.LEFT);

        for(int i = 0; i < 4; i++)
            guardMovement.add(MovingObject.MOVEMENT_TYPE.DOWN);

        for(int i = 0; i < 6; i++)
            guardMovement.add(MovingObject.MOVEMENT_TYPE.LEFT);
 
        guardMovement.add(MovingObject.MOVEMENT_TYPE.DOWN);

        for(int i = 0; i < 7; i++)
            guardMovement.add(MovingObject.MOVEMENT_TYPE.RIGHT);

        for(int i = 0; i < 5; i++)
            guardMovement.add(MovingObject.MOVEMENT_TYPE.UP);
    }
    
    protected MovingObject.MOVEMENT_TYPE contrary(MovingObject.MOVEMENT_TYPE move) {
    	
    	MovingObject.MOVEMENT_TYPE contraryMove;
    	
    	switch (move)
    	{
    		case UP:
    			contraryMove=MovingObject.MOVEMENT_TYPE.DOWN;
    			break;
    		case DOWN:
    			contraryMove=MovingObject.MOVEMENT_TYPE.UP;
    			break;
    		case RIGHT:
    			contraryMove=MovingObject.MOVEMENT_TYPE.LEFT;
    			break;
    		case LEFT:
    			contraryMove=MovingObject.MOVEMENT_TYPE.RIGHT;
    			break;
    		default:
    			contraryMove=MovingObject.MOVEMENT_TYPE.NONE;
    	}
    	
    	return contraryMove;
    }

    public Guard(int x, int y)
    {
    	position.setX(x);
        position.setY(y);
     

        fillMovement();
    }



}
