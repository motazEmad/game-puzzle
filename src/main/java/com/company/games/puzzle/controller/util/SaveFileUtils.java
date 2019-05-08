package com.company.games.puzzle.controller.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Map;

import com.company.games.puzzle.model.GameWorld;

public class SaveFileUtils {

	private static PrintStream errorconsole = System.err;

	@SuppressWarnings("unchecked")
	public static Map<String, GameWorld> readSavedFile(File savefile) {
		Map<String, GameWorld> savedprofiles = null;
		try (FileInputStream fis = new FileInputStream(savefile);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			savedprofiles = (Map<String, GameWorld>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			errorconsole.println("Failed to load game: error loading saved file");
			return null;
		}
		return savedprofiles;
	}

	public static boolean writeSavedFile(File savefile, Map<String, GameWorld> savedprofiles) {
		try (FileOutputStream fos = new FileOutputStream(savefile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(savedprofiles);
			return true;
		} catch (IOException e) {
			errorconsole.println("Failed to load game: error loading saved file");
			return false;
		}
	}
}
