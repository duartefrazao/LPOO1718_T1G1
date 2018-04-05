package dkeep.test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import dkeep.logic.Dungeon;
import dkeep.logic.Dungeon.GAME_STATE;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Ogre;
import dkeep.logic.guards.*;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

public class TestDungeonLogic {

	@Test
	public void testLevelPassing() {

		KeepLevel level2 = new KeepLevel(1);
		InitialLevel level1 = new InitialLevel();

		Vector<Level> levels = new Vector<>();
		levels.add(level1);
		levels.add(level2);

		Dungeon dungeon = new Dungeon(levels);
		
		dungeon.game(MOVEMENT_TYPE.DOWN);
		
		assertEquals(level1,dungeon.getCurrentLevel());
		
		level1.setLeverOff(true);
		 
		dungeon.getCurrentLevel().getHero().setX(5);
		 
		assertEquals(5, dungeon.getCurrentLevel().getHero().getX());
	
		
	}
	

	@Test
	public void testSubstituteKeepLevel() {

		KeepLevel level2 = new KeepLevel(1);
		InitialLevel level1 = new InitialLevel();

		Vector<Level> levels = new Vector<>();
		levels.add(level1);
		levels.add(level2);

		Dungeon dungeon = new Dungeon(levels);
		
		char testNewMap[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
		
		KeepLevel newLevel = new KeepLevel(testNewMap);
		
		dungeon.substituteKeepLevel(newLevel);
		
		assertEquals('X', dungeon.getKeepLevel().getMap()[2][1]);
		assertEquals('X', dungeon.getKeepLevel().getMap()[2][2]);
		assertEquals('X', dungeon.getKeepLevel().getMap()[4][1]);
		assertEquals('X', dungeon.getKeepLevel().getMap()[4][2]);
	}
	
	@Test
	public void testVictory() {

		char keepLevelMapNoOgres[][] = { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'k', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		
		KeepLevel level2 = new KeepLevel(keepLevelMapNoOgres);
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
		
		//Get to key
		dungeon.getCurrentLevel().getHero().setX(1);
		dungeon.getCurrentLevel().getHero().setY(7);
		gs = dungeon.game(MOVEMENT_TYPE.RIGHT);
		assertEquals(GAME_STATE.PLAYING, gs);
		assertEquals(true, dungeon.getHero().hasKey());
		
		//Get to the doors
		dungeon.getCurrentLevel().getHero().setX(1);
		dungeon.getCurrentLevel().getHero().setY(1);
		gs = dungeon.game(MOVEMENT_TYPE.LEFT);
		assertEquals(GAME_STATE.PLAYING, gs);
		gs = dungeon.game(MOVEMENT_TYPE.LEFT);
		assertEquals(GAME_STATE.VICTORY, gs);
		
	}
	
	@Test
	public void testSusbstituteCurrentLevel() {
		KeepLevel level2 = new KeepLevel(1);
		InitialLevel level1 = new InitialLevel();

		Vector<Level> levels = new Vector<>();
		levels.add(level1);
		levels.add(level2);

		Dungeon dungeon = new Dungeon(levels);
		
		dungeon.game(MOVEMENT_TYPE.DOWN);
		
		assertEquals(level1,dungeon.getCurrentLevel());
		
		dungeon.getCurrentLevel().getHero().setX(5);
		dungeon.getCurrentLevel().getHero().setY(1);
		
		//Reset
		dungeon.resetCurrentLevel();
		assertEquals(level1,dungeon.getCurrentLevel());
		assertEquals(1,dungeon.getCurrentLevel().getHero().getX());
		
	}
	
	@Test
	public void testGameOverInitialLevel() {

		KeepLevel level2 = new KeepLevel(1);
		InitialLevel level1 = new InitialLevel();
		
		//set a rookie guard to test death
		level1.setGuard(new RookieGuard(level1.getGuard().getX(), level1.getGuard().getY()));
		
		Vector<Level> levels = new Vector<>();
		levels.add(level1);
		levels.add(level2);

		Dungeon dungeon = new Dungeon(levels);
		
		assertEquals(level1,dungeon.getCurrentLevel());
		
		//Next to Guard 
		dungeon.getCurrentLevel().getHero().setX(1);
		dungeon.getCurrentLevel().getHero().setY(7);
		
		//Play a null move (against wall)
		GAME_STATE gs = dungeon.game(MOVEMENT_TYPE.LEFT);
		assertEquals(GAME_STATE.GAME_OVER, gs);
	}
	
	@Test
	public void testGameOverKeepLevelWithoutSword() {
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
		
		//Get to Ogre
		dungeon.getCurrentLevel().getHero().setX(3);
		dungeon.getCurrentLevel().getHero().setY(6);
		
		//Test ogre killing hero
		int i = 100;
		while(i-- >0 && gs!=GAME_STATE.GAME_OVER)dungeon.game(MOVEMENT_TYPE.LEFT);
		assertNotEquals(0, i);	
	}
	
	@Test
	public void testGameOverKeepLevelWithSword() {
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
		  
		//Get sword and test if it has it
		MOVEMENT_TYPE toSword = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getMove(dungeon.getMap(), dungeon.getHero().getPosition());
		dungeon.game(dungeon.getHero().contrary(toSword));
		assertEquals(true, dungeon.getHero().isArmed());
		
		
		
		//Get to Ogre
		dungeon.getCurrentLevel().getHero().setX(3);
		dungeon.getCurrentLevel().getHero().setY(6);
		
		//Test ogre killing hero
		int i = 100;
		while(i-- >0 && gs!=GAME_STATE.GAME_OVER)dungeon.game(MOVEMENT_TYPE.LEFT);
		assertNotEquals(0, i);	
	}
	
	

}
