package dkeep.gui;

import javax.swing.JPanel;

import dkeep.gui.GamePanel.guardType;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JSlider;

public class OptionsPanel extends JPanel {

	private JComboBox<guardType> guardPersonality;
	private JButton btnNewGame;
	private StateMachine stateMachine;
	private JSlider numOgres;
	private JPanel panel;

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

		this.setPreferredSize(new Dimension(619, 221));
		this.setMinimumSize(new Dimension(500, 200));
		this.setMaximumSize(new Dimension(500, 200));

		this.initMembers();

	}

	public void initMembers() {
		initPanel();
		initLabelOgres();
		initSlider();
		initStruct();
		initOgreLabel();
		initPersonality();
		initNewGameButton();
	}

	public void initPanel() {
		panel = new JPanel();
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
	}

	public void initLabelOgres() {
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		GridBagConstraints gbc_lblNumberOfOgres = new GridBagConstraints();
		gbc_lblNumberOfOgres.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfOgres.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfOgres.gridx = 1;
		gbc_lblNumberOfOgres.gridy = 2;
		panel.add(lblNumberOfOgres, gbc_lblNumberOfOgres);
	}

	public void initSlider() {
		numOgres = new JSlider();
		numOgres.setPaintLabels(true);
		numOgres.setPaintTicks(true);
		numOgres.setSnapToTicks(true);
		numOgres.setMinorTickSpacing(1);
		numOgres.setMinimum(1);
		numOgres.setMaximum(5);
		numOgres.setMajorTickSpacing(1);
		GridBagConstraints gbc_numOgres = new GridBagConstraints();
		gbc_numOgres.fill = GridBagConstraints.BOTH;
		gbc_numOgres.insets = new Insets(0, 0, 5, 5);
		gbc_numOgres.gridx = 2;
		gbc_numOgres.gridy = 2;
		panel.add(numOgres, gbc_numOgres);
	}

	public void initStruct() {
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 4;
		panel.add(horizontalStrut, gbc_horizontalStrut);
	}

	public void initOgreLabel() {
		JLabel lblOgrePersonality = new JLabel("Guard personality");
		GridBagConstraints gbc_lblOgrePersonality = new GridBagConstraints();
		gbc_lblOgrePersonality.anchor = GridBagConstraints.EAST;
		gbc_lblOgrePersonality.insets = new Insets(0, 0, 5, 5);
		gbc_lblOgrePersonality.gridx = 1;
		gbc_lblOgrePersonality.gridy = 5;
		panel.add(lblOgrePersonality, gbc_lblOgrePersonality);
	}

	public void initPersonality() {
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
	}

	public void initNewGameButton() {
		btnNewGame = new JButton("New Game");
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewGame.gridx = 3;
		gbc_btnNewGame.gridy = 8;
		panel.add(btnNewGame, gbc_btnNewGame);

		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateMachine.addOptions(numOgres.getValue(), guardPersonality.getSelectedItem());
				stateMachine.update(StateMachine.Event.startGame);
			}

		});
	}



}
