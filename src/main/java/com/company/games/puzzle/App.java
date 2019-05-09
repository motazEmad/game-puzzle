package com.company.games.puzzle;

import com.company.games.puzzle.controller.CommandLineGameController;
import com.company.games.puzzle.controller.GameController;
import com.company.games.puzzle.controller.util.StreamFactory;
import com.company.games.puzzle.model.CommandResult;
import com.company.games.puzzle.model.ProfileCommandResult;

public class App {
	public static void main(String[] args) {
		GameController gameController = new CommandLineGameController(new StreamFactory());
		new App().game(gameController);
	}
	
	public void game(GameController gameController) {
		gameController.startGame();
		do {
			ProfileCommandResult commandResult = null;
			do {
				commandResult = gameController.loadgame();
			} while (commandResult == ProfileCommandResult.profileDeleted);

			if (commandResult == ProfileCommandResult.newProfile) {
				gameController.createPlayer();
				gameController.selectAllie();
				gameController.savegame();
			} else if (commandResult == ProfileCommandResult.exitGame) {
				break;
			}
		} while (gameController.playgame() == CommandResult.exitProfile);
	}
}
