package dkeep.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import dkeep.gui.GamePanel.guardType;

public class StateMachine {
	
	public  OptionsPanel optionsPanel;
	public GamePanel gamePanel;
	public MainMenu mainMenu;
	public Resources resources;
	public State state;
	private JFrame frame;
	
	public enum State{
		mapCreator, game, options, mainMenu
	}
	
	public enum Event{
		newGame, exitApp, startGame, endGame
	}
	
	//public 
	
	StateMachine(Resources rs, JFrame frame){
		resources = rs;
		this.frame = frame;
		state = State.options;
	}
	
	public void setPanels(OptionsPanel op, GamePanel gp, MainMenu mn) {
		gamePanel= gp;
		optionsPanel=op;
		mainMenu=mn;
	}
	
	public void update(Event event)
	{
		switch(event) {
			case startGame:
				gamePanel.newGame();
				frame.setContentPane(gamePanel);
				gamePanel.setVisible(true);
				gamePanel.setFocusable(true);
				gamePanel.requestFocusInWindow();
				frame.pack();
				state = State.game;
				break;
			case exitApp:
				System.exit(0);
				break;
			case newGame:
				state = State.options;
				optionsPanel.setSize(new Dimension(500,500));
				optionsPanel.setVisible(true);
				optionsPanel.setFocusable(true);
				frame.setContentPane(optionsPanel);
				optionsPanel.requestFocusInWindow();
				frame.pack();
				break;
			case endGame:
				state = State.mainMenu;
				mainMenu.setVisible(true);
				mainMenu.setFocusable(true);
				frame.setContentPane(mainMenu);
				mainMenu.requestFocusInWindow();
				frame.pack();
				break;
		}
	}

	public void addOptions(Integer og, Object object, Integer mazeSize) {
		gamePanel.setNumOgres(og);
		gamePanel.setGuardPersonality((guardType) object);
		gamePanel.setMazeSize(mazeSize);
		gamePanel.newGame();
	}
}
