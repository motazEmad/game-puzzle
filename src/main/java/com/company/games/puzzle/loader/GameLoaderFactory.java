package com.company.games.puzzle.loader;

public class GameLoaderFactory {

	public static GameLoader getInstance() {
		return GameLoaderImpl.getInstance();
	}
}
