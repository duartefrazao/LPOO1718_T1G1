package dkeep.logic;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends MovingObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1907280161972033828L;
	
	private char Symbol = '*';

	/**
	 * Sets the new x position of the object
	 * 
	 * @returns weapon symbol
	 */
    public char getSymbol(){
        return Symbol;
    }
     
	/**
	 * Gets a new valid random movement for the weapon
	 * 
	 * @param map
	 * 		-map to move weapon
	 * @param heroPos
	 * 		-hero position
	 * 
	 * @returns random valid weapon movement
	 */
    public MOVEMENT_TYPE getMove(char map[][], Pair heroPos) {
    	
    	 
    	this.setX(heroPos.getX());
    	this.setY(heroPos.getY());
    	
    	int x = heroPos.getX();
        int y = heroPos.getY();

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

   
	/**
	 * Weapon constructor
	 * @returns new weapon object
	 */
    public Weapon() {
    	super();
    }


}
