package dkeep.test;

import static org.junit.Assert.*;


import org.junit.Test;

import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Pair;
import dkeep.logic.guards.RookieGuard;
import dkeep.logic.guards.SuspiciousGuard;
import dkeep.logic.levels.*;
import dkeep.logic.levels.Level.LEVEL_STATE;

public class TestDungeonGameLogic {

	char map[][] =
		{
		{'X', 'X', 'X', 'X', 'X'},
        {'X', 'H', ' ', 'G', 'X'},
        {'I', ' ', ' ', ' ', 'X'},
        {'I', 'k', ' ', ' ', 'I'},
        {'X', 'X', 'X', 'X', 'X'}
        };
	
	char cleanMap[][] =
		{
		{'X', 'X', 'X', 'X', 'X'},
        {'X', 'H', ' ', 'G', 'X'},
        {'I', ' ', ' ', ' ', 'X'},
        {'I', 'k', ' ', ' ', 'I'},
        {'X', 'X', 'X', 'X', 'X'}
        };
	
	@Test
	public void testHeroMovementIntoFreeCell() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map,hero);
		assertEquals(new Pair(1,1), hero.getPosition());
		
		hero.move(MOVEMENT_TYPE.DOWN, map);
		assertEquals(new Pair(2,1), hero.getPosition());
		
	}
	
	@Test
	public void testHeroMovementIntoWall() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map,hero);
		assertEquals(new Pair(1,1), hero.getPosition());
		
		hero.move(MOVEMENT_TYPE.LEFT, map);
		assertEquals(new Pair(1,1), hero.getPosition());
		
	}
	
	@Test
	public void testHeroMovementIntoAdjacentGuard() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map,hero);
		assertEquals(new Pair(1,1), hero.getPosition());
		
		hero.move(MOVEMENT_TYPE.RIGHT, map);
		assertEquals(new Pair(1,2), hero.getPosition());
		
		assertTrue(testLevel.collision(testLevel.getGuard(),1));
		
	}
	
	@Test
	public void testHeroMovementIntoClosedDoor() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map,hero);
		assertEquals(new Pair(1,1), hero.getPosition());
		
		hero.move(MOVEMENT_TYPE.DOWN, map);
		 
		assertEquals(new Pair(2,1), hero.getPosition());
		
		hero.move(MOVEMENT_TYPE.LEFT, map);
		
		assertEquals(new Pair(2,1), hero.getPosition());
	}
	
	@Test
	public void testHeroMovementIntoLever() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map,hero);
		assertEquals(new Pair(1,1), hero.getPosition());
		 
		hero.move(MOVEMENT_TYPE.DOWN, map);
		
		assertEquals(new Pair(2,1), hero.getPosition());
		
		testLevel.updateLevel(MOVEMENT_TYPE.DOWN); //To update lever symbol
		 
		assertEquals(new Pair(3,1), hero.getPosition()); 
		
		
		assertTrue(map[2][0]== 'S' && map[3][0]== 'S' && map[3][4] == 'S');
	}
	
	@Test
	public void testHeroMovementIntoOpenPassageDoor() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map,hero);
		assertEquals(new Pair(1,1), hero.getPosition());
		
		hero.move(MOVEMENT_TYPE.DOWN, map);
		
		assertEquals(new Pair(2,1), hero.getPosition());
		
		testLevel.updateLevel(MOVEMENT_TYPE.DOWN); //To update lever symbol
		
		assertEquals(LEVEL_STATE.PASSED_LEVEL,testLevel.updateLevel(MOVEMENT_TYPE.LEFT)); 
		 
	}  
	

	
	@SuppressWarnings("deprecation")
	@Test
	public void testCreatedMap() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map,hero);
		
		
		assertEquals(cleanMap, testLevel.createMapToPrint());
	}
	 
 

}
