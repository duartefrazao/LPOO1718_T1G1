package dkeep.test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

public class TestDungeonLogic {

	@Test
	public void test() {
		Hero hero = new Hero();

		KeepLevel level2 = new KeepLevel(1);
		InitialLevel level1 = new InitialLevel();

		Vector<Level> levels = new Vector<>();
		levels.add(level1);
		levels.add(level2);

		Dungeon dungeon = new Dungeon(levels);
		
		dungeon.game(MOVEMENT_TYPE.DOWN);
		
		assertEquals(level1,dungeon.getCurrentLevel());
		
		level1.setLeverOff(true);
		
		hero.setX(5);
		hero.setY(1);
		
		System.out.println(hero.getX()+ "  " + hero.getY());
		System.out.println(dungeon.game(MOVEMENT_TYPE.LEFT));
		
	}

}
