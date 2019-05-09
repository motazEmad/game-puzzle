package com.company.game.game_puzzle;

import java.io.InputStream;

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

@RunWith(MockitoJUnitRunner.class)
public class AppTest {
	
	@Mock
	StreamFactory stream;
	
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
		
		CommandLineGameController gameController = new CommandLineGameController(stream);
		
		
		new App().game(gameController);
	}

}
