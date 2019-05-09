package com.company.games.puzzle.loader;

public class GameLoaderFactory {

	public GameLoader getInstance() {
		return GameLoaderImpl.getInstance();
	}
}
