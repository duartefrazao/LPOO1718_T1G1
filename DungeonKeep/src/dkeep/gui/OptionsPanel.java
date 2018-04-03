package dkeep.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import dkeep.gui.GamePanel.guardType;
import dkeep.logic.Dungeon;
import dkeep.logic.Hero;
import dkeep.logic.MovingObject;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;
import dkeep.logic.levels.Level;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class OptionsPanel extends JPanel {
	
	private JComboBox<guardType> guardPersonality;
	private JTextField numOgres;
	private JTextField mazeSize;
	private JButton btnNewGame;
	private StateMachine stateMachine;

	private guardType guardPersonlity;

	public OptionsPanel(StateMachine st) {

		stateMachine = st;

		this.initialize();
	}

	public void initialize() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		setVisible(false);
		
		this.setPreferredSize(new Dimension(500, 200));

		this.initializeButtons();

	}

	public void initializeButtons() {

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 105, 120, 114, 0 };
		gbl_panel.rowHeights = new int[] { 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		GridBagConstraints gbc_lblNumberOfOgres = new GridBagConstraints();
		gbc_lblNumberOfOgres.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfOgres.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfOgres.gridx = 1;
		gbc_lblNumberOfOgres.gridy = 2;
		panel.add(lblNumberOfOgres, gbc_lblNumberOfOgres);

		numOgres = new JTextField();
		GridBagConstraints gbc_numOgres = new GridBagConstraints();
		gbc_numOgres.fill = GridBagConstraints.BOTH;
		gbc_numOgres.insets = new Insets(0, 0, 5, 5);
		gbc_numOgres.gridx = 2;
		gbc_numOgres.gridy = 2;
		panel.add(numOgres, gbc_numOgres);
		numOgres.setColumns(10);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 4;
		panel.add(horizontalStrut, gbc_horizontalStrut);

		JLabel lblOgrePersonality = new JLabel("Guard personality");
		GridBagConstraints gbc_lblOgrePersonality = new GridBagConstraints();
		gbc_lblOgrePersonality.anchor = GridBagConstraints.EAST;
		gbc_lblOgrePersonality.insets = new Insets(0, 0, 5, 5);
		gbc_lblOgrePersonality.gridx = 1;
		gbc_lblOgrePersonality.gridy = 5;
		panel.add(lblOgrePersonality, gbc_lblOgrePersonality);

		guardPersonality = new JComboBox();
		GridBagConstraints gbc_guardPersonality = new GridBagConstraints();
		gbc_guardPersonality.insets = new Insets(0, 0, 5, 5);
		gbc_guardPersonality.fill = GridBagConstraints.HORIZONTAL;
		gbc_guardPersonality.gridx = 2;
		gbc_guardPersonality.gridy = 5;
		guardPersonality.addItem(guardType.Drunken);
		guardPersonality.addItem(guardType.Suspicious);
		guardPersonality.addItem(guardType.Rookie);
		panel.add(guardPersonality, gbc_guardPersonality);

		JLabel lblNumberOfColums = new JLabel("Maze size");
		GridBagConstraints gbc_lblNumberOfColums = new GridBagConstraints();
		gbc_lblNumberOfColums.anchor = GridBagConstraints.EAST;
		gbc_lblNumberOfColums.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfColums.gridx = 1;
		gbc_lblNumberOfColums.gridy = 7;
		panel.add(lblNumberOfColums, gbc_lblNumberOfColums);

		mazeSize = new JTextField();
		GridBagConstraints gbc_mazeSize = new GridBagConstraints();
		gbc_mazeSize.insets = new Insets(0, 0, 5, 5);
		gbc_mazeSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_mazeSize.gridx = 2;
		gbc_mazeSize.gridy = 7;
		panel.add(mazeSize, gbc_mazeSize);
		mazeSize.setColumns(10);

		btnNewGame = new JButton("New Game");
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.gridx = 3;
		gbc_btnNewGame.gridy = 9;
		panel.add(btnNewGame, gbc_btnNewGame);

		this.initializeActions();
	}

	public void initializeActions() {

		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateMachine.addOptions(Integer.valueOf(numOgres.getText()), guardPersonality.getSelectedItem(),
						Integer.valueOf(mazeSize.getText()));
				stateMachine.update(StateMachine.Event.startGame);
			}

		});

	}

}
