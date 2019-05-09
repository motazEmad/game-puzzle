package com.company.games.puzzle.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.company.games.puzzle.controller.util.StreamFactory;
import com.company.games.puzzle.loader.GameLoader;
import com.company.games.puzzle.loader.GameLoaderFactory;
import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.Experience;
import com.company.games.puzzle.model.GameWorld;
import com.company.games.puzzle.model.Player;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineGameControllerTest {

	@Mock
	StreamFactory stream;
	
	@Mock
	GameLoader gameLoader;
	
	@Mock
	GameLoaderFactory gameLoaderFactory;
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testStartGame() {
		GameController gameController = new CommandLineGameController(new StreamFactory());
		gameController.startGame();
	}
	
	@Test
	public void testCreatePlayer() throws IOException {
		InputStream input = getClass().getResourceAsStream("/test_resources/createplayer.txt");
		Mockito.when(stream.getInputStream()).thenReturn(input);
		Mockito.when(stream.getOutputStream()).thenReturn(System.out);
		Mockito.when(stream.getErrorStream()).thenReturn(System.err);

		CommandLineGameController gameController = new CommandLineGameController(stream);
		gameController.startGame();

		GameWorld gameWorld = gameController.createPlayer();
		assertNotNull(gameWorld.getPlayer());
		
		try(BufferedReader userinputReader = new BufferedReader(new InputStreamReader(
				getClass().getResourceAsStream("/test_resources/createplayer.txt")))) {
			assertEquals(userinputReader.readLine(), gameWorld.getPlayer().getName());
			assertEquals(Integer.parseInt(userinputReader.readLine()), gameWorld.getPlayer().getAge());
			assertEquals(Player.Gender.valueOf(userinputReader.readLine()), gameWorld.getPlayer().getGender());
		}
	}
	
	@Test
	public void testSelectAllie() {
		InputStream input = getClass().getResourceAsStream("/test_resources/selectAllie.txt");
		Mockito.when(stream.getInputStream()).thenReturn(input);
		Mockito.when(stream.getOutputStream()).thenReturn(System.out);
		Mockito.when(stream.getErrorStream()).thenReturn(System.err);
		Mockito.when(gameLoader.getAllies()).thenReturn(Arrays.asList(new Character("Hulk", new Experience(1, 10, 10))));
		Mockito.when(gameLoaderFactory.getInstance()).thenReturn(gameLoader);

		GameController gameController = new CommandLineGameController(stream, gameLoaderFactory);
		GameWorld gameWorld = gameController.selectAllie();
		assertNotNull(gameWorld.getAllie());
		assertEquals("Hulk", gameWorld.getAllie().getName());
		
	}
	
	@Test
	public void testSelectAllieNoAllie() {
		InputStream input = getClass().getResourceAsStream("/test_resources/selectAllie.txt");
		Mockito.when(stream.getInputStream()).thenReturn(input);
		Mockito.when(stream.getOutputStream()).thenReturn(System.out);
		Mockito.when(stream.getErrorStream()).thenReturn(System.err);
		Mockito.when(gameLoader.getAllies()).thenReturn(Collections.emptyList());
		Mockito.when(gameLoaderFactory.getInstance()).thenReturn(gameLoader);

		GameController gameController = new CommandLineGameController(stream, gameLoaderFactory);
		GameWorld gameWorld = gameController.selectAllie();
		assertNull(gameWorld.getAllie());
		
	}
	
//	@Test
	public void testLoadgame() {
		GameController gameController = new CommandLineGameController(new StreamFactory());
		gameController.loadgame();
	}
	
}
