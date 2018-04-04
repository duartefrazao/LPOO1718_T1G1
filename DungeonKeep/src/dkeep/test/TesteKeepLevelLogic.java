package dkeep.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Vector;

import org.junit.Test;

import dkeep.logic.Hero; 
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level.LEVEL_STATE;

public class TesteKeepLevelLogic {
	
	 char testMap[][] =
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

	@Test
	public void testMoveIntoOgre() {
		Hero testHero = new Hero();
		KeepLevel testLevel = new KeepLevel(testMap, testHero);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.UP, testMap);

		testHero.move(MOVEMENT_TYPE.RIGHT, testMap);
		testHero.move(MOVEMENT_TYPE.RIGHT, testMap);
		/*testHero.move(MOVEMENT_TYPE.RIGHT, testMap);*/
		
		
		assertTrue(testLevel.checkOgreCollision());

	}

	@Test
	public void testChangeKeySymbol() {
		Hero testHero = new Hero();
		KeepLevel testLevel = new KeepLevel(testMap, testHero);

		for (int i = 0; i < 7; i++)
			testHero.move(MOVEMENT_TYPE.RIGHT, testMap);

		for (int i = 0; i < 5; i++)
			testHero.move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.UP);

		assertEquals('K', testHero.getSymbol());

	}

	@Test
	public void testFailToOpen() {
		Hero testHero = new Hero();
		KeepLevel testLevel = new KeepLevel(testMap, testHero);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.LEFT);

		assertEquals('I', testMap[1][0]);
	}

	@Test 
	public void testOpenDoor() {
		Hero testHero = new Hero();
		KeepLevel testLevel = new KeepLevel(testMap, testHero);

		for (int i = 0; i < 7; i++)
			testHero.move(MOVEMENT_TYPE.RIGHT, testMap);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.UP);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.DOWN, testMap);

		for (int i = 0; i < 7; i++)
			testHero.move(MOVEMENT_TYPE.LEFT, testMap);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.LEFT);

		assertEquals('S', testMap[1][0]);

	}

	@Test
	public void testWinLevel() {
		Hero testHero = new Hero();
		KeepLevel testLevel = new KeepLevel(testMap, testHero);

		for (int i = 0; i < 7; i++)
			testHero.move(MOVEMENT_TYPE.RIGHT, testMap);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.UP);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.DOWN, testMap);

		for (int i = 0; i < 7; i++)
			testHero.move(MOVEMENT_TYPE.LEFT, testMap);

		for (int i = 0; i < 6; i++)
			testHero.move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.LEFT);

		assertEquals(LEVEL_STATE.PASSED_LEVEL, testLevel.updateLevel(MOVEMENT_TYPE.LEFT));
	}

	@Test(timeout = 1000)
	public void testSomeRandomBehaviour() {

		boolean ogreHasGoneEveryDirection = false;
		boolean clubHasGoneEveryDirection = false;

		HashSet<MOVEMENT_TYPE> ogreMov = new HashSet<>();
		HashSet<MOVEMENT_TYPE> clubMov = new HashSet<>();

		Hero testHero = new Hero();
		KeepLevel testLevel = new KeepLevel(testMap, testHero);

		Vector<Ogre> testOgres = testLevel.getCrazyHorde();

		while (!ogreHasGoneEveryDirection || !clubHasGoneEveryDirection) {

			for (int i = 0; i < testOgres.size(); i++) {

				Ogre tempOgre = testOgres.elementAt(i);
				
				MOVEMENT_TYPE ogreM = tempOgre.getMove();

				ogreMov.add(ogreM);
 
				tempOgre.move(ogreM, testMap);
				
				MOVEMENT_TYPE clubM = tempOgre.getClub().getMove(testMap, tempOgre.getPosition());
				
				clubMov.add(clubM);
				
				tempOgre.getClub().move(clubM, testMap);
				
			}

			ogreHasGoneEveryDirection = ogreMov.size() == 4 ? true : false;
			clubHasGoneEveryDirection = clubMov.size() == 4 ? true : false;

		}

	}
	
	@Test
	public void testClassicMapCreation() {
		Hero testHero = new Hero();
		
		KeepLevel testLevel = new KeepLevel(testHero);
		
		assertEquals(new Pair(7,1), testLevel.getHero().getPosition());
		
		assertEquals(new Pair(1,8), testLevel.getKey());
		
	}

	@Test
	public void testMapToPrint() {
		
		Hero testHero = new Hero();
		
		KeepLevel testLevel = new KeepLevel(testHero);
		
		char[][] testMap = testLevel.createMapToPrint();
		
		assertEquals('I', testMap[1][0]);
		assertEquals('H', testMap[7][1]);
	}
}
