package dkeep.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import dkeep.logic.Dungeon;
import dkeep.logic.Ogre;
import dkeep.logic.guards.DrunkenGuard;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;

public class GameGraphics extends JPanel {

	private Resources resources;

	private Dungeon dungeon;

	private Dimension fixedDimension = new Dimension(700, 700);

	private int imageWidth;
	private int imageHeight;

	public void updateImageSize() {
		imageWidth = (int) (fixedDimension.getWidth() / resources.getMapWidth());
		imageHeight = (int) (fixedDimension.getHeight() / resources.getMapHeigth());

		if (imageWidth < imageHeight)
			imageHeight = imageWidth;
		else
			imageWidth = imageHeight;
	}

	@Override
	public Dimension getPreferredSize() {
		return this.fixedDimension;
	}

	public void updateSize() {

		this.setSize(fixedDimension);
	}

	public GameGraphics(Resources resources) {
		super();

		this.resources = resources;

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		char[][] map = this.resources.getMap();

		updateImageSize();

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

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
				case 'S':
					g.drawImage(this.resources.getDoorOpened(), j * imageHeight, i * imageWidth, imageWidth,
							imageHeight, this);
					break;
				default:
					g.drawImage(this.resources.getFloor(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
							this);
					break;

				}

			}
		}

		this.paintObjects(g);

	}

	public void paintObjects(Graphics g) {

		if (dungeon.getCurrentLevel() instanceof InitialLevel) {

			int g_i = ((InitialLevel) (this.dungeon.getCurrentLevel())).getGuard().getX();
			int g_j = ((InitialLevel) (this.dungeon.getCurrentLevel())).getGuard().getY();
			paintGuards(g, g_i, g_j);

			boolean lever = ((InitialLevel) (this.dungeon.getCurrentLevel())).isLeverOff();
			int l_i = ((InitialLevel) (this.dungeon.getCurrentLevel())).getLever().getX();
			int l_j = ((InitialLevel) (this.dungeon.getCurrentLevel())).getLever().getY();

			paintLever(g, l_i, l_j, lever);
		} else {

			int k_i = ((KeepLevel) (this.dungeon.getCurrentLevel())).getKey().getX();
			int k_j = ((KeepLevel) (this.dungeon.getCurrentLevel())).getKey().getY();
			paintKey(g, k_i, k_j);

			Vector<Ogre> ogres = ((KeepLevel) (this.dungeon.getCurrentLevel())).getCrazyHorde();
			paintOgres(g, ogres);

		}

		paintHero(g);

	}

	public void paintOgres(Graphics g, Vector<Ogre> ogres) {

		for (int k = 0; k < ogres.size(); k++) {

			Ogre tempOgre = ogres.elementAt(k);

			int i = tempOgre.getX();
			int j = tempOgre.getY();

			if (!tempOgre.isStunned())
				g.drawImage(this.resources.getOgre(), j * imageHeight, i * imageWidth, imageWidth, imageHeight, this);
			else
				g.drawImage(this.resources.getOgreStunned(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
						this);

			i = tempOgre.getClub().getX();
			j = tempOgre.getClub().getY();

			if (resources.getMap()[i][j] != 'O')
				g.drawImage(this.resources.getOgreWeapon(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
						this);

		}

	}

	public void paintKey(Graphics g, int i, int j) {

		if (!dungeon.getCurrentLevel().getHero().hasKey())
			g.drawImage(this.resources.getKey(), j * imageHeight, i * imageWidth, this);
		else
			g.drawImage(this.resources.getFloor(), j * imageHeight, i * imageWidth, this);
	}

	public void paintGuards(Graphics g, int i, int j) {

		if (((InitialLevel) dungeon.getCurrentLevel()).getGuard() instanceof DrunkenGuard)
			if (((DrunkenGuard) ((InitialLevel) dungeon.getCurrentLevel()).getGuard()).isSleeping()) {
				g.drawImage(this.resources.getGuardSleeping(), j * imageHeight, i * imageWidth, imageWidth, imageHeight,
						this);
				return;
			}

		g.drawImage(this.resources.getGuard(), j * imageHeight, i * imageWidth, imageWidth, imageHeight, this);

	}

	public void paintLever(Graphics g, int i, int j, boolean mode) {
		if (mode)
			g.drawImage(this.resources.getLeverOn(), j * imageHeight, i * imageWidth, imageWidth, imageHeight, this);
		else
			g.drawImage(this.resources.getLeverOff(), j * imageHeight, i * imageWidth, imageWidth, imageHeight, this);

	}

	public void paintHero(Graphics g) {

		int i = dungeon.getCurrentLevel().getHero().getX();
		int j = dungeon.getCurrentLevel().getHero().getY();

		if (dungeon.getCurrentLevel().getHero().isArmed())
			g.drawImage(this.resources.getHeroArmed(), j * imageHeight, i * imageWidth, imageWidth, imageHeight, this);
		else {
			g.drawImage(this.resources.getHero(), j * imageHeight, i * imageWidth, imageWidth, imageHeight, this);

			if (dungeon.getCurrentLevel() instanceof KeepLevel) {
				int w_i = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getX();
				int w_j = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getY();
				g.drawImage(this.resources.getFloor(), w_j * imageHeight, w_i * imageWidth, imageWidth, imageHeight,
						this);
				g.drawImage(this.resources.getHeroWeapon(), w_j * imageHeight, w_i * imageWidth, imageWidth,
						imageHeight, this);
			}

		}

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
	 * @return the fixedDimension
	 */
	public Dimension getFixedDimension() {
		return fixedDimension;
	}

	/**
	 * @param fixedDimension
	 *            the fixedDimension to set
	 */
	public void setFixedDimension(Dimension fixedDimension) {
		this.fixedDimension = fixedDimension;
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

}
