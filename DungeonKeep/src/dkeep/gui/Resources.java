package dkeep.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	private BufferedImage floor;

	private BufferedImage wall;

	private BufferedImage hero;
	private BufferedImage heroArmed;

	private BufferedImage heroWeapon;

	private BufferedImage ogre;
	private BufferedImage ogreStunned;
	private BufferedImage ogreWeapon;

	private BufferedImage guard;
	private BufferedImage guardSleeping;

	private BufferedImage key;

	private BufferedImage lever;
	private BufferedImage leverActivated;

	private BufferedImage door;
	private BufferedImage doorOpened;

	public final int IMAGE_SIZE = 78;

	private char[][] map;

	public Resources() {

		this.loadImages();
	}

	public void loadImages() {

		try {
			setWall(ImageIO.read(new File("res/wall.jpg")));
		} catch (IOException e) {
			System.out.println("failed to open wall image");
			System.exit(1);
		}

		try {
			setFloor(ImageIO.read(new File("res/floor.jpg")));
		} catch (IOException e) {
			System.out.println("failed to open floor image");
			System.exit(1);
		}

		try {
			hero = ImageIO.read(new File("res/hero.jpg"));
		} catch (IOException e) {
			System.exit(1);
		}

		try {
			heroArmed = ImageIO.read(new File("res/heroArmed.jpg"));
		} catch (IOException e) {
			System.exit(1);
		}

		try {
			heroWeapon = ImageIO.read(new File("res/heroWeapon.jpg"));
		} catch (IOException e) {
			System.exit(1);
		}

		try {
			ogreWeapon = ImageIO.read(new File("res/ogreWeapon.jpg"));
		} catch (IOException e) {
			System.exit(1);
		}

		try {
			ogre = ImageIO.read(new File("res/ogre.jpg"));
		} catch (IOException e) {
			System.exit(1);
		}

		try {
			ogreStunned = ImageIO.read(new File("res/ogreStunned.jpg"));
		} catch (IOException e) {
			System.exit(1);
		}

		try {
			guard = ImageIO.read(new File("res/guard.jpg"));
		} catch (IOException e) {
			System.out.println("failed to open guard image");
			System.exit(1);
		}

		// try {
		// guardSleeping = ImageIO.read(new File("guardSleeping.jpg"));
		// } catch (IOException e) {
		// System.exit(1);
		// }
		//
		// try {
		// key = ImageIO.read(new File("key.jpg"));
		// } catch (IOException e) {
		// System.exit(1);
		// }
		//
		// try {
		// lever = ImageIO.read(new File("lever.jpg"));
		// } catch (IOException e) {
		// System.exit(1);
		// }
		//
		// try {
		// leverActivated = ImageIO.read(new File("leverActivated.jpg"));
		// } catch (IOException e) {
		// System.exit(1);
		// }

		try {
			door = ImageIO.read(new File("res/door.jpg"));
		} catch (IOException e) {
			System.out.println("failed to open door image");
			System.exit(1);
		}

		try {
			doorOpened = ImageIO.read(new File("res/doorOpened.jpg"));
		} catch (IOException e) {
			System.out.println("failed to open doorOpened image");
			System.exit(1);
		}
	}

	/**
	 * @return the floor
	 */
	public BufferedImage getFloor() {
		return floor;
	}

	/**
	 * @param floor
	 *            the floor to set
	 */
	public void setFloor(BufferedImage floor) {
		this.floor = floor;
	}

	/**
	 * @return the wall
	 */
	public BufferedImage getWall() {
		return wall;
	}

	/**
	 * @param wall
	 *            the wall to set
	 */
	public void setWall(BufferedImage wall) {
		this.wall = wall;
	}

	/**
	 * @return the hero
	 */
	public BufferedImage getHero() {
		return hero;
	}

	/**
	 * @param hero
	 *            the hero to set
	 */
	public void setHero(BufferedImage hero) {
		this.hero = hero;
	}

	/**
	 * @return the heroArmed
	 */
	public BufferedImage getHeroArmed() {
		return heroArmed;
	}

	/**
	 * @param heroArmed
	 *            the heroArmed to set
	 */
	public void setHeroArmed(BufferedImage heroArmed) {
		this.heroArmed = heroArmed;
	}

	/**
	 * @return the heroWeapon
	 */
	public BufferedImage getHeroWeapon() {
		return heroWeapon;
	}

	/**
	 * @param heroWeapon
	 *            the heroWeapon to set
	 */
	public void setHeroWeapon(BufferedImage heroWeapon) {
		this.heroWeapon = heroWeapon;
	}

	/**
	 * @return the ogre
	 */
	public BufferedImage getOgre() {
		return ogre;
	}

	/**
	 * @param ogre
	 *            the ogre to set
	 */
	public void setOgre(BufferedImage ogre) {
		this.ogre = ogre;
	}

	/**
	 * @return the ogreStunned
	 */
	public BufferedImage getOgreStunned() {
		return ogreStunned;
	}

	/**
	 * @param ogreStunned
	 *            the ogreStunned to set
	 */
	public void setOgreStunned(BufferedImage ogreStunned) {
		this.ogreStunned = ogreStunned;
	}

	/**
	 * @return the guard
	 */
	public BufferedImage getGuard() {
		return guard;
	}

	/**
	 * @param guard
	 *            the guard to set
	 */
	public void setGuard(BufferedImage guard) {
		this.guard = guard;
	}

	/**
	 * @return the guardSleeping
	 */
	public BufferedImage getGuardSleeping() {
		return guardSleeping;
	}

	/**
	 * @param guardSleeping
	 *            the guardSleeping to set
	 */
	public void setGuardSleeping(BufferedImage guardSleeping) {
		this.guardSleeping = guardSleeping;
	}

	/**
	 * @return the key
	 */
	public BufferedImage getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(BufferedImage key) {
		this.key = key;
	}

	/**
	 * @return the lever
	 */
	public BufferedImage getLever() {
		return lever;
	}

	/**
	 * @param lever
	 *            the lever to set
	 */
	public void setLever(BufferedImage lever) {
		this.lever = lever;
	}

	/**
	 * @return the leverActivated
	 */
	public BufferedImage getLeverActivated() {
		return leverActivated;
	}

	/**
	 * @param leverActivated
	 *            the leverActivated to set
	 */
	public void setLeverActivated(BufferedImage leverActivated) {
		this.leverActivated = leverActivated;
	}

	/**
	 * @return the door
	 */
	public BufferedImage getDoor() {
		return door;
	}

	/**
	 * @param door
	 *            the door to set
	 */
	public void setDoor(BufferedImage door) {
		this.door = door;
	}

	/**
	 * @return the doorOpened
	 */
	public BufferedImage getDoorOpened() {
		return doorOpened;
	}

	/**
	 * @param doorOpened
	 *            the doorOpened to set
	 */
	public void setDoorOpened(BufferedImage doorOpened) {
		this.doorOpened = doorOpened;
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
	}

	/**
	 * @return the ogreWeapon
	 */
	public BufferedImage getOgreWeapon() {
		return ogreWeapon;
	}

	/**
	 * @param ogreWeapon the ogreWeapon to set
	 */
	public void setOgreWeapon(BufferedImage ogreWeapon) {
		this.ogreWeapon = ogreWeapon;
	}
	

}
