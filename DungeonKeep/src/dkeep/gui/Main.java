package dkeep.gui;

import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

import javax.swing.*;
import java.util.Vector;

public class Main {

    public static void main(String args[]){

        JFrame frame = new JFrame("GUI");

        GUI gui = new GUI();

        frame.setContentPane(gui.getContent());

        frame.pack();

        frame.setVisible(true);


        Hero hero = new Hero();

        Level level2 = new KeepLevel( hero);
        Level level1 = new InitialLevel( hero);

        Vector<Level> levels = new Vector<Level>();
        levels.add(level1);
        levels.add(level2);

        Dungeon d = new Dungeon(levels);

        Dungeon.GAME_STATE state;

       gui.printMap(d.getMap());





    }
}
