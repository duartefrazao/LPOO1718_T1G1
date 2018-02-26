import java.util.Vector;

public class Guard extends MovingObject{


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


    public Guard(char map[][])
    {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length;j++)
                if(map[i][j]== 'G')
                {
                    position.setX(i);
                    position.setY(j);
                    map[i][j] = ' ';
                }

        }

        fillMovement();
    }



}
