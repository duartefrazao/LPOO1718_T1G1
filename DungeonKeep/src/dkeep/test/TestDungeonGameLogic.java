package dkeep.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

import org.junit.Test;

import dkeep.cli.UI;
import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Ogre;
import dkeep.logic.Pair;
import dkeep.logic.Weapon;
import dkeep.logic.guards.DrunkenGuard;
import dkeep.logic.guards.Guard;
import dkeep.logic.guards.RookieGuard;
import dkeep.logic.guards.SuspiciousGuard;
import dkeep.logic.levels.*;
import dkeep.logic.levels.Level.LEVEL_STATE;

public class TestDungeonGameLogic {

	char map[][] = { { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'H', ' ', 'G', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
			{ 'I', 'k', ' ', ' ', 'I' }, { 'X', 'X', 'X', 'X', 'X' } };

	char cleanMap[][] = { { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'H', ' ', 'G', 'X' }, { 'I', ' ', ' ', ' ', 'X' },
			{ 'I', 'k', ' ', ' ', 'I' }, { 'X', 'X', 'X', 'X', 'X' } };

	@Test
	public void testHeroMovementIntoFreeCell() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);
		assertEquals(new Pair(1, 1), hero.getPosition());

		hero.move(MOVEMENT_TYPE.DOWN, map);
		assertEquals(new Pair(2, 1), hero.getPosition());

	}

	@Test
	public void testHeroMovementIntoWall() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);
		assertEquals(new Pair(1, 1), hero.getPosition());

		hero.move(MOVEMENT_TYPE.LEFT, map);
		assertEquals(new Pair(1, 1), hero.getPosition());

	}

	@Test
	public void testHeroMovementIntoAdjacentGuard() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);
		assertEquals(new Pair(1, 1), hero.getPosition());

		hero.move(MOVEMENT_TYPE.RIGHT, map);
		assertEquals(new Pair(1, 2), hero.getPosition());

		assertTrue(testLevel.collision(testLevel.getGuard(), 1));

	}

	@Test
	public void testHeroMovementIntoClosedDoor() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);
		assertEquals(new Pair(1, 1), hero.getPosition());

		hero.move(MOVEMENT_TYPE.DOWN, map);

		assertEquals(new Pair(2, 1), hero.getPosition());

		hero.move(MOVEMENT_TYPE.LEFT, map);

		assertEquals(new Pair(2, 1), hero.getPosition());
	}

	@Test
	public void testHeroMovementIntoLever() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);
		assertEquals(new Pair(1, 1), hero.getPosition());

		hero.move(MOVEMENT_TYPE.DOWN, map);

		assertEquals(new Pair(2, 1), hero.getPosition());

		testLevel.updateLevel(MOVEMENT_TYPE.DOWN); // To update lever symbol

		assertEquals(new Pair(3, 1), hero.getPosition());

		assertTrue(map[2][0] == 'S' && map[3][0] == 'S' && map[3][4] == 'S');
	}

	@Test
	public void testHeroMovementIntoOpenPassageDoor() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);
		assertEquals(new Pair(1, 1), hero.getPosition());

		hero.move(MOVEMENT_TYPE.DOWN, map);

		assertEquals(new Pair(2, 1), hero.getPosition());

		testLevel.updateLevel(MOVEMENT_TYPE.DOWN); // To update lever symbol

		assertEquals(LEVEL_STATE.PASSED_LEVEL, testLevel.updateLevel(MOVEMENT_TYPE.LEFT));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCreatedMap() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);

		assertEquals(cleanMap, testLevel.createMapToPrint());
	}

	@Test
	public void testMove() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(map, hero);

		Pair hPos = new Pair(hero.getX(), hero.getY());

		/*
		 * Scanner s = new Scanner(System.in); UI userInterface = new UI(s);
		 * 
		 * Vector<Level> levels = new Vector<Level>(); levels.add(testLevel);
		 * 
		 * userInterface.printMap(d.getMap());
		 * 
		 * Dungeon d = new Dungeon(levels); userInterface.printMap(d.getMap());
		 */

		hero.move(MOVEMENT_TYPE.DOWN, map);
		assertEquals(true, hero.getX() == hPos.getX() + 1);

		hero.move(MOVEMENT_TYPE.UP, map);
		assertEquals(true, hero.getX() == hPos.getX());

		hero.move(MOVEMENT_TYPE.RIGHT, map);
		assertEquals(true, hero.getY() == hPos.getY() + 1);

		hero.move(MOVEMENT_TYPE.LEFT, map);
		assertEquals(true, hero.getY() == hPos.getY());

		Ogre dO = new Ogre(1, 1);
		Pair dPos = new Pair(1, 1);

		dO.move(MOVEMENT_TYPE.DOWN, map);
		assertEquals(true, dO.getX() == dPos.getX() + 1);

		dO.move(MOVEMENT_TYPE.UP, map);
		assertEquals(true, dO.getX() == dPos.getX());

		dO.move(MOVEMENT_TYPE.RIGHT, map);
		assertEquals(true, dO.getY() == dPos.getY() + 1);

		dO.move(MOVEMENT_TYPE.LEFT, map);
		assertEquals(true, dO.getY() == dPos.getY());

		DrunkenGuard dG = new DrunkenGuard(1, 1);
		Pair gPos = new Pair(1, 1);

		dG.move(MOVEMENT_TYPE.DOWN, map);
		assertEquals(true, dG.getX() == gPos.getX() + 1);

		dG.move(MOVEMENT_TYPE.UP, map);
		assertEquals(true, dG.getX() == gPos.getX());

		dG.move(MOVEMENT_TYPE.RIGHT, map);
		assertEquals(true, dG.getY() == gPos.getY() + 1);

		dG.move(MOVEMENT_TYPE.LEFT, map);
		assertEquals(true, dG.getY() == gPos.getY());
 
	}

	@Test
	public void testSwordMove() {
		char mapSword[][] = { { 'X', 'X', 'X', 'X', 'X' }, { 'X', 'H', ' ', ' ', 'X' }, { 'I', 'X', ' ', 'X', 'X' },
				{ 'I', 'k', 'X', ' ', 'I' }, { 'X', 'X', 'X', 'X', 'X' } };

		HashSet<MOVEMENT_TYPE> movs1 = new HashSet<MOVEMENT_TYPE>();
		movs1.add(MOVEMENT_TYPE.RIGHT);

		HashSet<MOVEMENT_TYPE> movs2 = new HashSet<MOVEMENT_TYPE>();
		movs2.add(MOVEMENT_TYPE.UP);

		HashSet<MOVEMENT_TYPE> movs3 = new HashSet<MOVEMENT_TYPE>();

		HashSet<MOVEMENT_TYPE> movs4 = new HashSet<MOVEMENT_TYPE>();
		movs2.add(MOVEMENT_TYPE.LEFT);

		Hero hero = new Hero();
		KeepLevel swordTestLevel = new KeepLevel(mapSword, hero);

		Weapon wp = new Weapon();

		assertEquals(true, movs1.contains(wp.getMove(mapSword, hero.getPosition())));

		hero.move(MOVEMENT_TYPE.RIGHT, mapSword);
		hero.move(MOVEMENT_TYPE.DOWN, mapSword);
		assertEquals(true, movs2.contains(wp.getMove(mapSword, hero.getPosition())));

		hero.move(MOVEMENT_TYPE.RIGHT, mapSword);
		hero.move(MOVEMENT_TYPE.DOWN, mapSword);
		assertEquals(false, movs3.contains(wp.getMove(mapSword, hero.getPosition())));

		hero.move(MOVEMENT_TYPE.UP, mapSword);
		hero.move(MOVEMENT_TYPE.RIGHT, mapSword);
		assertEquals(false, movs4.contains(wp.getMove(mapSword, hero.getPosition())));

	}
	
	@Test
	public void testDrunkenGuard() {

		char testMapGuard[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		boolean guardHasGoneEveryDirection = false;

		HashSet<MOVEMENT_TYPE> guardMovs = new HashSet<>();

		Hero testHero = new Hero();
		InitialLevel testLevel = new InitialLevel(testMapGuard, testHero);

		DrunkenGuard newG = new DrunkenGuard(0,8);
		
		testLevel.setGuard(newG);
		
		int i = 0;
		
		while (guardMovs.size() <4 && ++i<200) {
 
			MOVEMENT_TYPE guardMove = newG.getMove();

			guardMovs.add(guardMove);

			newG.move(guardMove, testMapGuard);
		}
		
		assertEquals(true, i< 200);
	}
	
	
	@Test
	public void testHeroReset() {
		Hero hero = new Hero();
		InitialLevel testLevel = new InitialLevel(hero);
		
		assertEquals(new Pair(1,1), testLevel.getHero().getPosition());
		
		Hero newHero = new Hero();
		newHero.setX(1);
		newHero.setY(2);
		testLevel.setHero(newHero);
		
		
		assertEquals(new Pair(1,2), testLevel.getHero().getPosition());
		
		testLevel.resetElements();
		
		assertEquals(new Pair(1,1), testLevel.getHero().getPosition());
	}
	
	@Test
	public void testRookieGuard() {
		char testMapGuard[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		boolean guardHasGoneEveryDirection = false;

		HashSet<MOVEMENT_TYPE> guardMovs = new HashSet<>();

		Hero testHero = new Hero();
		InitialLevel testLevel = new InitialLevel(testMapGuard, testHero);

		RookieGuard newG = new RookieGuard(0,8);
		
		testLevel.setGuard(newG);
		
		int i = 0;
		
		while (guardMovs.size() <4 && ++i<200) {
 
			MOVEMENT_TYPE guardMove = newG.getMove();

			guardMovs.add(guardMove);

			newG.move(guardMove, testMapGuard);
		}
		
		assertEquals(true, i< 200);
		
	}
	
	@Test 
	public void testSuspicousGuard() {
		char testMapGuard[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		boolean guardHasGoneEveryDirection = false;

		HashSet<MOVEMENT_TYPE> guardMovs = new HashSet<>();

		Hero testHero = new Hero();
		InitialLevel testLevel = new InitialLevel(testMapGuard, testHero);

		SuspiciousGuard newG = new SuspiciousGuard(0,8);
		
		testLevel.setGuard(newG);
		
		int i = 0;
		
		while (guardMovs.size() <4 && ++i<200) {
 
			MOVEMENT_TYPE guardMove = newG.getMove();

			guardMovs.add(guardMove);

			newG.move(guardMove, testMapGuard);
		}
		
		assertEquals(true, i< 200);
		
	}
}
