package com.company.games.puzzle.loader;

import java.util.Arrays;
import java.util.List;

import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.Experience;
import com.company.games.puzzle.model.Location;
import com.company.games.puzzle.model.Messages;

/**
 * 
 * load game theme information <br> <br>
 * Singleton
 * @author Moataz emadeldin Nasr
 *
 */
class GameLoaderImpl implements GameLoader {
	
	private static GameLoader gameLoader;
	private List<Location> locations;
	private List<Character> villains;
	private List<Character> allies;
	private Messages messages;
	
	private GameLoaderImpl() {
		villains = loadVillains();
		allies = loadAllies();
		locations = loadLocations();
		messages = loadMessages();
	}
	/** 
	 * as enhancement loading villains can be from DB
	 * @return list of enemies
	 */
	private List<Character> loadVillains() {
		return Arrays.asList(
				new Character("Thanos", new Experience(10, 100, 100)),
				new Character("Red Skull", new Experience(1, 10, 10)),
				new Character("Loki", new Experience(3, 30, 30)),
				new Character("Ultron", new Experience(5, 50, 50))
		);
	}
	
	/**
	 * 
	 * @return list of friends
	 */
	private List<Character> loadAllies() {
//		return Collections.emptyList();
		return Arrays.asList(
				new Character("Iron Man", new Experience(2, 20, 20)),
				new Character("Captin Marvel", new Experience(6, 60, 60))
		);
	}
	
	/**
	 * as enhancement loading locations can be from DB
	 * @return list of the cities, places, planets
	 */
	private List<Location> loadLocations() {
		return Arrays.asList(
				new Location("New York City"),
				new Location("Sokovia"),
				new Location("KnowWhere")
		);
	}
	
	private Messages loadMessages() {
		return new Messages("Welcome to Avengers World", 
				new String[] {"You win this match", "You are invincible"});
	}
	
	static GameLoader getInstance() {
		if(gameLoader == null) {
			gameLoader = new GameLoaderImpl();
		}
		return gameLoader;
	}
	
	@Override
	public List<Character> getAllies() {
		return allies;
	}
	
	@Override
	public List<Location> getLocations() {
		return locations;
	}
	@Override
	public List<Character> getVillains() {
		return villains;
	}
	@Override
	public Messages getMessages() {
		return messages;
	}
	
}
