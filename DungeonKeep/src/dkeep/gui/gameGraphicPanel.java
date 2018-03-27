package dkeep.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

public class gameGraphicPanel extends JPanel {

	private Resources resources;

	/**
	 * Create the panel.
	 */
	public gameGraphicPanel(Resources resources) {

		this.resources = resources;

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
				case 'H':
					g.drawImage(this.resources.getHero(), j * 78,  i * 78, this);
					break;
				case 'G':
					g.drawImage(this.resources.getGuard(), j * 78, i * 78, this);
					break;
				case 'A':
					g.drawImage(this.resources.getHeroArmed(), j * 78, i * 78, this);
					break;
				case 'O':
					g.drawImage(this.resources.getOgre(), j * 78, i * 78, this);
					break;
				case '8':
					g.drawImage(this.resources.getOgreStunned(), j * 78,  i * 78, this);
					break;
				case '*':
					g.drawImage(this.resources.getOgreWeapon(), j * 78, i * 78, this);
					break;
				default:
					g.drawImage(this.resources.getFloor(), j * 78, i * 78, this);
					break;

				}

			}
		}

	}

}
