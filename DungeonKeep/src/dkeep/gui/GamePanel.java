package dkeep.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.SwingConstants;

import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject;
import dkeep.logic.guards.*;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JToggleButton;

public class GamePanel extends JPanel implements KeyListener {

	private Dungeon dungeon;
	private Hero hero;
	private Resources resources;
	private StateMachine stateMachine;
	private guardType guardPersonality = guardType.Drunken;
	private Integer numOgres = 3;

	private int mazeSize = 0;
	private gameGraphicPanel gameArea;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnDown;
	private JButton btnExit;

	public guardType getGuardPersonality() {
		return guardPersonality;
	}

	public void resizeWindow() {
		Dimension newOne = new Dimension(this.resources.getMapWidth() + 500, this.resources.getMapHeigth() + 200);
		this.setSize(newOne);
	}

	public void processGame(MovingObject.MOVEMENT_TYPE move) {

		Dungeon.GAME_STATE state = dungeon.game(move);

		if (state != Dungeon.GAME_STATE.PLAYING) {
			btnUp.setEnabled(false);
			btnLeft.setEnabled(false);
			btnRight.setEnabled(false);
			btnDown.setEnabled(false);
			stateMachine.update(StateMachine.Event.endGame);
		}

		resources.setMap(dungeon.getMap());

		this.gameArea.repaint();

		repaint();
	}

	/**
	 * Create the panel.
	 */
	public GamePanel(Resources resources, StateMachine st) {
		
		this.resources = resources;
		this.stateMachine = st;

		addKeyListener(this);
		
		this.initialize();
		this.initializeButtons();
		this.initializeActions();
	}

	public void initialize() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 266, 58, 0, 92, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 31, 0, 0, 0, 44, -17, -30, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		gameArea = new gameGraphicPanel(resources);
		gameArea.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_gameArea = new GridBagConstraints();
		gbc_gameArea.gridheight = 6;
		gbc_gameArea.insets = new Insets(0, 0, 5, 5);
		gbc_gameArea.fill = GridBagConstraints.BOTH;
		gbc_gameArea.gridx = 1;
		gbc_gameArea.gridy = 1;
		add(gameArea, gbc_gameArea);

	}

	public void initializeButtons() {
		btnUp = new JButton("Up");
		btnUp.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnUp.gridx = 4;
		gbc_btnUp.gridy = 2;
		add(btnUp, gbc_btnUp);

		btnLeft = new JButton("Left");
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.gridx = 3;
		gbc_btnLeft.gridy = 3;
		add(btnLeft, gbc_btnLeft);

		btnRight = new JButton("Right");
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnRight.gridx = 5;
		gbc_btnRight.gridy = 3;
		add(btnRight, gbc_btnRight);

		btnDown = new JButton("Down");
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnDown.gridx = 4;
		gbc_btnDown.gridy = 4;
		add(btnDown, gbc_btnDown);

		btnExit = new JButton("Exit");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 4;
		gbc_btnExit.gridy = 6;
		add(btnExit, gbc_btnExit);
	}

	public void initializeActions() {

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MovingObject.MOVEMENT_TYPE.UP);
			}
		});

		btnDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MovingObject.MOVEMENT_TYPE.DOWN);
			}
		});

		btnRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MovingObject.MOVEMENT_TYPE.RIGHT);
			}
		});

		btnLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processGame(MovingObject.MOVEMENT_TYPE.LEFT);
			}
		});
	}

	public void newGame() {

		hero = new Hero();

		KeepLevel level2 = new KeepLevel(hero, numOgres);
		InitialLevel level1 = new InitialLevel(hero);

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

		btnUp.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
		btnDown.setEnabled(true);

		resources.setMap(dungeon.getMap());

		gameArea.setDungeon(dungeon);

		gameArea.setMapSize();

		this.resizeWindow();
		
		resources.resizeGUIWindow();

		gameArea.setVisible(true);

		gameArea.repaint();

		requestFocusInWindow();

	}

	@Override
	public void keyPressed(KeyEvent e) {

		MovingObject.MOVEMENT_TYPE move;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			move = MovingObject.MOVEMENT_TYPE.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			move = MovingObject.MOVEMENT_TYPE.RIGHT;
			break;
		case KeyEvent.VK_UP:
			move = MovingObject.MOVEMENT_TYPE.UP;
			break;
		case KeyEvent.VK_DOWN:
			move = MovingObject.MOVEMENT_TYPE.DOWN;
			break;
		default:
			move = MovingObject.MOVEMENT_TYPE.RIGHT;
			return;
		}
		processGame(move);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

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

	public void setGuardPersonality(guardType object) {
		this.guardPersonality = object;
	}

	public int getNumOgres() {
		return numOgres;
	}

	public void setNumOgres(int nO) {
		this.numOgres = nO;
	}

	public int getMazeSize() {
		return mazeSize;
	}

	public void setMazeSize(int mazeSize) {
		this.mazeSize = mazeSize;
	}

	public enum guardType {
		Drunken, Rookie, Suspicious
	}

	public void setGameAreaVisible() {
		// gameArea.setVisible(true);
	}

}
