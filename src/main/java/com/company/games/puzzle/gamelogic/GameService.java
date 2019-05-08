package com.company.games.puzzle.gamelogic;

import java.util.List;

import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.GameWorld;
import com.company.games.puzzle.model.Location;
import com.company.games.puzzle.model.Player;

public interface GameService {

	/**
	 * put villains into locations based on villains level
	 * @param villains list of villains
	 * @param locations list of locations
	 * @return game world
	 */
	GameWorld setVillainsLocations(List<Character> villains, List<Location> locations);

	boolean fight(GameWorld gameWorld, Character villain);

	void killVillain(GameWorld gameWorld, Location location, Character villain);

	void gainExperience(Player player, Character killedVillain);

	void loseLife(Player player);

}