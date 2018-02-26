import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Level {

    private char map[][];

    private Hero hero;

    private boolean guardDefined = false;
    private Guard guard;

    private boolean ogreDefined = false;
    private Ogre ogre;

    private boolean terminate = false;
    private boolean won = false;

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
                        guard.setX(i);
                        guard.setY(j);
                        break;

                    case 'O':
                        ogre.setX(i);
                        ogre.setY(j);
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

    public Level(char level[][], boolean hasGuard, boolean hasOgre) {
        map = level;

        hero = new Hero(map);

        if (hasGuard) {
            guardDefined = true;
            guard = new Guard(map);
        }

        if (hasOgre) {
            ogre = new Ogre(map);
            ogreDefined = true;
        }

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
                } else
                    System.out.print(map[i][j] + " ");

            }

            System.out.println();
        }
    }

    public void updateGame() {
        MovingObject.MOVEMENT_TYPE move = hero.getMove();

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

        if (ogreDefined)
            ogre.move(ogre.getMove(), map);


        if (map[hero.getX()][hero.getY()] == 'S') {
            won = true;
            terminate = true;
            System.out.println("You Won! Going to the next Level...\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return;
        }

        if ((guardDefined && collision(guard)) || (ogreDefined && collision(ogre))) {
            won = false;
            terminate = true;
            System.out.println("You Lost!\n");
            return;
        }


    }

    public boolean game() {
        printMap();
        do {
            updateGame();
            printMap();
        } while (!terminate);

        return won;

    }

}
