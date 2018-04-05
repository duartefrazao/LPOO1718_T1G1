package dkeep.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Vector;

import org.junit.Test;

import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;
import dkeep.logic.Dungeon.GAME_STATE;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;
import dkeep.logic.levels.Level.LEVEL_STATE;

public class TesteKeepLevelLogic {

	char testMap[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'I', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'k', 'X' }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, };

	@Test
	public void testMoveIntoOgre() {
		KeepLevel testLevel = new KeepLevel(testMap);

		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.UP, testMap);

		testLevel.getHero().move(MOVEMENT_TYPE.RIGHT, testMap);
		testLevel.getHero().move(MOVEMENT_TYPE.RIGHT, testMap);
		/* testHero.move(MOVEMENT_TYPE.RIGHT, testMap); */

		assertTrue(testLevel.checkOgreCollision());

	}

	@Test
	public void testChangeKeySymbol() {

		KeepLevel testLevel = new KeepLevel(testMap);

		for (int i = 0; i < 7; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.RIGHT, testMap);

		for (int i = 0; i < 5; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.UP);

		assertEquals('K', testLevel.getHero().getSymbol());

	}

	@Test
	public void testFailToOpen() {
		KeepLevel testLevel = new KeepLevel(testMap);

		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.LEFT);

		assertEquals('I', testMap[1][0]);
	}

	@Test
	public void testOpenDoor() {
		KeepLevel testLevel = new KeepLevel(testMap);

		for (int i = 0; i < 7; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.RIGHT, testMap);

		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.UP);

		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.DOWN, testMap);

		for (int i = 0; i < 7; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.LEFT, testMap);

		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.LEFT);

		assertEquals('S', testMap[1][0]);

	}

	@Test
	public void testWinLevel() {
		KeepLevel testLevel = new KeepLevel(testMap);

		for (int i = 0; i < 7; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.RIGHT, testMap);

		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.UP);

		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.DOWN, testMap);

		for (int i = 0; i < 7; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.LEFT, testMap);
		for (int i = 0; i < 6; i++)
			testLevel.getHero().move(MOVEMENT_TYPE.UP, testMap);

		testLevel.updateLevel(MOVEMENT_TYPE.LEFT);

		assertEquals(LEVEL_STATE.PASSED_LEVEL, testLevel.updateLevel(MOVEMENT_TYPE.LEFT));
	}

	@Test(timeout = 1000)
	public void testSomeRandomBehaviour() {

		boolean ogreHasGoneEveryDirection = false;
		boolean clubHasGoneEveryDirection = false;

		HashSet<MOVEMENT_TYPE> ogreMov = new HashSet<>();
		HashSet<MOVEMENT_TYPE> clubMov = new HashSet<>();

		KeepLevel testLevel = new KeepLevel(testMap);

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

		KeepLevel testLevel = new KeepLevel();

		assertEquals(new Pair(7, 1), testLevel.getHero().getPosition());

		assertEquals(new Pair(1, 8), testLevel.getKey());

	}

	@Test
	public void testMapToPrint() {

		KeepLevel testLevel = new KeepLevel();

		char[][] testMap = testLevel.createMapToPrint();

		assertEquals('I', testMap[1][0]);
		assertEquals('H', testMap[7][1]);
	}
	
	@Test
	public void testValidWeaponSpawn() {
		KeepLevel level2 = new KeepLevel(1);
		InitialLevel level1 = new InitialLevel();

		Vector<Level> levels = new Vector<>();
		levels.add(level1);
		levels.add(level2);

		Dungeon dungeon = new Dungeon(levels);
		
		dungeon.game(MOVEMENT_TYPE.DOWN);
		
		assertEquals(level1,dungeon.getCurrentLevel());
		 
		//Next to lever 
		dungeon.getCurrentLevel().getHero().setX(8);
		dungeon.getCurrentLevel().getHero().setY(8);
		
		assertEquals(8, dungeon.getCurrentLevel().getHero().getX());
		
		//Touch lever
		dungeon.game(MOVEMENT_TYPE.LEFT);
		assertEquals(false, ((InitialLevel) (dungeon.getCurrentLevel())).isLeverOff());
		
		//Take hero next to door
		dungeon.getCurrentLevel().getHero().setX(5);
		dungeon.getCurrentLevel().getHero().setY(1);
		
		//Pass to keep level
		GAME_STATE gs = dungeon.game(MOVEMENT_TYPE.LEFT);
		assertEquals(GAME_STATE.PLAYING, gs);
		assertEquals(true, (dungeon.getCurrentLevel() instanceof KeepLevel));
		
		//Test spawn
		assertEquals(true, dungeon.getKeepLevel().getHeroWeapon().getPosition().getX() == dungeon.getHero().getX() ||dungeon.getKeepLevel().getHeroWeapon().getPosition().getX() == dungeon.getHero().getX());
		MOVEMENT_TYPE toSword = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getMove(dungeon.getMap(), dungeon.getHero().getPosition());
		dungeon.game(dungeon.getHero().contrary(toSword));
		
		//Test if hero has sword and has a valid sword object
		assertEquals(true, dungeon.getHero().isArmed());
		assertNotEquals(null, dungeon.getHero().getWeapon());
	}
}
