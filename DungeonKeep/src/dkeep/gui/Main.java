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


        //---- Setting up Game Elements

        Hero hero = new Hero();

        Level level2 = new KeepLevel( hero);
        Level level1 = new InitialLevel( hero);

        Vector<Level> levels = new Vector<Level>();
        levels.add(level1);
        levels.add(level2);

        Dungeon dungeon = new Dungeon(levels);


        //---- Setting up JFrame and GUI ----

        JFrame frame = new JFrame("GUI");

        GUI gui = new GUI(dungeon);

        frame.setContentPane(gui.getContent());

        frame.pack();

        frame.setVisible(true);



    }
}
