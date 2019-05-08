package com.company.games.puzzle.controller;

import org.junit.Test;

import com.company.games.puzzle.model.GameWorld;

public class CommandLineGameControllerTest {

//	@Test
	public void testCreatePlayer() {
		GameController gameController = new CommandLineGameController();
		
		GameWorld gameWorld = gameController.createPlayer();
//		StringWriter bufferedWriter = new StringWriter();
//		bufferedWriter.write("ab\n");
		System.out.print("a");
//		assertNotNull(gameWorld.getPlayer());
	}
	
	@Test
	public void testLoadgame() {
		GameController gameController = new CommandLineGameController();
		gameController.loadgame();
	}
	
}
