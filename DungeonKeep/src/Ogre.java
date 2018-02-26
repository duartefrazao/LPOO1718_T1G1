import java.util.concurrent.ThreadLocalRandom;

public class Ogre extends MovingObject {

    private char Symbol = 'O';

    public MovingObject.MOVEMENT_TYPE getMove() {

        int x = ThreadLocalRandom.current().nextInt(0, 3 + 1);

        switch (x) {

            case 0:
                return MOVEMENT_TYPE.UP;
            case 1:
                return MOVEMENT_TYPE.DOWN;
            case 2:
                return MOVEMENT_TYPE.RIGHT;
            case 3:
                return MOVEMENT_TYPE.LEFT;
            default:
                return MOVEMENT_TYPE.NONE;

        }

    }

    public char getSymbol(){ return this.Symbol;}

    public void setSymbol(char symbol){ this.Symbol = symbol;  }


    public Ogre(char map[][]) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                if (map[i][j] == Symbol) {
                    position.setX(i);
                    position.setY(j);
                    map[i][j] = ' ';
                }

        }
    }

}
