import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends MovingObject {

    private char Symbol = '*';

    public char getSymbol(){
        return Symbol;
    }


    public void move(char map[][], Pair A) {

        int x = A.getX();
        int y = A.getY();

        Vector<Pair> possibleMovs = new Vector<Pair>(0);


        if (map[x - 1][y] != 'X' && map[x - 1][y] != 'I') {
            possibleMovs.addElement(new Pair(x-1,y));
        }
        if (map[x + 1][y] != 'X' && map[x + 1][y] != 'I') {
            possibleMovs.addElement(new Pair(x+1,y));
        }
        if (map[x][y - 1] != 'X' && map[x][y - 1] != 'I') {
            possibleMovs.addElement(new Pair(x,y-1));
        }
        if (map[x][y + 1] != 'X' && map[x][y + 1] != 'I') {
            possibleMovs.addElement(new Pair(x,y+1));
        }

        int rand = ThreadLocalRandom.current().nextInt(0, possibleMovs.size());

        setX(possibleMovs.elementAt(rand).getX());
        setY(possibleMovs.elementAt(rand).getY());

    }

    public Weapon(char [][] map, Pair A) {
        move(map, A);
    }


}
