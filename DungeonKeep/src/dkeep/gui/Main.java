package dkeep.gui;

import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

import javax.swing.*;
import dkeep.gui.StatusPanel;

import java.awt.*;
import java.util.Vector;

public class Main {

    public static void main(String args[]){

        //---- Setting up JFrame and GUI ----

        JFrame frame = new JFrame("GUI");

        GamePanel gamePanel = new GamePanel();

        frame.setContentPane(gamePanel);

        StatusPanel statusP = new StatusPanel();

        frame.getContentPane().add(statusP);

        frame.pack();

        frame.setVisible(true);



    }
}
