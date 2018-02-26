import java.util.Vector;

public class Level {
    private char map[][];
    private Hero hero;
    private boolean leverOn = false;
    private boolean terminate = false;
    private Pair Lever = new Pair(0, 0);
    private Guard guard;

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
                }
            }

        }
    }


    private boolean collision(MovingObject A){

        int hero_x = hero.getX();
        int hero_y = hero.getY();

        int obj_x = A.getX();
        int obj_y = A.getY();


        if((Math.abs(hero_x - obj_x) + Math.abs(hero_y - obj_y)) <= 1)
            return true;

        return false;

    }

    public Level(char level[][]) {
        map = level;
        hero = new Hero(map);
        guard = new Guard(map);
        findGameElements();
        findPassageDoors();
    }

    public void printMap() {


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                if (hero.getX() == i && hero.getY() == j)
                    System.out.print("H ");
                else if(guard.getX() == i && guard.getY() == j)
                    System.out.print("G ");
                else
                    System.out.print(map[i][j] + " ");

            }

            System.out.println();
        }
    }

    public void updateGame() {
    	MovingObject.MOVEMENT_TYPE move = hero.getMove(map);

        int x = hero.getX();
        int y = hero.getY();


        if (x == Lever.getX() && y == Lever.getY()) {

            for (int i = 0; i < passageDoors.size(); i++) {

                int a = passageDoors.elementAt(i).getX();
                int b = passageDoors.elementAt(i).getY();

                map[a][b] = 'S';


            }

        }

        hero.move(move, map);
        guard.move(guard.getMove(), map);


        if (map[hero.getX()][hero.getY()] == 'S' || collision(guard))
            terminate = true;

    }

    public void game() {
        printMap();
        do {
            updateGame();
            printMap();
        } while (!terminate);

    }

}
