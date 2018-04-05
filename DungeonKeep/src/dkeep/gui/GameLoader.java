package dkeep.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dkeep.logic.Dungeon;

public class GameLoader {

	public void SaveGame(Dungeon dungeon, String filename) throws IOException {

		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream("res/saved_games/" + filename, true);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(dungeon);

		} catch (Exception ex) {
			ex.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (objectinputstream != null)
			objectinputstream.close();

		return dungeon;

	}
}
