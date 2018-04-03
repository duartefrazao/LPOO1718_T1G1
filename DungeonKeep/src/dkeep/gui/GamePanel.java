package dkeep.gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
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
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class GamePanel extends JPanel implements KeyListener {

	private Dungeon dungeon;
	private Hero hero;
	private Resources resources;
	private StateMachine stateMachine;
	private guardType guardPersonality = guardType.Drunken;
	private Integer numOgres = 3;

	private int mazeSize = 0;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnDown;
	private JButton btnExit;

	public guardType getGuardPersonality() {
		return guardPersonality;
	}

	public void resizeWindow() {
		Dimension newOne = new Dimension(this.resources.getMapWidth() + 300, this.resources.getMapHeigth());
		
		this.setPreferredSize(newOne);
		this.setMinimumSize(newOne);
		this.setMaximumSize(newOne);
		
		System.out.println(newOne);
		System.out.println(this.getSize());
	}

	public void processGame(MOVEMENT_TYPE move) {

		Dungeon.GAME_STATE state = dungeon.game(move);

		if (state != Dungeon.GAME_STATE.PLAYING) {
			btnUp.setEnabled(false);
			btnLeft.setEnabled(false);
			btnRight.setEnabled(false);
			btnDown.setEnabled(false);
			stateMachine.update(StateMachine.Event.endGame);
		}
		
		this.resizeWindow();
		this.resources.resizeGUIWindow();
		
		resources.setMap(dungeon.getMap());
		
		
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
	}

	public void initialize() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 266, 87, 89, 0, 25, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 31, 0, 0, 0, 44, 16, 10, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

	}

	public void upButInit() {
		btnUp = new JButton("Up");
		btnUp.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnUp.gridx = 2;
		gbc_btnUp.gridy = 2;
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
		gbc_btnDown.gridx = 2;
		gbc_btnDown.gridy = 4;
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
		gbc_btnLeft.gridx = 1;
		gbc_btnLeft.gridy = 3;
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
		gbc_btnRight.gridx = 3;
		gbc_btnRight.gridy = 3;
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
		gbc_btnExit.gridx = 2;
		gbc_btnExit.gridy = 6;
		add(btnExit, gbc_btnExit);

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}

	public void initializeButtons() {
		upButInit();
		rightButInit();
		downButInit();
		leftButInit();
		exitButInit();

	}

	public void newGameDungeonSettings() {
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
	}

	public void newGame() {

		newGameDungeonSettings();

		btnUp.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
		btnDown.setEnabled(true);

		resources.setMap(dungeon.getMap());
		this.resizeWindow();

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
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		char[][] map = this.resources.getMap();

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				switch (map[i][j]) {
				case ' ':
					g.drawImage(this.resources.getFloor(), j * 78, i * 78, this);
					break;
				case 'X':
					g.drawImage(this.resources.getWall(), j * 78, i * 78, this);
					break;
				case 'I':
					g.drawImage(this.resources.getDoor(), j * 78, i * 78, this);
					break;
				case 'S':
					g.drawImage(this.resources.getDoorOpened(), j * 78, i * 78, this);
					break;

				default:
					g.drawImage(this.resources.getFloor(), j * 78, i * 78, this);
					break;

				}

			}
		}

		this.paintObjects(g);

	}

	public void paintObjects(Graphics g) {

		if (dungeon.getCurrentLevel() instanceof InitialLevel) {

			int g_i = ((InitialLevel) (this.dungeon.getCurrentLevel())).getGuard().getX();
			int g_j = ((InitialLevel) (this.dungeon.getCurrentLevel())).getGuard().getY();
			paintGuards(g, g_i, g_j);

			boolean lever = ((InitialLevel) (this.dungeon.getCurrentLevel())).isLeverOff();
			int l_i = ((InitialLevel) (this.dungeon.getCurrentLevel())).getLever().getX();
			int l_j = ((InitialLevel) (this.dungeon.getCurrentLevel())).getLever().getY();

			paintLever(g, l_i, l_j, lever);
		} else {

			Vector<Ogre> ogres = ((KeepLevel) (this.dungeon.getCurrentLevel())).getCrazyHorde();
			paintOgres(g, ogres);

			int k_i = ((KeepLevel) (this.dungeon.getCurrentLevel())).getKey().getX();
			int k_j = ((KeepLevel) (this.dungeon.getCurrentLevel())).getKey().getY();
			paintKey(g, k_i, k_j);
		}

		paintHero(g);

	}

	public void paintOgres(Graphics g, Vector<Ogre> ogres) {

		for (int k = 0; k < ogres.size(); k++) {

			Ogre tempOgre = ogres.elementAt(k);

			int i = tempOgre.getX();
			int j = tempOgre.getY();

			if (!tempOgre.isStunned())
				g.drawImage(this.resources.getOgre(), j * 78, i * 78, this);
			else
				g.drawImage(this.resources.getOgreStunned(), j * 78, i * 78, this);

			i = tempOgre.getClub().getX();
			j = tempOgre.getClub().getY();

			g.drawImage(this.resources.getOgreWeapon(), j * 78, i * 78, this);

		}

	}

	public void paintKey(Graphics g, int i, int j) {
		g.drawImage(this.resources.getKey(), j * 78, i * 78, this);
	}

	public void paintGuards(Graphics g, int i, int j) {

		if (((InitialLevel) dungeon.getCurrentLevel()).getGuard() instanceof DrunkenGuard)
			if (((DrunkenGuard) ((InitialLevel) dungeon.getCurrentLevel()).getGuard()).isSleeping()) {
				g.drawImage(this.resources.getGuardSleeping(), j * 78, i * 78, this);
				return;
			}

		g.drawImage(this.resources.getGuard(), j * 78, i * 78, this);

	}

	public void paintLever(Graphics g, int i, int j, boolean mode) {
		if (mode)
			g.drawImage(this.resources.getLeverOn(), j * 78, i * 78, this);
		else
			g.drawImage(this.resources.getLeverOff(), j * 78, i * 78, this);

	}

	public void paintHero(Graphics g) {

		int i = dungeon.getCurrentLevel().getHero().getX();
		int j = dungeon.getCurrentLevel().getHero().getY();

		if (dungeon.getCurrentLevel().getHero().isArmed())
			g.drawImage(this.resources.getHeroArmed(), j * 78, i * 78, this);
		else {
			g.drawImage(this.resources.getHero(), j * 78, i * 78, this);

			if (dungeon.getCurrentLevel() instanceof KeepLevel) {
				int w_i = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getX();
				int w_j = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getY();

				g.drawImage(this.resources.getHeroWeapon(), w_j * 78, w_i * 78, this);
			}

		}

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
