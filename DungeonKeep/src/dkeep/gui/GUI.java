package dkeep.gui;

import dkeep.logic.Dungeon;
import dkeep.logic.MovingObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public JPanel getContent() {

        return this.panel1;
    }

    private JPanel panel1;
    private JLabel Ogres;
    private JTextField noOgres;
    private JComboBox comboBox1;
    private JTextArea gameArea;
    private JButton startGame;
    private JButton exitButton;
    private JButton leftButton;
    private JButton upButton;
    private JButton rightButton;
    private JTextField gameStatus;
    private JButton downButton;
    private Dungeon dungeon;

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
            upButton.setEnabled(false);
            leftButton.setEnabled(false);
            downButton.setEnabled(false);
            rightButton.setEnabled(false);

            gameStatus.setText("Game Over");

            //TO-DO
            //Here we have to reset the game, still wasn't able to do it
            System.exit(0);

        }
    }

    public GUI(Dungeon d) {

        this.dungeon = d;

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int numberOgres = 0;

                String s = noOgres.getText();


                if (!s.isEmpty()) {

                    try {
                        numberOgres = Integer.parseInt(s);
                    } catch (NumberFormatException exc) {
                        gameStatus.setText("Please insert a valid number!");
                        noOgres.setText("");
                        return;
                    }

                    upButton.setEnabled(true);
                    leftButton.setEnabled(true);
                    downButton.setEnabled(true);
                    rightButton.setEnabled(true);

                    printMap(dungeon.getMap());


                } else {
                    gameStatus.setText("Please insert a number of Ogres!");
                }

            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                processGame(MovingObject.MOVEMENT_TYPE.UP);

            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGame(MovingObject.MOVEMENT_TYPE.DOWN);
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGame(MovingObject.MOVEMENT_TYPE.RIGHT);
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGame(MovingObject.MOVEMENT_TYPE.LEFT);
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        Font panel1Font = this.$$$getFont$$$("Monospaced", -1, -1, panel1.getFont());
        if (panel1Font != null) panel1.setFont(panel1Font);
        panel1.setMaximumSize(new Dimension(845, 711));
        Ogres = new JLabel();
        Ogres.setHorizontalAlignment(11);
        Ogres.setHorizontalTextPosition(10);
        Ogres.setText("Number of Ogres:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(Ogres, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer2, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Guard Personality:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label1, gbc);
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Novice");
        defaultComboBoxModel1.addElement("Suspicious");
        defaultComboBoxModel1.addElement("Drunken");
        comboBox1.setModel(defaultComboBoxModel1);
        comboBox1.setToolTipText("Chose a Personality");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 70;
        panel1.add(comboBox1, gbc);
        gameArea = new JTextArea();
        gameArea.setEditable(false);
        Font gameAreaFont = this.$$$getFont$$$("Monospaced", -1, -1, gameArea.getFont());
        if (gameAreaFont != null) gameArea.setFont(gameAreaFont);
        gameArea.setMaximumSize(new Dimension(500, 500));
        gameArea.setMinimumSize(new Dimension(500, 500));
        gameArea.setPreferredSize(new Dimension(500, 500));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.gridheight = 6;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(gameArea, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 10;
        panel1.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 10;
        panel1.add(spacer4, gbc);
        startGame = new JButton();
        startGame.setText("New Game");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(startGame, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 25;
        panel1.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 25;
        panel1.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 8;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 25;
        panel1.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 25;
        panel1.add(spacer8, gbc);
        exitButton = new JButton();
        exitButton.setText("Exit");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(exitButton, gbc);
        upButton = new JButton();
        upButton.setEnabled(false);
        upButton.setMaximumSize(new Dimension(64, 33));
        upButton.setMinimumSize(new Dimension(64, 33));
        upButton.setPreferredSize(new Dimension(64, 33));
        upButton.setText("Up");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 7;
        gbc.ipadx = 10;
        gbc.ipady = 1;
        panel1.add(upButton, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 163;
        panel1.add(spacer9, gbc);
        noOgres = new JTextField();
        noOgres.setColumns(0);
        noOgres.setMaximumSize(new Dimension(50, 25));
        noOgres.setMinimumSize(new Dimension(50, 25));
        noOgres.setPreferredSize(new Dimension(50, 25));
        noOgres.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 15);
        panel1.add(noOgres, gbc);
        gameStatus = new JTextField();
        gameStatus.setEditable(false);
        Font gameStatusFont = this.$$$getFont$$$("Loma", -1, -1, gameStatus.getFont());
        if (gameStatusFont != null) gameStatus.setFont(gameStatusFont);
        gameStatus.setHorizontalAlignment(0);
        gameStatus.setText("Welcome to Dungeon Keep!");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(gameStatus, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer10, gbc);
        leftButton = new JButton();
        leftButton.setEnabled(false);
        leftButton.setMaximumSize(new Dimension(64, 33));
        leftButton.setMinimumSize(new Dimension(64, 33));
        leftButton.setPreferredSize(new Dimension(64, 33));
        leftButton.setText("Left");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 8;
        gbc.ipadx = 10;
        gbc.ipady = 1;
        panel1.add(leftButton, gbc);
        rightButton = new JButton();
        rightButton.setEnabled(false);
        rightButton.setMaximumSize(new Dimension(64, 33));
        rightButton.setMinimumSize(new Dimension(64, 33));
        rightButton.setPreferredSize(new Dimension(64, 33));
        rightButton.setText("Right");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 8;
        gbc.ipadx = 10;
        gbc.ipady = 1;
        panel1.add(rightButton, gbc);
        downButton = new JButton();
        downButton.setEnabled(false);
        downButton.setMaximumSize(new Dimension(64, 33));
        downButton.setMinimumSize(new Dimension(64, 33));
        downButton.setPreferredSize(new Dimension(64, 33));
        downButton.setText("Down");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 9;
        gbc.ipadx = 10;
        gbc.ipady = 1;
        panel1.add(downButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
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
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
