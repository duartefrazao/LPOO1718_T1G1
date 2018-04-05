package dkeep.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JPanel;

import dkeep.logic.Dungeon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameLoader extends JPanel {

	private File[] listOfFiles;
	private Vector<String> nameOfFiles;
	private JComboBox<String> comboBox;
	private Dungeon dungeon;
	private StateMachine stateMachine;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField txtLoader;
	private JButton btnBack;

	public GameLoader(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
		this.initialize();
		this.initComponents();
		this.addFilesOptions();
	}

	public void addFilesOptions() {

		File folder = new File("res/saved_games/");

		listOfFiles = folder.listFiles();

		for (File f : listOfFiles) {
			comboBox.addItem(f.getName());
		}

	}

	public void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 47, 165, 142, 32, 0 };
		gridBagLayout.rowHeights = new int[] { 94, 0, 129, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		this.setPreferredSize(new Dimension(500, 200));
		this.setMinimumSize(new Dimension(500, 200));
	}

	public void initComponents() {
		this.initBtnOK();
		this.initCombo();
		this.initTxt();
	}

	public void initTxt() {
		txtLoader = new JTextField();
		txtLoader.setHorizontalAlignment(SwingConstants.CENTER);
		txtLoader.setText("Select a file to load:");
		txtLoader.setEditable(false);
		GridBagConstraints gbc_txtLoader = new GridBagConstraints();
		gbc_txtLoader.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLoader.insets = new Insets(0, 0, 5, 5);
		gbc_txtLoader.gridx = 1;
		gbc_txtLoader.gridy = 1;
		add(txtLoader, gbc_txtLoader);
		txtLoader.setColumns(10);
	}

	public void initCombo() {
		comboBox = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);
	}

	public void initBtnOK() {
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String option = (String) comboBox.getSelectedItem();

				try {
					dungeon = loadGame(option);
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(1);
				}

				stateMachine.update(StateMachine.Event.startGame);

			}
		});
		{
			btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stateMachine.update(StateMachine.Event.back);
				}
			});
			GridBagConstraints gbc_btnBack = new GridBagConstraints();
			gbc_btnBack.insets = new Insets(0, 0, 0, 5);
			gbc_btnBack.gridx = 1;
			gbc_btnBack.gridy = 2;
			add(btnBack, gbc_btnBack);
		}
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 2;
		add(btnNewButton, gbc_btnNewButton);
	}

	public void SaveGame(Dungeon dungeon, String filename) throws IOException {

		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream("res/saved_games/" + filename, false);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(dungeon);
 
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		if (oos != null) {
			oos.close();

		}

	}

	public Dungeon loadGame(String filename) throws IOException {

		Dungeon dungeon = null;
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;

		try {
			streamIn = new FileInputStream("res/saved_games/" + filename);
			objectinputstream = new ObjectInputStream(streamIn);
			dungeon = (Dungeon) objectinputstream.readObject();
			this.stateMachine.gamePanel.setDungeon(dungeon);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (objectinputstream != null)
			objectinputstream.close();

		return dungeon;

	}

	/**
	 * @return the listOfFiles
	 */
	public File[] getListOfFiles() {
		return listOfFiles;
	}

	/**
	 * @param listOfFiles
	 *            the listOfFiles to set
	 */
	public void setListOfFiles(File[] listOfFiles) {
		this.listOfFiles = listOfFiles;
	}

	/**
	 * @return the nameOfFiles
	 */
	public Vector<String> getNameOfFiles() {
		return nameOfFiles;
	}

	/**
	 * @param nameOfFiles
	 *            the nameOfFiles to set
	 */
	public void setNameOfFiles(Vector<String> nameOfFiles) {
		this.nameOfFiles = nameOfFiles;
	}

	/**
	 * @return the comboBox
	 */
	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	/**
	 * @param comboBox
	 *            the comboBox to set
	 */
	public void setComboBox(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
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
	 * @return the txtLoader
	 */
	public JTextField getTxtLoader() {
		return txtLoader;
	}

	/**
	 * @param txtLoader
	 *            the txtLoader to set
	 */
	public void setTxtLoader(JTextField txtLoader) {
		this.txtLoader = txtLoader;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
