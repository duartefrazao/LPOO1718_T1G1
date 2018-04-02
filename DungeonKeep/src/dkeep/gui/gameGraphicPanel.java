package dkeep.gui;

import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import dkeep.logic.Dungeon;
import dkeep.logic.Ogre;
import dkeep.logic.guards.DrunkenGuard;
import dkeep.logic.levels.InitialLevel;
import dkeep.logic.levels.KeepLevel;

public class gameGraphicPanel extends JPanel {

	private Resources resources;

	private Dungeon dungeon;

	/**
	 * Create the panel.
	 */
	public gameGraphicPanel(Resources resources) {

		this.resources = resources;
		this.setVisible(false);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		char[][] map = this.resources.getMap();

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				switch (map[i][j]) {
				case ' ':
					g.drawImage(this.resources.getFloor(), j * 78, i * 78, this);
					break;
				case 'X':
					g.drawImage(this.resources.getWall(), j * 78, i * 78, this);
					break;
				case 'I':
					g.drawImage(this.resources.getDoor(), j * 78, i * 78, this);
					break;
				case 'S':
					g.drawImage(this.resources.getDoorOpened(), j * 78, i * 78, this);
					break;

				default:
					g.drawImage(this.resources.getFloor(), j * 78, i * 78, this);
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

			Vector<Ogre> ogres = ((KeepLevel) (this.dungeon.getCurrentLevel())).getCrazyHorde();
			paintOgres(g, ogres);

			int k_i = ((KeepLevel) (this.dungeon.getCurrentLevel())).getKey().getX();
			int k_j = ((KeepLevel) (this.dungeon.getCurrentLevel())).getKey().getY();
			paintKey(g, k_i, k_j);
		}

		paintHero(g);

	}

	public void paintOgres(Graphics g, Vector<Ogre> ogres) {

		for (int k = 0; k < ogres.size(); k++) {

			Ogre tempOgre = ogres.elementAt(k);

			int i = tempOgre.getX();
			int j = tempOgre.getY();

			if (!tempOgre.isStunned())
				g.drawImage(this.resources.getOgre(), j * 78, i * 78, this);
			else
				g.drawImage(this.resources.getOgreStunned(), j * 78, i * 78, this);

			i = tempOgre.getClub().getX();
			j = tempOgre.getClub().getY();

			g.drawImage(this.resources.getOgreWeapon(), j * 78, i * 78, this);

		}

	}

	public void paintKey(Graphics g, int i, int j) {
		g.drawImage(this.resources.getKey(), j * 78, i * 78, this);
	}

	public void paintGuards(Graphics g, int i, int j) {

		if (((InitialLevel) dungeon.getCurrentLevel()).getGuard() instanceof DrunkenGuard)
			if (((DrunkenGuard) ((InitialLevel) dungeon.getCurrentLevel()).getGuard()).isSleeping()) {
				g.drawImage(this.resources.getGuardSleeping(), j * 78, i * 78, this);
				return;
			}

		g.drawImage(this.resources.getGuard(), j * 78, i * 78, this);

	}

	public void paintLever(Graphics g, int i, int j, boolean mode) {
		if (mode)
			g.drawImage(this.resources.getLeverOn(), j * 78, i * 78, this);
		else
			g.drawImage(this.resources.getLeverOff(), j * 78, i * 78, this);

	}

	public void paintHero(Graphics g) {

		int i = dungeon.getCurrentLevel().getHero().getX();
		int j = dungeon.getCurrentLevel().getHero().getY();

		if (dungeon.getCurrentLevel().getHero().isArmed())
			g.drawImage(this.resources.getHeroArmed(), j * 78, i * 78, this);
		else {
			g.drawImage(this.resources.getHero(), j * 78, i * 78, this);

			if (dungeon.getCurrentLevel() instanceof KeepLevel) {
				int w_i = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getX();
				int w_j = ((KeepLevel) dungeon.getCurrentLevel()).getHeroWeapon().getY();

				g.drawImage(this.resources.getHeroWeapon(), w_j * 78, w_i * 78, this);
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

}
