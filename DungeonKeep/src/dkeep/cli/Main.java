package dkeep.cli;

import dkeep.logic.Level;

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

		Level level1 = new Level(map1, true, false);
		Level level2 = new Level(map2, false, true);


		//if( level1.game() == true)
		level2.game();

		
	
	}
}