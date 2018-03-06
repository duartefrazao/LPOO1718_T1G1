package dkeep.logic;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends MovingObject {

    private char Symbol = '*';

    public char getSymbol(){
        return Symbol;
    }
    
    public MOVEMENT_TYPE getMove(char map[][], Pair A) {
    	
    	
    	this.setX(A.getX());
    	this.setY(A.getY());
    	
    	int x = A.getX();
        int y = A.getY();

        Vector<MOVEMENT_TYPE> possibleMovs = new Vector<>();


        if (map[x - 1][y] != 'X' && map[x - 1][y] != 'I') {
            possibleMovs.addElement(MOVEMENT_TYPE.UP);
        }
        if (map[x + 1][y] != 'X' && map[x + 1][y] != 'I') {
            possibleMovs.addElement(MOVEMENT_TYPE.DOWN);
        }
        if (map[x][y - 1] != 'X' && map[x][y - 1] != 'I') {
            possibleMovs.addElement(MOVEMENT_TYPE.LEFT);
        }
        if (map[x][y + 1] != 'X' && map[x][y + 1] != 'I') {
            possibleMovs.addElement(MOVEMENT_TYPE.RIGHT);
        }

        int rand = ThreadLocalRandom.current().nextInt(0, possibleMovs.size());
    	
        
        return possibleMovs.elementAt(rand);
    }

   

    public Weapon() {
    	super();
    }


}
