package dkeep.gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.SwingConstants;

import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject.MOVEMENT_TYPE;
import dkeep.logic.Ogre;
import dkeep.logic.guards.*;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;

public class GamePanel extends JPanel implements KeyListener {

	private Dungeon dungeon;
	private Hero hero;
	private Resources resources;
	private StateMachine stateMachine;
	private guardType guardPersonality = guardType.Drunken;
	private Integer numOgres = 3;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnDown;
	private JButton btnExit;
	private GameGraphics gameArea;
	private JTextField textField;
	private GameLoader gameLoader;

	private Dimension fixedDimension = new Dimension(740 + 300, 740);

	public guardType getGuardPersonality() {
		return guardPersonality;
	}

	@Override
	public Dimension getPreferredSize() {
		return this.fixedDimension;
	}

	public void processGame(MOVEMENT_TYPE move) {

		Dungeon.GAME_STATE state = dungeon.game(move);

		if (state == Dungeon.GAME_STATE.VICTORY) {
			this.textField.setText("YOU WON! Press exit to play again!");
			btnUp.setEnabled(false);
			btnLeft.setEnabled(false);
			btnRight.setEnabled(false);
			btnDown.setEnabled(false);
			textField.requestFocusInWindow();
		} else if (state != Dungeon.GAME_STATE.PLAYING) {
			
			this.textField.setText("YOU LOST! Press exit to try again!");
			btnUp.setEnabled(false);
			btnLeft.setEnabled(false);
			btnRight.setEnabled(false);
			btnDown.setEnabled(false);
			textField.requestFocusInWindow();
			
		} else {

			this.updateText();

			resources.setMap(dungeon.getMap());

			this.gameArea.updateSize();

			requestFocusInWindow();

			this.gameArea.repaint();
		}
	}

	public void updateText() {

		if (dungeon.getCurrentLevel() instanceof InitialLevel) {

			if (((InitialLevel) dungeon.getCurrentLevel()).isLeverOff())
				textField.setText("The Lever is off! Set it on to escape!");
			else
				textField.setText("Congrats! You can now escape!");

		} else {
			if (dungeon.getCurrentLevel().getHero().hasKey())
				textField.setText("You have the key! Now escape!");
			else
				textField.setText("Get the key so you can escape!");
		}
	}

	/**
	 * Create the panel.
	 */
	public GamePanel(Resources resources, StateMachine st) {

		this.resources = resources;
		this.stateMachine = st;
		this.gameLoader = new GameLoader();

		addKeyListener(this);

		this.setPreferredSize(fixedDimension);
		this.setMinimumSize(fixedDimension);
		this.setMaximumSize(fixedDimension);

		this.initialize();
		this.initializeButtons();
		this.initializeGameArea();
	}

	public void initializeGameArea() {

		gameArea = new GameGraphics(resources);
		// gameArea = new JPanel();
		GridBagConstraints gbc_gameArea = new GridBagConstraints();
		gbc_gameArea.fill = GridBagConstraints.NONE;
		gbc_gameArea.gridheight = 8;
		gbc_gameArea.insets = new Insets(0, 0, 0, 5);
		gbc_gameArea.gridx = 1;
		gbc_gameArea.gridy = 1;
		gameArea.updateSize();
		add(gameArea, gbc_gameArea);
		GridBagLayout gbl_gameArea = new GridBagLayout();
		gbl_gameArea.columnWidths = new int[] { 0 };
		gbl_gameArea.rowHeights = new int[] { 0 };
		gbl_gameArea.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_gameArea.rowWeights = new double[] { Double.MIN_VALUE };
		gameArea.setLayout(gbl_gameArea);

	}

	public void initialize() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 266, 87, 89, 0, 25, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 31, 0, 0, 0, 44, 16, 0, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

	}

	public void initializeButtons() {
		upButInit();
		rightButInit();
		downButInit();
		leftButInit();
		exitButInit();
		initTextArea();

	}

	public void initTextArea() {
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(10);
	}

	public void upButInit() {
		btnUp = new JButton("Up");
		btnUp.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnUp.gridx = 3;
		gbc_btnUp.gridy = 3;
		add(btnUp, gbc_btnUp);

		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MOVEMENT_TYPE.UP);
			}
		});
	}

	public void downButInit() {
		btnDown = new JButton("Down");
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnDown.gridx = 3;
		gbc_btnDown.gridy = 5;
		add(btnDown, gbc_btnDown);

		btnDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MOVEMENT_TYPE.DOWN);
			}
		});
	}

	public void leftButInit() {
		btnLeft = new JButton("Left");
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.gridx = 2;
		gbc_btnLeft.gridy = 4;
		add(btnLeft, gbc_btnLeft);

		btnLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MOVEMENT_TYPE.LEFT);
			}
		});
	}

	public void rightButInit() {
		btnRight = new JButton("Right");
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnRight.gridx = 4;
		gbc_btnRight.gridy = 4;
		add(btnRight, gbc_btnRight);

		btnRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MOVEMENT_TYPE.RIGHT);
			}
		});
	}

	public void exitButInit() {
		btnExit = new JButton("Exit");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 3;
		gbc_btnExit.gridy = 7;
		add(btnExit, gbc_btnExit);

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.endGame);
				
				try {
					gameLoader.SaveGame(dungeon, "123");
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					System.exit(1);
				}
			}
		});

	}

	public void newGameDungeonSettings() {
		KeepLevel level2 = new KeepLevel(numOgres);
		InitialLevel level1 = new InitialLevel();

		Vector<Level> levels = new Vector<>();
		levels.add(level1);
		levels.add(level2);

		switch (guardPersonality) {
		case Drunken:
			level1.setGuard(new DrunkenGuard(level1.getGuard().getX(), level1.getGuard().getY()));
			break;
		case Suspicious:
			level1.setGuard(new SuspiciousGuard(level1.getGuard().getX(), level1.getGuard().getY()));
			break;
		case Rookie:
			level1.setGuard(new RookieGuard(level1.getGuard().getX(), level1.getGuard().getY()));
			break;
		}
		dungeon = new Dungeon(levels);

	}

	public void resetGame() {

		dungeon.resetCurrentLevel();

		InitialLevel level1 = (InitialLevel) dungeon.getCurrentLevel();

		switch (guardPersonality) {
		case Drunken:
			level1.setGuard(new DrunkenGuard(level1.getGuard().getX(), level1.getGuard().getY()));
			break;
		case Suspicious:
			level1.setGuard(new SuspiciousGuard(level1.getGuard().getX(), level1.getGuard().getY()));
			break;
		case Rookie:
			level1.setGuard(new RookieGuard(level1.getGuard().getX(), level1.getGuard().getY()));
			break;
		}

	}

	public void newGame() {

		newGameDungeonSettings();

		btnUp.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
		btnDown.setEnabled(true);

		resources.setMap(dungeon.getMap());

		this.gameArea.setDungeon(dungeon);

		this.gameArea.updateSize();

		this.initializeButtons();

		this.updateText();

		requestFocusInWindow();

	}

	@Override
	public void keyPressed(KeyEvent e) {

		MOVEMENT_TYPE move;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			move = MOVEMENT_TYPE.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			move = MOVEMENT_TYPE.RIGHT;
			break;
		case KeyEvent.VK_UP:
			move = MOVEMENT_TYPE.UP;
			break;
		case KeyEvent.VK_DOWN:
			move = MOVEMENT_TYPE.DOWN;
			break;
		default:
			move = MOVEMENT_TYPE.RIGHT;
			return;
		}
		processGame(move);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * @return the btnUp
	 */
	public JButton getBtnUp() {
		return btnUp;
	}

	/**
	 * @param btnUp
	 *            the btnUp to set
	 */
	public void setBtnUp(JButton btnUp) {
		this.btnUp = btnUp;
	}

	/**
	 * @return the btnDown
	 */
	public JButton getBtnDown() {
		return btnDown;
	}

	/**
	 * @param btnDown
	 *            the btnDown to set
	 */
	public void setBtnDown(JButton btnDown) {
		this.btnDown = btnDown;
	}

	/**
	 * @return the btnLeft
	 */
	public JButton getBtnLeft() {
		return btnLeft;
	}

	/**
	 * @param btnLeft
	 *            the btnLeft to set
	 */
	public void setBtnLeft(JButton btnLeft) {
		this.btnLeft = btnLeft;
	}

	/**
	 * @return the btnRight
	 */
	public JButton getBtnRight() {
		return btnRight;
	}

	/**
	 * @param btnRight
	 *            the btnRight to set
	 */
	public void setBtnRight(JButton btnRight) {
		this.btnRight = btnRight;
	}

	/**
	 * @return the btnExit
	 */
	public JButton getBtnExit() {
		return btnExit;
	}

	/**
	 * @param btnExit
	 *            the btnExit to set
	 */
	public void setBtnExit(JButton btnExit) {
		this.btnExit = btnExit;
	}

	/**
	 * @return the dungeon
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}

	/**
	 * @param dungeon
	 *            the dungeon to set
	 */
	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}

	/**
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * @param hero
	 *            the hero to set
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * @return the resources
	 */
	public Resources getResources() {
		return resources;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(Resources resources) {
		this.resources = resources;
	}

	/**
	 * @return the stateMachine
	 */
	public StateMachine getStateMachine() {
		return stateMachine;
	}

	/**
	 * @param stateMachine
	 *            the stateMachine to set
	 */
	public void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	/**
	 * @return the numOgres
	 */
	public Integer getNumOgres() {
		return numOgres;
	}

	/**
	 * @param numOgres
	 *            the numOgres to set
	 */
	public void setNumOgres(Integer numOgres) {
		this.numOgres = numOgres;
	}

	/**
	 * @param guardPersonality
	 *            the guardPersonality to set
	 */
	public void setGuardPersonality(guardType guardPersonality) {
		this.guardPersonality = guardPersonality;
	}

	public enum guardType {
		Drunken, Rookie, Suspicious
	}

}