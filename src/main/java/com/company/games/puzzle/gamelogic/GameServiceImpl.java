package com.company.games.puzzle.gamelogic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.Experience;
import com.company.games.puzzle.model.GameWorld;
import com.company.games.puzzle.model.Location;
import com.company.games.puzzle.model.Player;

/**
 * game service class that do the logic related to the gameplay
 * @author Moataz Emadeldin Nasr
 *
 */
public class GameServiceImpl implements GameService {
	private static GameService gameService;
	
	private GameServiceImpl() {
		
	}
	
	public static GameService getInstance() {
		if(gameService == null) 
			gameService = new GameServiceImpl();
		return gameService;
	}
	
	@Override
	public GameWorld setVillainsLocations(List<Character> villains, List<Location> locations) {
		Map<Location, List<Character>> world = IntStream.range(0, locations.size()).boxed()
				.collect(Collectors.toMap(
						i -> locations.get(i),
						i -> villains.stream().filter(a -> a.getExperience().getLevel() / 5 == i).collect(Collectors.toList()),
						(loc,loc2)->loc,
						LinkedHashMap::new
				));
		return new GameWorld(world);
	}

	@Override
	public boolean fight(GameWorld gameWorld, Character villain) {
		int force = 0;
		Player player = gameWorld.getPlayer();
		Character allie = gameWorld.getAllie();
		
		if(allie != null)
			force = player.getExperience().getAttach() + allie.getExperience().getAttach() 
			+ player.getExperience().getDefence() + allie.getExperience().getDefence();
		else
			force = player.getExperience().getAttach() + player.getExperience().getDefence();
		
		
		return force >= (villain.getExperience().getAttach() + villain.getExperience().getDefence());
	}

	@Override
	public void killVillain(GameWorld gameWorld, Location location, Character villain) {
		gameWorld.getWorld().get(location).remove(villain);
	}

	@Override
	public void gainExperience(Player player, Character killedVillain) {
		int attach = player.getExperience().getAttach() + killedVillain.getExperience().getAttach();
		int defence = player.getExperience().getDefence() + killedVillain.getExperience().getDefence();
		int level = attach >= defence ? attach/10 : defence/10;
		player.setExperience(new Experience(level, attach, defence));
	}
	
	@Override
	public void loseLife(Player player) {
		player.setLife(player.getLife() - 1);
	}
}
