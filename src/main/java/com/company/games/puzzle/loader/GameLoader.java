package com.company.games.puzzle.loader;

import java.util.List;

import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.Location;
import com.company.games.puzzle.model.Messages;

/**
 * load game theme information, in order to change the theme you can implement this interface
 * @author Moataz Emadeldin
 *
 */
public interface GameLoader {

	List<Character> getAllies();

	List<Location> getLocations();

	List<Character> getVillains();

	Messages getMessages();
}