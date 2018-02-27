package dkeep.logic;
import java.util.Vector;

public class Guard extends MovingObject{

    private char Symbol = 'G';

    public char getSymbol(){ return this.Symbol;};

    public void setSymbol(char symbol){ this.Symbol = symbol;  };

    private Vector<MOVEMENT_TYPE> guardMovement = new Vector<MovingObject.MOVEMENT_TYPE>(0);

    public static int currentMovPos = 0;

    public MovingObject.MOVEMENT_TYPE getMove(){
        MovingObject.MOVEMENT_TYPE move = guardMovement.elementAt(currentMovPos);
        currentMovPos++;
        currentMovPos = (currentMovPos % guardMovement.size());
        return move;
    }

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
