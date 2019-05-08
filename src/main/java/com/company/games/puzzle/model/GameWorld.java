package com.company.games.puzzle.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameWorld implements BaseObject {

	private static final long serialVersionUID = -2654607753801713497L;
	private Map<Location, List<Character>> world = new LinkedHashMap<>();
	private Character allie;
	private Player player;
	
	public GameWorld(Map<Location, List<Character>> world) {
		this.world = world;
	}
	
	public Map<Location, List<Character>> getWorld() {
		return world;
	}
	
	public void setAllie(Character allie) {
		this.allie = allie;
	}
	
	public Character getAllie() {
		return allie;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
