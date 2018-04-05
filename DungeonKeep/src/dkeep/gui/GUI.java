package dkeep.gui;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class GUI {

	private JFrame frame;

	private GamePanel gamePanel;

	private OptionsPanel optionsPanel;

	private StateMachine stateMachine;

	private Resources resources;

	private MainMenu mainMenu;
	
	private MapCreator mapCreator;
  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public GUI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws IOException {

		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		resources = new Resources(frame);
		
		

		stateMachine = new StateMachine(resources, frame);

		gamePanel = new GamePanel(this.resources, stateMachine);
		
		gameLoader = new GameLoader(stateMachine);

		optionsPanel = new OptionsPanel(stateMachine);

		mainMenu = new MainMenu(stateMachine, resources);
		
		mapCreator = new MapCreator(stateMachine, resources);
		
		mainMenu.repaint();

		frame.setContentPane(mainMenu);

		stateMachine.setPanels(optionsPanel, gamePanel, mainMenu, mapCreator, gameLoader);

		frame.pack();

		frame.setLocationRelativeTo(null);
	}

}
