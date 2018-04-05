package dkeep.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JPanel;

import dkeep.logic.levels.InitialLevel;

public class CreatorGraphics extends JPanel implements MouseListener, MouseMotionListener {

	private char[][] map;

	private Resources resources;

	final private char WALL = 'X';
	final private char OGRE = 'O';
	final private char HERO = 'H';
	final private char KEY = 'k';
	final private char DOOR = 'I';
	final private char FLOOR = ' ';

	private boolean isMazePossible = false;

	private int numberOfOgres = 0;
	private boolean isKeySet = false;
	private boolean isHeroSet = false;
	private int numberOfDoors = 0;

	private Integer mazeSize = -1;

	private int x_hero = -1;
	private int y_hero = -1;

	public void resetElements() {
		numberOfOgres = 0;
		isKeySet = false;
		isHeroSet = false;
		numberOfDoors = 0;
		x_hero = -1;
		y_hero = -1;
	}

	private int imageWidth;
	private int imageHeight;

	private char NOT_DEFINED = '0';

	private char currentDrawOption = NOT_DEFINED;

	private boolean visited[][];

	public void initVisited() {
		visited = new boolean[map.length][0];
		for (int i = 0; i < map.length; i++) {
			visited[i] = new boolean[map[i].length];
			for (int j = 0; j < map[i].length; j++) {

				visited[i][j] = false;
			}
		}
	}

	public void findHeroPos() {
		for (int j = 0; j < map.length; j++) {
			for (int i = 0; i < map[j].length; i++) {
				if (map[j][i] == HERO) {
					x_hero = i;
					y_hero = j;
				}
			}
		}

	}

	public boolean isMazePossible() {

		if (!isHeroSet || !isKeySet || numberOfDoors == 0)
			return false;

		this.initVisited();

		isMazePossible = false;

		findHeroPos();

		mazeSearch(x_hero, y_hero);

		return isMazePossible;

	}

	public void mazeSearch(int x, int y) {
		if (y < 0 || y > map.length || x < 0 || x > map[y].length)
			return;

		if (isMazePossible)
			return;

		if (visited[y][x])
			return;
		else
			visited[y][x] = true;

		if (map[y][x] == DOOR) {
			isMazePossible = true;
			return;
		}

		if (map[y][x] == WALL)
			return;

		mazeSearch(x + 1, y);
		mazeSearch(x - 1, y);
		mazeSearch(x, y + 1);
		mazeSearch(x, y - 1);
	}

	public void updateImageSize() {
		imageWidth = (int) (this.getWidth() / mazeSize);
		imageHeight = (int) (this.getHeight() / mazeSize);

		if (imageWidth < imageHeight)
			imageHeight = imageWidth;
		else
			imageWidth = imageHeight;
	}

	public CreatorGraphics(Resources resources) {
		super();

		this.addMouseListener(this);

		this.addMouseMotionListener(this);

		this.resources = resources;
	}

	@Override
	public void paintComponent(Graphics g) {

		if (mazeSize.intValue() == -1 || map == null)
			return;

		super.paintComponent(g);

		updateImageSize();

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				g.drawImage(this.resources.getFloor(), j * imageHeight, i * imageWidth, imageWidth, imageHeight, this);
				switch (map[i][j]) {
				case ' ':
					g.drawImage(this.resources.getFloor(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
							this);
					break;
				case 'X':
					g.drawImage(this.resources.getWall(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
							this);
					break;
				case 'I':
					g.drawImage(this.resources.getDoor(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
							this);
					break;
				case 'k':
					g.drawImage(this.resources.getKey(), j * imageHeight, i * imageWidth, this);
					break;
				case 'O':
					g.drawImage(this.resources.getOgre(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
							this);
					break;
				case 'H':
					g.drawImage(this.resources.getHero(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
							this);
					break;
				default:
					g.drawImage(this.resources.getFloor(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
							this);
					break;

				}

			}
		}

	}

	public void updateMap(int i, int j, char option) {

		if (option == HERO && isHeroSet)
			return;

		if (option == KEY && isKeySet)
			return;

		if (option == OGRE && numberOfOgres >= 5)
			return;

		updateInformation(map[j][i], option);

		this.map[j][i] = option;
	}

	public void updateInformation(char oldInfo, char newInfo) {
		if (oldInfo == newInfo)
			return;

		switch (oldInfo) {

		case HERO:
			isHeroSet = false;
			break;
		case KEY:
			isKeySet = false;
			break;
		case OGRE:
			numberOfOgres--;
			break;
		case DOOR:
			numberOfDoors--;
			break;
		default:
			break;

		}

		switch (newInfo) {
		case HERO:
			isHeroSet = true;
			break;
		case KEY:
			isKeySet = true;
			break;
		case OGRE:
			numberOfOgres++;
			break;
		case DOOR:
			numberOfDoors++;
			break;
		default:
			break;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int x_pos = e.getX();
		int y_pos = e.getY();

		if (currentDrawOption == NOT_DEFINED || x_pos > this.getWidth() || y_pos > this.getWidth())
			return;

		this.updateMap(x_pos / this.getImageWidth(), y_pos / this.getImageHeight(), currentDrawOption);

		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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
		resetElements();
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
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param imageWidth
	 *            the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param imageHeight
	 *            the imageHeight to set
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	/**
	 * @return the nOT_DEFINED
	 */
	public char getNOT_DEFINED() {
		return NOT_DEFINED;
	}

	/**
	 * @param nOT_DEFINED
	 *            the nOT_DEFINED to set
	 */
	public void setNOT_DEFINED(char nOT_DEFINED) {
		NOT_DEFINED = nOT_DEFINED;
	}

	/**
	 * @return the currentDrawOption
	 */
	public char getCurrentDrawOption() {
		return currentDrawOption;
	}

	/**
	 * @param currentDrawOption
	 *            the currentDrawOption to set
	 */
	public void setCurrentDrawOption(char currentDrawOption) {
		this.currentDrawOption = currentDrawOption;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x_pos = e.getX();
		int y_pos = e.getY();

		if (currentDrawOption == NOT_DEFINED || x_pos > this.getWidth() || y_pos > this.getWidth())
			return;

		this.updateMap(x_pos / this.getImageWidth(), y_pos / this.getImageHeight(), currentDrawOption);

		this.repaint();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
