package dkeep.gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {
	
	
	private StateMachine stateMachine;
	private JButton btnNewGame;
	private JButton btnExit;

	public MainMenu(StateMachine st) {
		
		
		setLayout(null);
		setVisible(true);

		this.initialize();
		stateMachine = st;
	}
	
	public void initialize() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		setVisible(true);
		
		this.initializeButtons();

	}

	public void initializeButtons() {
		btnNewGame = new JButton("New Game");
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewGame.gridx = 0;
		gbc_btnNewGame.gridy = 1;
		add(btnNewGame, gbc_btnNewGame);
		
		btnExit = new JButton("Exit");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExit.insets = new Insets(0, 0, 5, 0);
		gbc_btnExit.gridx = 0;
		gbc_btnExit.gridy = 2;
		add(btnExit, gbc_btnExit);
		
		this.initializeActions();
		//grabFocus();
	}
	
	public void initializeActions() {

		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.newGame);
			}

		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stateMachine.update(StateMachine.Event.exitApp);
			}
		});

		
	}
}
