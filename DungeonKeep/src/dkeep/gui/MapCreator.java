package dkeep.gui;

import javax.swing.*;

import dkeep.logic.Dungeon;
import dkeep.logic.levels.KeepLevel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MapCreator extends JPanel {

	private JTextField txtMazeSize;
	private JButton btnHero;
	private JButton btnCreateMaze;

	private JButton btnWalls;
	private JButton btnExitDoor;
	private JButton btnKey;
	private JButton btnOgres;
	private Resources resources;
	private JButton btnOk;
 
	private JDialog warning;

	private Integer mazeSize;

	private Dungeon dungeon;

	private char map[][];

	private Dimension dimension = new Dimension(1200, 800);
	private JButton btnClearCell;

	private CreatorGraphics gameArea;

	private StateMachine stateMachine;
	private JButton btnBack;

	public MapCreator(StateMachine st, Resources resources) {

		super();

		this.resources = resources;

		this.stateMachine = st;

		this.setVisible(false);

		this.setPreferredSize(dimension);

		this.initialize();
		this.initComponents();
		this.initBtnsIcon();

	}

	public void initializeMap() {

		map = new char[this.mazeSize.intValue()][this.mazeSize.intValue()];

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				if (i == 0 || i == map.length - 1 || j == 0 || j == map[i].length - 1)
					map[i][j] = 'X';
				else
					map[i][j] = ' ';
			}
		}

		this.gameArea.setMap(map);

		this.gameArea.repaint();

	}

	public void initBtnsIcon() {

		try {
			btnHero.setIcon(new ImageIcon(resources.getHero()));
			btnWalls.setIcon(new ImageIcon(resources.getWall()));
			btnExitDoor.setIcon(new ImageIcon(resources.getDoor()));
			btnOgres.setIcon(new ImageIcon(resources.getOgre()));
			btnKey.setIcon(new ImageIcon(resources.getKey()));
			btnClearCell.setIcon(new ImageIcon(resources.getFloor()));
		} catch (Exception ex) {
			System.out.println(ex);
			System.exit(1);
		}

	}

	public void enableButtons() {
		this.btnExitDoor.setEnabled(true);
		this.btnHero.setEnabled(true);
		this.btnKey.setEnabled(true);
		this.btnOgres.setEnabled(true);
		this.btnWalls.setEnabled(true);
		this.btnClearCell.setEnabled(true);
		this.btnCreateMaze.setEnabled(true);
	}

	public void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 167, 20, 148, 65, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 20, 46, 0, 0, 0, 0, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		gameArea = new CreatorGraphics(resources);
		gameArea.setBackground(SystemColor.info);
		GridBagConstraints gbc_gameArea = new GridBagConstraints();
		gbc_gameArea.anchor = GridBagConstraints.NORTHWEST;
		gbc_gameArea.gridheight = 5;
		gbc_gameArea.insets = new Insets(0, 0, 5, 5);
		gbc_gameArea.gridx = 1;
		gbc_gameArea.gridy = 2;
		add(gameArea, gbc_gameArea);

	}

	public void initComponents() {
		this.initBtnCreate();
		this.initBtnDoor();
		this.initBtnHero();
		this.initBtnKey();
		this.initBtnOgres();
		this.initBtnWalls();
		this.initBtnOK();
		this.initTxtMazeSize();
		this.initBtnClear();
		this.initBackDoor();
	}

	public void initBtnHero() {
		btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameArea.setCurrentDrawOption('H');
			}
		});
		btnHero.setEnabled(false);

		GridBagConstraints gbc_btnHero = new GridBagConstraints();
		gbc_btnHero.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHero.insets = new Insets(0, 0, 5, 5);
		gbc_btnHero.gridx = 3;
		gbc_btnHero.gridy = 6;
		add(btnHero, gbc_btnHero);

	}

	public void initBtnCreate() {
		btnCreateMaze = new JButton("Create Maze");
		btnCreateMaze.setEnabled(false);

		GridBagConstraints gbc_btnCreateMaze = new GridBagConstraints();
		gbc_btnCreateMaze.fill = GridBagConstraints.VERTICAL;
		gbc_btnCreateMaze.gridwidth = 6;
		gbc_btnCreateMaze.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreateMaze.gridx = 0;
		gbc_btnCreateMaze.gridy = 0;
		add(btnCreateMaze, gbc_btnCreateMaze);

		this.btnCreateMazeAction();
	}

	public void btnCreateMazeAction() {
		btnCreateMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean isPossible = gameArea.isMazePossible();

				if (!isPossible) {
					warning = new JDialog(null, "Impossible Maze", ModalityType.APPLICATION_MODAL);
					warning.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					JLabel label = new JLabel("Current Maze is impossible to solve.");
					warning.getContentPane().add(label);
					warning.setLocationRelativeTo(null);
					label.setPreferredSize(new Dimension(300, 100));
					warning.setPreferredSize(new Dimension(300, 200));
					warning.pack();
					warning.setVisible(true);
				} else {
					dungeon.substituteKeepLevel(new KeepLevel(map));
					stateMachine.update(StateMachine.Event.startGame);
				}
			}
		});
	}

	public void initBtnClear() {

		btnClearCell = new JButton("Clear Cell");
		btnClearCell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameArea.setCurrentDrawOption(' ');
			}
		});
		btnClearCell.setEnabled(false);
		GridBagConstraints gbc_btnClearCell = new GridBagConstraints();
		gbc_btnClearCell.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClearCell.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearCell.gridx = 4;
		gbc_btnClearCell.gridy = 6;
		add(btnClearCell, gbc_btnClearCell);

	}

	public void initTxtMazeSize() {

		txtMazeSize = new JTextField();
		txtMazeSize.setToolTipText("Maze Size");
		txtMazeSize.setBackground(SystemColor.window);
		txtMazeSize.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtMazeSize = new GridBagConstraints();
		gbc_txtMazeSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMazeSize.insets = new Insets(0, 0, 5, 5);
		gbc_txtMazeSize.gridx = 3;
		gbc_txtMazeSize.gridy = 2;
		add(txtMazeSize, gbc_txtMazeSize);
		txtMazeSize.setColumns(10);

	}

	public void initBtnOK() {

		btnOk = new JButton("OK!");

		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOk.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk.gridx = 4;
		gbc_btnOk.gridy = 2;
		add(btnOk, gbc_btnOk);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					mazeSize = Integer.valueOf(txtMazeSize.getText());

					if (mazeSize.intValue() >= 6 && mazeSize.intValue() <= 30) {
						enableButtons();
						initializeMap();
						gameArea.setMazeSize(mazeSize);
						gameArea.setSize(new Dimension(720, 720));
					} else {
						txtMazeSize.setText("Number between 6 and 30");
					}
				} catch (Exception ex) {
					txtMazeSize.setText("Invalid Input");
				}
			}
		});

	}

	public void initBtnWalls() {

		btnWalls = new JButton("Walls");
		btnWalls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameArea.setCurrentDrawOption('X');
			}
		});
		btnWalls.setEnabled(false);
		GridBagConstraints gbc_btnWalls = new GridBagConstraints();
		gbc_btnWalls.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnWalls.insets = new Insets(0, 0, 5, 5);
		gbc_btnWalls.gridx = 3;
		gbc_btnWalls.gridy = 4;
		add(btnWalls, gbc_btnWalls);
	}

	public void initBackDoor() {

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.back);
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBack.gridwidth = 2;
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 3;
		gbc_btnBack.gridy = 3;
		add(btnBack, gbc_btnBack);

	}

	public void initBtnDoor() {

		btnExitDoor = new JButton("Exit Door");
		btnExitDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameArea.setCurrentDrawOption('I');
			}
		});

		btnExitDoor.setEnabled(false);
		GridBagConstraints gbc_btnExitDoor = new GridBagConstraints();
		gbc_btnExitDoor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExitDoor.insets = new Insets(0, 0, 5, 5);
		gbc_btnExitDoor.gridx = 4;
		gbc_btnExitDoor.gridy = 4;
		add(btnExitDoor, gbc_btnExitDoor);
	}

	public void initBtnKey() {

		btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameArea.setCurrentDrawOption('k');
			}
		});
		btnKey.setEnabled(false);
		GridBagConstraints gbc_btnKey = new GridBagConstraints();
		gbc_btnKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKey.insets = new Insets(0, 0, 5, 5);
		gbc_btnKey.gridx = 3;
		gbc_btnKey.gridy = 5;
		add(btnKey, gbc_btnKey);
	}

	public void initBtnOgres() {

		btnOgres = new JButton("Ogres");
		btnOgres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameArea.setCurrentDrawOption('O');
			}
		});
		btnOgres.setEnabled(false);
		GridBagConstraints gbc_btnOgres = new GridBagConstraints();
		gbc_btnOgres.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOgres.insets = new Insets(0, 0, 5, 5);
		gbc_btnOgres.gridx = 4;
		gbc_btnOgres.gridy = 5;
		add(btnOgres, gbc_btnOgres);
	}

	/**
	 * @return the txtMazeSize
	 */
	public JTextField getTxtMazeSize() {
		return txtMazeSize;
	}

	/**
	 * @param txtMazeSize
	 *            the txtMazeSize to set
	 */
	public void setTxtMazeSize(JTextField txtMazeSize) {
		this.txtMazeSize = txtMazeSize;
	}

	/**
	 * @return the btnHero
	 */
	public JButton getBtnHero() {
		return btnHero;
	}

	/**
	 * @param btnHero
	 *            the btnHero to set
	 */
	public void setBtnHero(JButton btnHero) {
		this.btnHero = btnHero;
	}

	/**
	 * @return the btnCreateMaze
	 */
	public JButton getBtnCreateMaze() {
		return btnCreateMaze;
	}

	/**
	 * @param btnCreateMaze
	 *            the btnCreateMaze to set
	 */
	public void setBtnCreateMaze(JButton btnCreateMaze) {
		this.btnCreateMaze = btnCreateMaze;
	}

	/**
	 * @return the btnWalls
	 */
	public JButton getBtnWalls() {
		return btnWalls;
	}

	/**
	 * @param btnWalls
	 *            the btnWalls to set
	 */
	public void setBtnWalls(JButton btnWalls) {
		this.btnWalls = btnWalls;
	}

	/**
	 * @return the btnExitDoor
	 */
	public JButton getBtnExitDoor() {
		return btnExitDoor;
	}

	/**
	 * @param btnExitDoor
	 *            the btnExitDoor to set
	 */
	public void setBtnExitDoor(JButton btnExitDoor) {
		this.btnExitDoor = btnExitDoor;
	}

	/**
	 * @return the btnKey
	 */
	public JButton getBtnKey() {
		return btnKey;
	}

	/**
	 * @param btnKey
	 *            the btnKey to set
	 */
	public void setBtnKey(JButton btnKey) {
		this.btnKey = btnKey;
	}

	/**
	 * @return the btnOgres
	 */
	public JButton getBtnOgres() {
		return btnOgres;
	}

	/**
	 * @param btnOgres
	 *            the btnOgres to set
	 */
	public void setBtnOgres(JButton btnOgres) {
		this.btnOgres = btnOgres;
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
	 * @return the btnOk
	 */
	public JButton getBtnOk() {
		return btnOk;
	}

	/**
	 * @param btnOk
	 *            the btnOk to set
	 */
	public void setBtnOk(JButton btnOk) {
		this.btnOk = btnOk;
	}

	/**
	 * @return the mazeSize
	 */
	public Integer getMazeSize() {
		return mazeSize;
	}

	/**
	 * @param mazeSize
	 *            the mazeSize to set
	 */
	public void setMazeSize(Integer mazeSize) {
		this.mazeSize = mazeSize;
	}

	/**
	 * @return the map
	 */
	public char[][] getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(char[][] map) {
		this.map = map;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * @param dimension
	 *            the dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	/**
	 * @return the btnClearCell
	 */
	public JButton getBtnClearCell() {
		return btnClearCell;
	}

	/**
	 * @param btnClearCell
	 *            the btnClearCell to set
	 */
	public void setBtnClearCell(JButton btnClearCell) {
		this.btnClearCell = btnClearCell;
	}

	/**
	 * @return the gameArea
	 */
	public CreatorGraphics getGameArea() {
		return gameArea;
	}

	/**
	 * @param gameArea
	 *            the gameArea to set
	 */
	public void setGameArea(CreatorGraphics gameArea) {
		this.gameArea = gameArea;
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

}
