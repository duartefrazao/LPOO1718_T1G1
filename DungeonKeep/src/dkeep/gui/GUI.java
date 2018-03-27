package dkeep.gui;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class GUI {

	private JFrame frame;

	private GamePanel gamePanel;

	private StatusPanel statusP;

	private Resources resources;

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

		resources = new Resources();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new GamePanel(this.resources);

		frame.setContentPane(gamePanel);

		statusP = new StatusPanel();

		frame.getContentPane().add(statusP);

		frame.pack();

		frame.setVisible(true);
	}

}
