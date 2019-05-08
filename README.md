
# Game-puzzle


Game-puzzle is a CLI game

first step is to create a profile or select an existing profile, then the game will start, you can go to places and fight villains
when you win a fight your level will up based on the strength of the villain you won.

After starting the game you can also save the game or exit current profile or exit game.

### Architecture

* GameLoader: is responsible of loading data and also the theme of the game
* GameService: is responsible of the game logic related functionality
* GameController: is responsible of control the flow of the game
* Model package: dto of the game

![Image of design](https://raw.githubusercontent.com/motazEmad/game-puzzle/master/design.png)

### Build
using maven build tool
```sh
$ cd game-puzzle
$ mvn clean install
```

### Run Application
run the generated uber jar file

```sh
$ java -jar target\game-puzzle-1.0-jar-with-dependencies.jar
```

### Configuration 
config.properties contains configuration like <br>

- minimum and maximum player's age <br>
- lifes of the players<br>
- experience that the player start the game with<br>
- save life path separator 

### Todos

-Connect players over network <br>
-Provide different controller implementation for example web controller implementation<br>
-Develop localization <br>
-Use Lombok library <br>
-User logger to log exceptions<br>
-If save file modified by other resource, show warning to override modification<br>
