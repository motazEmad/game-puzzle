package com.company.games.puzzle.controller;

import com.company.games.puzzle.model.CommandResult;
import com.company.games.puzzle.model.GameWorld;
import com.company.games.puzzle.model.ProfileCommandResult;

public interface GameController {
	void startGame();
	GameWorld createPlayer();
	GameWorld selectAllie();
	CommandResult playgame();
	ProfileCommandResult loadgame();
	boolean savegame();
}
