package com.company.games.puzzle.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.IntStream;

import com.company.games.puzzle.controller.util.ConsoleReaderUtils;
import com.company.games.puzzle.controller.util.StreamFactory;
import com.company.games.puzzle.controller.util.SaveFileUtils;
import com.company.games.puzzle.exception.CommandException;
import com.company.games.puzzle.gamelogic.GameService;
import com.company.games.puzzle.gamelogic.GameServiceImpl;
import com.company.games.puzzle.loader.GameLoader;
import com.company.games.puzzle.loader.GameLoaderFactory;
import com.company.games.puzzle.model.Character;
import com.company.games.puzzle.model.CommandResult;
import com.company.games.puzzle.model.Experience;
import com.company.games.puzzle.model.GameWorld;
import com.company.games.puzzle.model.Location;
import com.company.games.puzzle.model.Player;
import com.company.games.puzzle.model.ProfileCommandResult;

public class CommandLineGameController implements GameController {

	private PrintStream console;
	private PrintStream errorconsole;
	private BufferedReader userinputReader;
	private Properties properties = new Properties();
	private Map<String, GameWorld> savedprofiles;
	private GameWorld gameWorld;
	private GameLoaderFactory gameLoaderFactory;
	
	public CommandLineGameController(StreamFactory streamFactory) {
		this.userinputReader = new BufferedReader(new InputStreamReader(streamFactory.getInputStream()));
		console = streamFactory.getOutputStream();
		errorconsole = streamFactory.getErrorStream();
		gameLoaderFactory = new GameLoaderFactory();
		try (InputStream input = getClass().getResourceAsStream("/resources/config.properties")) {
			properties.load(input);
		} catch (IOException io) {
			errorconsole.println("Failed to load game: error loading configuration");
		}
	}
	
	public CommandLineGameController(StreamFactory streamFactory, GameLoaderFactory gameLoaderFactory) {
		this(streamFactory);
		this.gameLoaderFactory = gameLoaderFactory;
	}
	public CommandLineGameController(StreamFactory streamFactory, GameLoaderFactory gameLoaderFactory, Properties properties) {
		this(streamFactory, gameLoaderFactory);
		this.properties = properties;
	}

	public void startGame() {
		console.println("Loading...");
		GameLoader gameLoader = gameLoaderFactory.getInstance();
		String welcommessage = gameLoader.getMessages().getWelcomeMessage();
		console.println(welcommessage);
		console.println("Game is ready");
	}
	
	private ProfileCommandResult loadgame(String playerName) {
		String savepath = properties.getProperty("savePath");
		File savefile = new File(System.getenv("userprofile") + properties.getProperty("fileSeparator", "\\") + savepath);
		if (!savefile.exists()) {
			return ProfileCommandResult.newProfile;
		}
		savedprofiles = SaveFileUtils.readSavedFile(savefile);
		if(savedprofiles == null || savedprofiles.isEmpty())
			return ProfileCommandResult.newProfile;
		GameWorld selectedprofile = savedprofiles.get(playerName);
		if (selectedprofile != null && !selectedprofile.getPlayer().isWin()) {
			gameWorld = selectedprofile;
			return ProfileCommandResult.profileLoaded;
		}
		return ProfileCommandResult.profileNotSelected;
	}
	
	public ProfileCommandResult loadgame() {
		String savepath = properties.getProperty("savePath");
		File savefile = new File(System.getenv("userprofile") + properties.getProperty("fileSeparator", "\\") + savepath);
		if (!savefile.exists()) {
			try {
				selectProfile(new HashMap<>());
			} catch (CommandException e) {
				return handleProfileCommands(e);
			}
			return ProfileCommandResult.newProfile;
		}
		savedprofiles = SaveFileUtils.readSavedFile(savefile);
		if(savedprofiles == null || savedprofiles.isEmpty()) {
			try {
				selectProfile(new HashMap<>());
			} catch (CommandException e) {
				return handleProfileCommands(e);
			}
			return ProfileCommandResult.newProfile;
		}
		while(true) {
			try {
				GameWorld selectedprofile = selectProfile(savedprofiles);
				if (selectedprofile != null && !selectedprofile.getPlayer().isWin()) {
					gameWorld = selectedprofile;
					return ProfileCommandResult.profileLoaded;
				} else if(selectedprofile != null && selectedprofile.getPlayer().isWin()) {
					console.println("Mission Completed for this player, please select different player or create a new player");
				} else if(selectedprofile == null) {
					return ProfileCommandResult.newProfile;
				}
			} catch (CommandException e) {
				ProfileCommandResult commandResult = handleProfileCommands(e);
				if(commandResult != ProfileCommandResult.profileNotSelected) {
					return commandResult;
				}
			}
		}
	}

	

	private ProfileCommandResult handleProfileCommands(CommandException e) {
		if(e.getCommand().equals(":q"))
			return ProfileCommandResult.exitGame;
		else if(e.getCommand().startsWith(":d") && e.getCommand().split(" ").length == 2) {
			deleteProfile(e.getCommand().split(" ")[1]);
			return ProfileCommandResult.profileDeleted;
		} else {
			console.println("Invalid Command");
			return ProfileCommandResult.profileNotSelected;
		}
	}

	@Override
	public boolean savegame() {
		String savepath = properties.getProperty("savePath");
		File savefile = new File(System.getenv("userprofile") + properties.getProperty("fileSeparator", "\\") + savepath);

		if(savedprofiles == null) // save file does not exist
			savedprofiles = new HashMap<>();
		savedprofiles.put(gameWorld.getPlayer().getName(), gameWorld);
		return SaveFileUtils.writeSavedFile(savefile, savedprofiles);
	}
	
	private void deleteProfile(String playerName) {
		if(savedprofiles == null)
			savedprofiles = new HashMap<>();
		if(!savedprofiles.containsKey(playerName)) {
			errorconsole.println("Invalid Profile Name");
			return;
		}
		savedprofiles.remove(playerName);
		String savepath = properties.getProperty("savePath");
		File savefile = new File(System.getenv("userprofile") + properties.getProperty("fileSeparator", "\\") + savepath);
		boolean removed = SaveFileUtils.writeSavedFile(savefile, savedprofiles);
		if (removed)
			console.println("Profile " + playerName + " is removed");
	}
	
	private GameWorld selectProfile(Map<String, GameWorld> savedprofiles) throws CommandException {
		console.println("Select Profile: (enter 0 to create a new player)");
		console.println("Commands: \n :d  to delete profile + profileName, ex-> :d myProfile \n "
				+ ":q  to exit Game");
		int i = 0;
		for (Iterator<String> iterator = savedprofiles.keySet().iterator(); iterator.hasNext();i++) {
			String type = (String) iterator.next();
			console.print((i+1) + ": " + type + "\t");
		}
		console.println();
		
		int selected = ConsoleReaderUtils.readIntInput(userinputReader,
				"Invalid Input, please enter values from 0 to " + (savedprofiles.keySet().size()),
				(in) -> in >= 0 && in <= (savedprofiles.keySet().size())
				);
		if(selected == 0)
			return null;
		return (GameWorld) savedprofiles.get(savedprofiles.keySet().toArray()[selected-1]);
	}

	@Override
	public GameWorld createPlayer() {
		Player player = new Player();
		console.println("\n\ncreate a new player");
		
		console.print("Player Name: ");
		player.setName(ConsoleReaderUtils.readInput(userinputReader, (s) -> s.matches("[a-zA-Z]+"), "Invalid Name, Only letters allowed").trim());

		console.print("Age: ");
		int maxAge = Integer.parseInt(properties.getProperty("maxAge"));
		int minAge = Integer.parseInt(properties.getProperty("minAge"));
		player.setAge(ConsoleReaderUtils.readIntInput(userinputReader, (i) -> i >= minAge && i <= maxAge, "please enter valid number, from: " + minAge + " to: " + maxAge));

		
		console.print("Gender (Male / Female): ");
		String gender = ConsoleReaderUtils.readInput(userinputReader, (i) -> i.equals("Male") || i.equals("Female"), "please enter 'Male' or 'Female'");
		player.setGender(Player.Gender.valueOf(gender));
		
		player.setExperience(new Experience(Integer.parseInt(properties.getProperty("initPlayerLevel", "0")), 
				Integer.parseInt(properties.getProperty("initPlayerAttach", "0")), 
				Integer.parseInt(properties.getProperty("initPlayerDefence", "0"))));
		player.setLife(Integer.parseInt(properties.getProperty("lifes", "3")));
		
		GameLoader gameLoader = gameLoaderFactory.getInstance();
		List<Location> locations = gameLoader.getLocations();
		List<Character> villains = gameLoader.getVillains();
		
		gameWorld = GameServiceImpl.getInstance().setVillainsLocations(villains, locations);
		gameWorld.setPlayer(player);
		console.println("player created");
		return gameWorld;
	}
	
	@Override
	public GameWorld selectAllie() {
		console.println("\n\nSelect an Allie: ");
		if(gameWorld == null)
			gameWorld = new GameWorld(null);
		List<Character> allies = gameLoaderFactory.getInstance().getAllies();
		if(allies.size() == 0) {
			console.println("you are meant to be alone on this path");
			return gameWorld;
		}
		// print out all allies
		IntStream.range(0, allies.size()).forEach(i -> console.println((i+1) + ": " + allies.get(i) + "\t"));
		
		// get selected allie
		int selected = ConsoleReaderUtils.readIntInput(userinputReader,(in) -> in > 0 && in <= allies.size(), 
				"Invalid Input, please enter values from 1 to " + allies.size());
		gameWorld.setAllie(allies.get(selected - 1));
		console.println("you selected " + allies.get(selected - 1) + " to fight along with you");
		return gameWorld;
	}

	@Override
	public CommandResult playgame() {
		console.println("\n\nGame Started\tlet's fight " + gameWorld.getPlayer().getName());
		console.println("Commands: \n :s  to save game \n :sqp  to save and exit profile \n :qp to exit profile \n"
				+ " :d to delete profile \n"
				+ " :sq to save and exit Game \n :q  to exit Game without saving");
		
		while(true) {
			try {
				if(gameWorld.getWorld().isEmpty()) {
					console.println("Congrats, You Won!");
					gameWorld.getPlayer().setWin(true);
					savegame();
					return CommandResult.exitProfile;
				} else if(gameWorld.getPlayer().getCurrentLocation() == null) {
					Location selectedLocation = selectLocation();
					gameWorld.getPlayer().setCurrentLocation(selectedLocation);
				} else {
					Character villain = selectVillain(gameWorld.getPlayer().getCurrentLocation());
					if(villain == null) {
						gameWorld.getPlayer().setCurrentLocation(null);
						continue;
					}
					GameService gameService = GameServiceImpl.getInstance();
					boolean isWin = gameService.fight(gameWorld, villain);
					if(isWin) {
						gameService.killVillain(gameWorld, gameWorld.getPlayer().getCurrentLocation(), villain);
						gameService.gainExperience(gameWorld.getPlayer(), villain);
						console.println("You won the fight vs " + villain);
						console.println("Your level is: " + gameWorld.getPlayer().getExperience().getLevel() + "\n\n");
					} else if(gameWorld.getPlayer().getLife() <= 1) {
						console.println("You lost!");
						console.println("Do you want to return to last save point ? (Y/N)");
						String load = ConsoleReaderUtils.readInput(userinputReader, (s) -> s.equalsIgnoreCase("y") || s.equalsIgnoreCase("n"), "Invalid Input");
						if(load.equalsIgnoreCase("y")) {
							ProfileCommandResult loaded = loadgame(gameWorld.getPlayer().getName());
							if(loaded == ProfileCommandResult.profileLoaded)
								console.println("game loaded!");
							else {
								errorconsole.println("failed to load profile");
								return CommandResult.exitProfile;
							}
						} else if(load.equalsIgnoreCase("n"))
							return CommandResult.exitProfile;
					} else {
						console.println("You lost this fight, keep up for next one!");
						gameService.loseLife(gameWorld.getPlayer());
					}
				}
			} catch(CommandException e) {
				CommandResult commandResult = handleCommand(e);
				if(commandResult != CommandResult.continuePlay)
					return commandResult;
			}
		}
//		return CommandResult.exitGame;
	}

	private Character selectVillain(Location selectedLocation) throws CommandException {
		List<Character> villains = gameWorld.getWorld().get(selectedLocation);
		if(villains == null)
			return null;
		console.println("select villain to fight: (enter 0 to go back to world map)");
		if(villains.isEmpty()) {
			console.println("you have beaten this place");
			gameWorld.getWorld().remove(gameWorld.getPlayer().getCurrentLocation());
		} else {
			IntStream.range(0, villains.size()).forEach(i -> console.println((i+1) + ": " + villains.get(i) + "\t"));
		}
		int selected = ConsoleReaderUtils.readIntInput(userinputReader, 
				"Invalid Input, please enter" +
				(villains.size() != 0 ?  "values from 1 to " + villains.size() + " or " : "") + " 0 to go back to world map",
				(in) -> in >= 0 && in <= villains.size());
		return selected != 0 ? villains.get(selected-1) : null ;
	}

	

	private Location selectLocation() throws CommandException {
		console.println("Select Where to go: ");
		int i = 0;
		for (Iterator<Location> iterator = gameWorld.getWorld().keySet().iterator(); iterator.hasNext();i++) {
			Location type = (Location) iterator.next();
			console.print((i+1) + ": " + type + "\t");
		}
		console.println();
		int selected = ConsoleReaderUtils.readIntInput(userinputReader, 
				"Invalid Input, please enter values from 1 to " + (gameWorld.getWorld().keySet().size()),
				(in) -> in > 0 && in <= (gameWorld.getWorld().keySet().size()));
		return (Location) gameWorld.getWorld().keySet().toArray()[selected-1];
	}
	
	private CommandResult handleCommand(CommandException e) {
		switch(e.getCommand()) {
			case ":s":
				if(savegame())
					console.println("Game Saved!");
				return CommandResult.continuePlay;
			case ":q":
				return CommandResult.exitGame;
			case ":sq":
				if(savegame())
					console.println("Game Saved!");
				return CommandResult.exitGame;
			case ":sqp":
				if(savegame())
					console.println("Game Saved!");
				return CommandResult.exitProfile;
			case ":qp":
				return CommandResult.exitProfile;
			case ":d":
				deleteProfile(gameWorld.getPlayer().getName());
				return CommandResult.exitProfile;
			default:
				errorconsole.println("Invalid Command");
				return CommandResult.continuePlay;
		}
	}
}
