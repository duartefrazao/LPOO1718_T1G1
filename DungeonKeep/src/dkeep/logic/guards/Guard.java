package dkeep.logic.guards;
import java.io.Serializable;
import java.util.Vector;

import dkeep.logic.MovingObject;

public abstract class Guard extends MovingObject implements Serializable{
	
	
 
	private static final long serialVersionUID = -8052894892352592636L;

	protected char Symbol = 'G';
	
    protected Vector<MOVEMENT_TYPE> guardMovement = new Vector<MOVEMENT_TYPE>(0);

    public  int currentMovPos = 0;
	
    
    /**
     * Gets the guard symbol
     * @return the guard symbol
     */
    public char getSymbol(){ return this.Symbol;};

    /**
     * Sets a new guard symbol
     * @param symbol
     * 		-new guard symbol
     */
    public void setSymbol(char symbol){ this.Symbol = symbol;  };

    /**
     * Gets a new guard movement respecting restrictions
     * @return new guard movent
     *
     */
    public abstract MOVEMENT_TYPE getMove();

    /**
     * fills the movement of the initial level in the movements vector
     */
    private void fillMovement(){

        guardMovement.add(MOVEMENT_TYPE.LEFT);

        for(int i = 0; i < 4; i++)
            guardMovement.add(MOVEMENT_TYPE.DOWN);

        for(int i = 0; i < 6; i++)
            guardMovement.add(MOVEMENT_TYPE.LEFT);
 
        guardMovement.add(MOVEMENT_TYPE.DOWN);

        for(int i = 0; i < 7; i++)
            guardMovement.add(MOVEMENT_TYPE.RIGHT);

        for(int i = 0; i < 5; i++)
            guardMovement.add(MOVEMENT_TYPE.UP);
    }
    
  /**
   * Guard constructor
   * 
   * @param x
   * 		- guard x position
   * @param y
   * 		- guard y position
   * 
   */

    public Guard(int x, int y)
    {
    	position.setX(x);
        position.setY(y);
     

        fillMovement();
    }




}
