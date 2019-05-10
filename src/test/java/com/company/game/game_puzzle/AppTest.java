package com.company.game.game_puzzle;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.company.games.puzzle.App;
import com.company.games.puzzle.controller.CommandLineGameController;
import com.company.games.puzzle.controller.util.StreamFactory;
import com.company.games.puzzle.loader.GameLoader;
import com.company.games.puzzle.loader.GameLoaderFactory;
import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.Experience;
import com.company.games.puzzle.model.Location;
import com.company.games.puzzle.model.Messages;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {
	
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
	public void testApp() {
		InputStream input = getClass().getResourceAsStream("/test_resources/success_scenario.txt");
		Mockito.when(stream.getInputStream()).thenReturn(input);
		Mockito.when(stream.getOutputStream()).thenReturn(System.out);
		Mockito.when(stream.getErrorStream()).thenReturn(System.err);
		
		Mockito.when(gameLoader.getAllies()).thenReturn(Arrays.asList(
				new Character("Iron Man", new Experience(2, 20, 20)),
				new Character("Captin Marvel", new Experience(6, 60, 60))
		));
		
		Mockito.when(gameLoader.getVillains()).thenReturn(Arrays.asList(
				new Character("Thanos", new Experience(10, 100, 100)),
				new Character("Red Skull", new Experience(1, 10, 10)),
				new Character("Loki", new Experience(3, 30, 30)),
				new Character("Ultron", new Experience(5, 50, 50))
		));
		
		Mockito.when(gameLoader.getLocations()).thenReturn(Arrays.asList(
				new Location("New York City"),
				new Location("Sokovia"),
				new Location("KnowWhere")
		));
		
		Mockito.when(gameLoader.getMessages()).thenReturn(new Messages("Welcome to Avengers World", 
				new String[] {"You win this match", "You are invincible"}));
		Mockito.when(gameLoaderFactory.getInstance()).thenReturn(gameLoader);
		
		CommandLineGameController gameController = new CommandLineGameController(stream, gameLoaderFactory);
		new App().game(gameController);
	}
	
	@After
	public void distroy() {
		
	}

}
