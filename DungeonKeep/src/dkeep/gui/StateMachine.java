package dkeep.gui;

import dkeep.gui.GamePanel.guardType;

public class StateMachine {
	
	public  OptionsPanel optionsPanel;
	public GamePanel gamePanel;
	public MainMenu mainMenu;
	public Resources resources;
	public State state;
	
	
	public enum State{
		mapCreator, game, options, mainMenu
	}
	
	public enum Event{
		newGame, exitApp, startGame, endGame
	}
	
	//public 
	
	StateMachine(Resources rs){
		resources = rs;
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
				state = State.game;
				optionsPanel.setVisible(false);
				mainMenu.setVisible(false);
				gamePanel.setVisible(true);
				gamePanel.setFocusable(true);
				gamePanel.requestFocusInWindow();
				break;
			case exitApp:
				System.exit(0);
				break;
			case newGame:
				state = State.options;
				optionsPanel.setVisible(true);
				optionsPanel.setFocusable(true);
				gamePanel.setVisible(false);
				mainMenu.setVisible(false);
				optionsPanel.requestFocusInWindow();
				break;
			case endGame:
				state = State.mainMenu;
				mainMenu.setVisible(true);
				mainMenu.setFocusable(true);
				gamePanel.setVisible(false);
				optionsPanel.setVisible(false);
				mainMenu.requestFocusInWindow();
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
