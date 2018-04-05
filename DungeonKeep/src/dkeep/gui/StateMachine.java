package dkeep.gui;

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
	private GameLoader gameLoader;

	public enum State {
		mapCreator, game, options, mainMenu
	}
 
	public enum Event {
		newGame, exitApp, startGame, endGame, createMaze, loadGame, back
	}


	StateMachine(Resources rs, JFrame frame) {
		resources = rs;
		this.frame = frame;
		state = State.options;
	}

	public void setPanels(OptionsPanel op, GamePanel gp, MainMenu mn, MapCreator mp, GameLoader gl) {
		gamePanel = gp;
		optionsPanel = op;
		mainMenu = mn;
		mapCreator = mp;
		gameLoader = gl;
		gamePanel.newGame();
		mp.setDungeon(gamePanel.getDungeon());
		gameLoader.setDungeon(gamePanel.getDungeon());
	}

	public void update(Event event) {
		switch (event) {
		case startGame:
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
			break;
		case endGame:
			gamePanel.resetGame();
			state = State.mainMenu;
			mainMenu.repaint();
			frame.setContentPane(mainMenu);
			mainMenu.setFocusable(true);
			mainMenu.requestFocusInWindow();
			frame.pack();
			frame.setLocationRelativeTo(null);
			mainMenu.setVisible(true);
			break;
		case createMaze:
			state = State.mapCreator;
			frame.setContentPane(mapCreator);
			mapCreator.setFocusable(true);
			mapCreator.requestFocusInWindow();
			frame.pack();
			frame.setLocationRelativeTo(null);
			mapCreator.setVisible(true);
			break;
		case loadGame:
			gameLoader.addFilesOptions();
			frame.setContentPane(gameLoader);
			gameLoader.setFocusable(true);
			gameLoader.requestFocusInWindow();
			frame.pack();
			frame.setLocationRelativeTo(null);
			gameLoader.setVisible(true);
			break;
		case back:
			state = State.mainMenu;
			mainMenu.repaint();
			frame.setContentPane(mainMenu);
			mainMenu.setFocusable(true);
			mainMenu.requestFocusInWindow();
			frame.pack();
			frame.setLocationRelativeTo(null);
			mainMenu.setVisible(true);
			break;

		}
	}

	public void addOptions(Integer og, Object object) {
		gamePanel.setNumOgres(og);
		gamePanel.setGuardPersonality((guardType) object);
		gamePanel.newGame();
	}
}
