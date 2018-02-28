package dkeep.cli;

import dkeep.logic.Level;

import java.util.Scanner;
import java.util.Vector;
import dkeep.logic.*;

public class Main {
	


	static char map1[][] =
		{
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
        {'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X'},
        {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
        {'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
        {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
        {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
        {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
        {'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
        {'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X'},
        {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
        };

	static char map2[][] =
		{
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
		{'I', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'k', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
		};
	
	
	
	public static void main(String args[]) {
		
		Hero hero = new Hero();
		Scanner s = new Scanner(System.in);
		UI userInterface = new UI(s);

		Level level2 = new Level(map2, hero);
		Level level1 = new Level(map1, hero);
		
		Vector<Level> levels = new Vector<Level>();
		levels.add(level1);
		levels.add(level2);
		
		Dungeon d = new Dungeon(levels);
		
		Dungeon.GAME_STATE state;
		
	    do {
	    	
	        d.printMap();
	        
	        state = d.game(userInterface.getMove());
	        
	    } while (state == Dungeon.GAME_STATE.PLAYING);
	    
	    d.printMap();
	    

		s.close();
	
	}
}
