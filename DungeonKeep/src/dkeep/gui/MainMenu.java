package dkeep.gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {

	private StateMachine stateMachine;
	private Dimension dimension = new Dimension(500, 500);
	private JButton btnNewGame;
	private JButton btnSettings;
	private JButton btnExit;
	private JButton btnCreateMaze;
	private Resources resources;
	private JButton btnLoadGame;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(resources.getInitialMenu(), 0, 0, this);
	}

	public MainMenu(StateMachine st, Resources resources) {

		this.resources = resources;

		stateMachine = st;

		this.initialize();

		this.initializeButtons();

		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
	}

	public void initializeButtons() {
		initNewGameButton();
		initSettingsButton();
		initCreateButton();
		initExitButton();
	}

	public void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 316, 0, 50, 0 };
		gridBagLayout.rowHeights = new int[] { 50, 0, 0, 0, 0, 0, 50, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
	}

	public void initNewGameButton() {
		btnNewGame = new JButton("New Game");
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewGame.gridx = 1;
		gbc_btnNewGame.gridy = 1;
		add(btnNewGame, gbc_btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.startGame);
			}

		});
	}

	public void initSettingsButton() {
		{
			btnLoadGame = new JButton("Load Game");
			GridBagConstraints gbc_btnLoadGame = new GridBagConstraints();
			gbc_btnLoadGame.insets = new Insets(0, 0, 5, 5);
			gbc_btnLoadGame.gridx = 1;
			gbc_btnLoadGame.gridy = 2;
			add(btnLoadGame, gbc_btnLoadGame);
		}

		btnSettings = new JButton("Settings");
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSettings.gridx = 1;
		gbc_btnSettings.gridy = 3;
		add(btnSettings, gbc_btnSettings);

		btnSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.newGame);
			}
		});
	}

	public void initExitButton() {

		btnExit = new JButton("Exit");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 1;
		gbc_btnExit.gridy = 5;
		add(btnExit, gbc_btnExit);

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.exitApp);
			}
		});
	}

	public void initCreateButton() {
		btnCreateMaze = new JButton("Create Maze");
		btnCreateMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.createMaze);
			}
		});
		GridBagConstraints gbc_btnCreateMaze = new GridBagConstraints();
		gbc_btnCreateMaze.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreateMaze.gridx = 1;
		gbc_btnCreateMaze.gridy = 4;
		add(btnCreateMaze, gbc_btnCreateMaze);

	}

}
