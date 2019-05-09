package com.company.games.puzzle.gamelogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import com.company.games.puzzle.gamelogic.GameServiceImpl;
import com.company.games.puzzle.loader.GameLoader;
import com.company.games.puzzle.loader.GameLoaderFactory;
import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.Experience;
import com.company.games.puzzle.model.GameWorld;
import com.company.games.puzzle.model.Location;
import com.company.games.puzzle.model.Player;

public class GameServiceTest {
	
	@Test
	public void testsetVillainsLocations() {
		
		GameLoader gameLoader = new GameLoaderFactory().getInstance();
		List<Location> locations = gameLoader.getLocations();
		List<Character> villains = gameLoader.getVillains();
		GameWorld game = GameServiceImpl.getInstance().setVillainsLocations(villains, locations);
		assertNotNull(game.getWorld());
		
		// assert all locations available 
		locations.forEach(a ->  assertTrue(game.getWorld().containsKey(a)));
		
		// assert all villains available
		villains.forEach(villain -> assertTrue(game.getWorld().values().stream().flatMap(l -> l.stream()).anyMatch(b -> b.equals(villain))));
		
		// assert villains in correct locations
		IntStream
			.range(0, locations.size())
			.forEach(i -> game.getWorld().get(locations.get(i))
					.forEach(villain -> assertTrue(villain.getExperience().getLevel() / 5 == i)));
		
	}
	
	@Test
	public void testWinFight() {
		GameLoader gameLoader = new GameLoaderFactory().getInstance();
		List<Location> locations = gameLoader.getLocations();
		List<Character> villains = gameLoader.getVillains();
		GameWorld game = GameServiceImpl.getInstance().setVillainsLocations(villains, locations);
		
		
		Player player = new Player();
		player.setExperience(new Experience(1, 20, 20));
		game.setPlayer(player);
		
		boolean fight = GameServiceImpl.getInstance().fight(game, new Character("Loki", new Experience(1, 10, 10)));
		assertTrue(fight);
		
	}
	
	@Test
	public void testLoseFight() {
		GameLoader gameLoader = new GameLoaderFactory().getInstance();
		List<Location> locations = gameLoader.getLocations();
		List<Character> villains = gameLoader.getVillains();
		GameWorld game = GameServiceImpl.getInstance().setVillainsLocations(villains, locations);
		
		
		Player player = new Player();
		player.setExperience(new Experience(1, 20, 10));
		game.setPlayer(player);
		
		boolean fight = GameServiceImpl.getInstance().fight(game, new Character("Loki", new Experience(1, 25, 15)));
		assertFalse(fight);	
	}
	
	@Test
	public void testFightwithAllie() {
		GameLoader gameLoader = new GameLoaderFactory().getInstance();
		List<Location> locations = gameLoader.getLocations();
		List<Character> villains = gameLoader.getVillains();
		GameWorld game = GameServiceImpl.getInstance().setVillainsLocations(villains, locations);
		
		Character allie = new Character("Starlord", new Experience(5, 50, 50));
		game.setAllie(allie);
		
		Player player = new Player();
		player.setExperience(new Experience(1, 20, 10));
		game.setPlayer(player);
		
		boolean fight = GameServiceImpl.getInstance().fight(game, new Character("Loki", new Experience(1, 25, 15)));
		assertTrue(fight);	
	}
	
	@Test
	public void testKillVillain() {
		List<Location> locations = Arrays.asList(new Location("Void"));
		List<Character> villains = Arrays.asList(new Character("Dormammu", new Experience(9, 90, 90)));
		GameWorld game = GameServiceImpl.getInstance().setVillainsLocations(villains, locations);
	
		GameServiceImpl.getInstance().killVillain(game, new Location("Void"), new Character("Dormammu", new Experience(9, 90, 90)));
		
		assertTrue(game.getWorld().get(new Location("Void")).isEmpty());
	}
	
	@Test
	public void testGainExperience() {
		
		Player player = new Player();
		player.setExperience(new Experience(1, 5, 5));
		Character killedVillain = new Character("Dormammu", new Experience(9, 90, 90));
		GameServiceImpl.getInstance().gainExperience(player, killedVillain);
		
		assertEquals(9, player.getExperience().getLevel());
		assertEquals(95, player.getExperience().getAttach());
		assertEquals(95, player.getExperience().getDefence());
	}
	
	@Test
	public void testGainExperienceDefence() {
		
		Player player = new Player();
		player.setExperience(new Experience(1, 5, 10));
		Character killedVillain = new Character("Dormammu", new Experience(9, 90, 90));
		GameServiceImpl.getInstance().gainExperience(player, killedVillain);
		
		assertEquals(10, player.getExperience().getLevel());
		assertEquals(95, player.getExperience().getAttach());
		assertEquals(100, player.getExperience().getDefence());
	}
	
	@Test
	public void testLoseLife() {
		Player player = new Player();
		player.setLife(1);
		GameServiceImpl.getInstance().loseLife(player);
		assertEquals(0, player.getLife());
	}
	
}
