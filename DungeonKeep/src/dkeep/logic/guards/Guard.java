package dkeep.logic.guards;
import java.io.Serializable;
import java.util.Vector;

import dkeep.logic.MovingObject;

public abstract class Guard extends MovingObject implements Serializable{
	
	
    protected char Symbol = 'G';

    public char getSymbol(){ return this.Symbol;};

    public void setSymbol(char symbol){ this.Symbol = symbol;  };

    protected Vector<MOVEMENT_TYPE> guardMovement = new Vector<MovingObject.MOVEMENT_TYPE>(0);

    public  int currentMovPos = 0;

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
    
  

    public Guard(int x, int y)
    {
    	position.setX(x);
        position.setY(y);
     

        fillMovement();
    }



}
