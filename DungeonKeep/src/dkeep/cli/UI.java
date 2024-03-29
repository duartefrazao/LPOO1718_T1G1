package dkeep.cli;

import java.util.Scanner;

import dkeep.logic.MovingObject;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;

public class UI {
	Scanner scanner;
	public UI(Scanner s)
	{
		scanner = s;
	}
	public MovingObject.MOVEMENT_TYPE getMove()
	{
		System.out.print("Move: ");
		char movement = scanner.next().charAt(0);
		
		
		switch(movement) {
			case 'd':
				return MOVEMENT_TYPE.RIGHT;
			case 'w':
				return MOVEMENT_TYPE.UP;
			case 's':
				return MOVEMENT_TYPE.DOWN;
			case 'a':
				return MOVEMENT_TYPE.LEFT;
			default:
				return MOVEMENT_TYPE.NONE;
				
		}	
	}
	public void printMap(char[][] map) {
		for(int i = 0; i < map.length;i++)
		{
			for(int j = 0 ; j < map[i].length;j++)
			{
				System.out.print(map[i][j]+ " ");
			}
			System.out.println();
		}
		
	}
}
