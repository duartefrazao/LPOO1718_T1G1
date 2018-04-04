package dkeep.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import dkeep.gui.GamePanel.guardType;

public class StateMachine {

	public OptionsPanel optionsPanel;
	public GamePanel gamePanel;
	public MainMenu mainMenu;
	public Resources resources;
	public State state;
	private JFrame frame;
	private MapCreator mapCreator;

	public enum State {
		mapCreator, game, options, mainMenu
	}

	public enum Event {
		newGame, exitApp, startGame, endGame, createMaze
	}

	// public

	StateMachine(Resources rs, JFrame frame) {
		resources = rs;
		this.frame = frame;
		state = State.options;
	}

	public void setPanels(OptionsPanel op, GamePanel gp, MainMenu mn, MapCreator mp) {
		gamePanel = gp;
		optionsPanel = op;
		mainMenu = mn;
		mapCreator = mp;
		gamePanel.newGame();
		mp.setDungeon(gamePanel.getDungeon());
	}

	public void update(Event event) {
		switch (event) {
		case startGame:
			gamePanel.resetGame();
			frame.setContentPane(gamePanel);
			gamePanel.setFocusable(true);
			gamePanel.requestFocusInWindow();
			frame.pack();
			frame.setLocationRelativeTo(null);
			gamePanel.setVisible(true);
			state = State.game;
			break;
		case exitApp:
			System.exit(0);
			break;
		case newGame:
			state = State.options;
			optionsPanel.setFocusable(true);
			optionsPanel.requestFocusInWindow();
			frame.setContentPane(optionsPanel);	
			optionsPanel.setVisible(true);
			frame.pack();
			frame.setLocationRelativeTo(null);
			gamePanel.newGame();
			break;
		case endGame:
			gamePanel.resetGame();
			state = State.mainMenu;
			frame.setContentPane(mainMenu);
			mainMenu.setFocusable(true);
			mainMenu.requestFocusInWindow();
			frame.pack();
			frame.setLocationRelativeTo(null);
			mainMenu.setVisible(true);
			break;
		case createMaze:
			//gamePanel.newGame();
			state = State.mapCreator;
			frame.setContentPane(mapCreator);
			mapCreator.setFocusable(true);
			mapCreator.requestFocusInWindow();
			frame.pack();
			frame.setLocationRelativeTo(null);
			mapCreator.setVisible(true);
			break;
			

		}
	}

	public void addOptions(Integer og, Object object) {
		gamePanel.setNumOgres(og);
		gamePanel.setGuardPersonality((guardType) object);
		gamePanel.newGame();
	}
}
