package dkeep.logic;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Level {
	
	public enum LEVEL_STATE{
		PASSED_LEVEL, DEATH, NONE
	}
	
    private char map[][];

    private Hero hero;

    private boolean guardDefined = false;
    private Guard guard;

    private boolean ogreDefined = false;
    private Ogre ogre;


    private Pair Lever = new Pair(0, 0);


    private Vector<Pair> passageDoors = new Vector<Pair>(0);


    private void findPassageDoors() {

        for (int i = 0; i < map.length; i++) {

            if (i == 0 || i == (map.length - 1)) {
                for (int j = 0; j < map[i].length; j++) {

                    if (map[i][j] == 'I')
                        passageDoors.add(new Pair(i, j));
                }
            } else {
                if (map[i][0] == 'I') {
                    Pair newDoor = new Pair(i, 0);
                    passageDoors.add(newDoor);
                }
                if (map[i][map[i].length - 1] == 'I')
                    passageDoors.add(new Pair(i, map[i].length - 1));
            }


        }
    }


    private void findGameElements() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                switch (map[i][j]) {
                    case 'k':
                        Lever.setX(i);
                        Lever.setY(j);
                        break;
                    case 'G':
                        guard= new Guard(i,j);
                        map[i][j]= ' ';
                        guardDefined = true;
                        break;
                    case 'O':
                    	ogre = new Ogre(i,j);
                    	map[i][j]= ' ';
                        ogreDefined = true;
                        break;
                    case 'H':
                    	hero.setX(i);
                    	hero.setY(j);
                    	map[i][j]= ' ';
                    	break;
                    	
                }
            }

        }
    }


    private boolean collision(MovingObject A) {

        int hero_x = hero.getX();
        int hero_y = hero.getY();

        int obj_x = A.getX();
        int obj_y = A.getY();


        if ((Math.abs(hero_x - obj_x) + Math.abs(hero_y - obj_y)) <= 1)
            return true;

        return false;

    }

    public Level(char level[][], Hero globalHero) {
        this.map = level;
        this.hero = globalHero;
        
        findGameElements();
        findPassageDoors();
    }
    
    public void resetElements()
    {
    	hero.setSymbol('H');
    	findGameElements();
        findPassageDoors();
    }
    
    

    public void printMap() {


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                if (hero.getX() == i && hero.getY() == j)
                    System.out.print(hero.getSymbol() + " ");

                else if (guardDefined && guard.getX() == i && guard.getY() == j)
                    System.out.print(guard.getSymbol() + " ");

                else if (ogreDefined && ogre.getX() == i && ogre.getY() == j) {
                    if (ogre.getX() == Lever.getX() && ogre.getY() == Lever.getY())
                        System.out.print("$ ");
                    else
                        System.out.print(ogre.getSymbol() + " ");

                }
                else if(ogreDefined && ogre.getClub().getX() == i && ogre.getClub().getY() == j){

                    if(ogre.getClub().getX() == Lever.getX() && ogre.getClub().getY() == Lever.getY())
                        System.out.print("$ ");
                    else
                        System.out.print(ogre.getClub().getSymbol() + " ");

                }
                else
                    System.out.print(map[i][j] + " ");

            }

            System.out.println();
        }
    }

    public LEVEL_STATE updateLevel(MovingObject.MOVEMENT_TYPE move) {

        int x = hero.getX();
        int y = hero.getY();


        if (x == Lever.getX() && y == Lever.getY()) {

            hero.setSymbol('K');
            map[x][y] = ' ';

        }

        hero.move(move, map);

        if (hero.hasKey()) {


            switch (move) {
                case UP: {
                    x--;
                    break;
                }
                case DOWN: {
                    x++;
                    break;
                }
                case LEFT: {
                    y--;
                    break;
                }
                case RIGHT: {
                    y++;
                    break;
                }
            }

            for (int i = 0; i < passageDoors.size(); i++) {

                int a = passageDoors.elementAt(i).getX();
                int b = passageDoors.elementAt(i).getY();

                if (x == a && y == b)
                    map[a][b] = 'S';

            }

        }

        if (guardDefined)
            guard.move(guard.getMove(), map);

        if (ogreDefined) {
            ogre.move(ogre.getMove(), map);
            ogre.getClub().move(map, ogre.getPosition());
        }

        if (map[hero.getX()][hero.getY()] == 'S')
            return LEVEL_STATE.PASSED_LEVEL;
        else if ((guardDefined && collision(guard)) || (ogreDefined && collision(ogre)) || (ogreDefined && collision(ogre.getClub()))) 
        	return LEVEL_STATE.DEATH;
     

    	return LEVEL_STATE.NONE;
    }

}
