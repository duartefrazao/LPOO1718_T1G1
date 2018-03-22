package dkeep.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.SwingConstants;

import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {

	private JButton btnNewGame;
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnExit;
	private Dungeon dungeon;
	private Hero hero;
	private JTextArea gameArea;

	public void printMap(char[][] map) {

		this.gameArea.setText("");

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				this.gameArea.append(map[i][j] + " ");
			}
			this.gameArea.append("\n");
		}

	}

	public void processGame(MovingObject.MOVEMENT_TYPE move) {

		Dungeon.GAME_STATE state = dungeon.game(move);

		printMap(dungeon.getMap());

		if (state != Dungeon.GAME_STATE.PLAYING) {
			btnUp.setEnabled(false);
			btnLeft.setEnabled(false);
			btnRight.setEnabled(false);
			btnDown.setEnabled(false);

			// gameStatus.setText("Game Over");

		}
	}

	/**
	 * Create the panel.
	 */
	public GamePanel() {

		this.initialize();

	}

	public void initialize() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 492, 17, 0, 73, 0, 0, 44, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 162, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		this.initializeButtons();

	}

	public void initializeButtons() {

		gameArea = new JTextArea();
		Font panel1Font = this.getFont("Monospaced", -1, -1, gameArea.getFont());
		if (panel1Font != null)
			gameArea.setFont(panel1Font);
		gameArea.setEditable(false);
		GridBagConstraints gbc_gameArea = new GridBagConstraints();
		gbc_gameArea.gridheight = 6;
		gbc_gameArea.insets = new Insets(0, 0, 5, 5);
		gbc_gameArea.fill = GridBagConstraints.BOTH;
		gbc_gameArea.gridx = 2;
		gbc_gameArea.gridy = 1;
		add(gameArea, gbc_gameArea);

		btnNewGame = new JButton("New Game");
		btnNewGame.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewGame.gridx = 5;
		gbc_btnNewGame.gridy = 1;
		add(btnNewGame, gbc_btnNewGame);

		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnUp.gridx = 5;
		gbc_btnUp.gridy = 2;
		add(btnUp, gbc_btnUp);

		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.gridx = 4;
		gbc_btnLeft.gridy = 3;
		add(btnLeft, gbc_btnLeft);

		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnRight.gridx = 6;
		gbc_btnRight.gridy = 3;
		add(btnRight, gbc_btnRight);

		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnDown.gridx = 5;
		gbc_btnDown.gridy = 4;
		add(btnDown, gbc_btnDown);

		btnExit = new JButton("Exit");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 5;
		gbc_btnExit.gridy = 6;
		add(btnExit, gbc_btnExit);

		this.initializeActions();

	}

	public void initializeActions() {

		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int numberOgres = 3;

				hero = new Hero();

				Level level2 = new KeepLevel(hero, numberOgres);
				Level level1 = new InitialLevel(hero);

				Vector<Level> levels = new Vector<>();
				levels.add(level1);
				levels.add(level2);

				dungeon = new Dungeon(levels);

				btnUp.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
				btnDown.setEnabled(true);

				printMap(dungeon.getMap());

			}

		});

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

	private Font getFont(String fontName, int style, int size, Font currentFont) {
		if (currentFont == null)
			return null;
		String resultName;
		if (fontName == null) {
			resultName = currentFont.getName();
		} else {
			Font testFont = new Font(fontName, Font.PLAIN, 10);
			if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
				resultName = fontName;
			} else {
				resultName = currentFont.getName();
			}
		}
		return new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
				size >= 0 ? size : currentFont.getSize());
	}

}
