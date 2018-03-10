package dkeep.cli;

import java.util.Scanner;
import java.util.Vector;
import dkeep.logic.*;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

public class Main {
	
	public static void main(String args[]) {
		
		Hero hero = new Hero();
		Scanner s = new Scanner(System.in);
		UI userInterface = new UI(s);

		Level level2 = new KeepLevel( hero);
		Level level1 = new InitialLevel( hero);
		
		Vector<Level> levels = new Vector<Level>();
		levels.add(level1);
		levels.add(level2);
		
		Dungeon d = new Dungeon(levels);
		
		Dungeon.GAME_STATE state; 
		
	    do {
	    	
	        userInterface.printMap(d.getMap());
	        
	        state = d.game(userInterface.getMove());
	        
	    } while (state == Dungeon.GAME_STATE.PLAYING);
	    
	    userInterface.printMap(d.getMap());
	    

		s.close();
	
	}
}
