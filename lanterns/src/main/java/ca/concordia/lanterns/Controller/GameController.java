package ca.concordia.lanterns.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ca.concordia.lanterns.dao.impl.FileGameDao;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanterns.services.impl.EndGameDetectService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Allows user input using console, controls game flow.
 */
public class GameController {
	private Scanner keyboard;
	private Game game;

	// private static final MarshallerManager<Game> marshaller = new
	// JaxbGameMarshaller();

	public GameController() {
		keyboard = new Scanner(System.in);
	}

	public static void displayCurrentGameState(Game game) {
		// Writer writer = new StringWriter();
		// marshaller.marshall(game, writer);
		// System.out.println(writer.toString());

		// System.out.println(game);
		for (int i = 0; i < game.getPlayers().length; i++) {
			System.out.println("_______________________________");
			System.out.println("Player " + game.getPlayers()[i].getId() + ": " + game.getPlayers()[i].getName());

			System.out.println("Favor Tokens: " + game.getPlayers()[i].getFavors());
			dispPlayerLanterns(game, i);
			dispPlayerLakeTiles(game, i);
			dispPlayerDedications(game, i);
			System.out.println("The lake currently has the following tiles in it:");
			dispLake(game);
		}

		System.out.println("_______________________________");
	}

	private static void dispPlayerLanterns(Game game, final int playerID) {

		for (int i = 0; i < game.getCards().length; i++) {
			System.out.println("Lantern Card: " + game.getPlayers()[playerID].getCards()[i]);
		}
	}

	private static void dispPlayerLakeTiles(Game game, final int playerID) {

		for (int i = 0; i < game.getPlayers()[playerID].getTiles().size(); i++) {

			System.out.println("Lake Tile: " + i);
			int cardSides = game.getPlayers()[playerID].getTiles().get(i).getSides().length;
			for (int j = 0; j < cardSides; j++) {
				System.out.println("	" + game.getPlayers()[playerID].getTiles().get(i).getSides()[j]);
			}

			boolean tileHasPlatform = game.getPlayers()[playerID].getTiles().get(i).isPlatform();
			if (tileHasPlatform) {
				System.out.println("	Lake Tile " + i + " has a platform");
			} else {
				System.out.println("	Lake Tile " + i + " does not have a platform");
			}

		}

	}

	private static void dispLake(Game game) {

		for (int i = 0; i < game.getLake().size(); i++) {
			System.out.println(i + "=" + game.getLake().get(i));
		}
	}

	private static void dispPlayerDedications(Game game, final int playerID) {

		if (game.getPlayers()[playerID].getDedications().isEmpty()) {
			System.out.println(game.getPlayers()[playerID].getName() + " has no dedication tokens");
		} else {

			System.out.println("Dedications: " + game.getPlayers()[playerID].getDedications());

		}

	}

	public void init() {
		showMenu();

		while (true) {
			int userChoice = getValidInt("", 1, 3);
			menuSelection(userChoice);
		}
	}

	private void showMenu() {
		System.out.println("GROUP A SOEN 6441 LANTERNS BUILD 2");
		System.out.println("Select the following: ");
		System.out.println("1) Start a new game");
		System.out.println("2) Load game from file");
		System.out.println("3) Quit");
	}

	private void menuSelection(final int userChoice) {
		switch (userChoice) {
		case 1:
			startGame();
			break;
		case 2:
			loadGame();
			break;
		case 3:
			quit();
		default:
			System.out.println("Invalid Input, please try again.");
		}
	}

	private void startGame() {
		int numberOfPlayers = getValidInt("Specify the number of players (2-4)", 2, 4);
		String[] playerNames = new String[numberOfPlayers];
		String[] hardcodeNames = new String[] { "Alpha", "Bravo", "Charlie", "Delta" };
		for (int i = 0; i < numberOfPlayers; i++) {
			playerNames[i] = hardcodeNames[i];
		}

		game = DefaultSetupService.getInstance().createGame(playerNames);
		displayCurrentGameState(game);
		System.out.println("Successfully Initialized game");

		gameSelection();
	}

	private void gameSelection() {
		int newGameOption = getValidInt("Select one option:\n1) Play\n2) Save Game\n3) Quit", 1, 3);

		switch (newGameOption) {
		case 1:
			if (game != null) {
				play();
			}
			break;
		case 2:
			if (game != null) {
				String saveFileName = getValidString("Specify the name of the save file with the extension (.xml)");

				FileGameDao write = new FileGameDao();
				write.saveGame(saveFileName, game);
				System.out.println("Saved game, quitting.");
				quit();
			}
			break;
		case 3:
			quit();
			return;
		default:
			System.out.println("Unsuported Option, now exiting.");
			quit();
			return;
		}
	}

	private void loadGame() {
		String loadFileName = getValidString("Specify the name of the load file with the extension (.xml)");

		FileGameDao read = new FileGameDao();
		game = read.loadGame(loadFileName);

		displayCurrentGameState(game);
		System.out.println("Successfully loaded game");

		gameSelection();
	}

	private void quit() {
		System.out.println("So long, farewell, auf Wiedersehen, goodbye!");
		keyboard.close();
		System.exit(0);
	}

	private void play() {
		boolean isEnded = EndGameDetectService.getInstance().isGameEnded(game);
		while (!isEnded) {
			int currentIndex = game.getCurrentTurnPlayer();
			Player currentPlayer = game.getPlayers()[currentIndex];
			playTurn(currentPlayer);
			game.setCurrentTurnPlayer(game.getNextPlayer());
		}
		// when game is ended, show the winner
		try {
			Set<Player> winners = EndGameDetectService.getInstance().getGameWinner(game);
			showWinner(winners);
		} catch (GameRuleViolationException e) {
			System.err.println(e.getMessage());
		}
	}

	private void playTurn(Player currentPlayer) {
		System.out.println("_______________________________");
		System.out.println("It is player '" + currentPlayer.getName() + "'s turn.");

		exchangeLantern(currentPlayer);
		makeDedication(currentPlayer);
		placeTile(currentPlayer);

		System.out.println("Player '" + currentPlayer.getName() + "'s turn is finished.");
		System.out.println("Game state after this player turn:");
		displayCurrentGameState(game);
	}

	private void exchangeLantern(Player currentPlayer) {
		System.out.println("You have [" + currentPlayer.getFavors() + "] favors.");
		// System.out.println("Here are your lanterns:" +
		// Arrays.toString(currentPlayer.getCards()));

		int doExchange = 2;
		if (currentPlayer.getFavors() > 1) {
			dispPlayerLanterns(game, currentPlayer.getId());
			doExchange = getValidInt("Do you want to make an exchange?\n1) Yes\n2) No", 1, 2);
		} else {
			System.out.println("You do not have enough favour tokens to make an exchange this turn.");
		}

		if (doExchange == 1) {
			System.out.println("Select one card to give:");
			int giveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
			Colour giveCard = Colour.values()[giveCardIndex];
			System.out.println("Select one card to receive:");
			int receiveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
			Colour receiveCard = Colour.values()[receiveCardIndex];
			try {
				ActivePlayerService.getInstance().exchangeLanternCard(game, currentPlayer.getId(), giveCard,
						receiveCard);
			} catch (GameRuleViolationException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private void makeDedication(Player currentPlayer) {
		// System.out.println("Here are your lanterns:" +
		// Arrays.toString(currentPlayer.getCards()));
		dispPlayerLanterns(game, currentPlayer.getId());

		int doDedication = getValidInt("Do you want to make a dedication?\n1) Yes\n2) No", 1, 2);

		if (doDedication == 1) {
			System.out.println("Select one dedication type:");
			int typeIndex = getValidInt(dedicationTypesString(), 0, 3);
			DedicationType type = DedicationType.values()[typeIndex];
			DedicationCost cost = ActivePlayerService.getInstance().getDedicationCost(type);
			Colour[] colours = new Colour[cost.getRequiredColors()];
			for (int i = 0; i < colours.length; i++) {
				System.out.println("Select one colour:");
				int giveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
				colours[i] = Colour.values()[giveCardIndex];
			}
			try {
				ActivePlayerService.getInstance().makeDedication(game, currentPlayer.getId(), type, colours);
			} catch (GameRuleViolationException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private void placeTile(Player currentPlayer) {
		// active player must place a tile
		Boolean playerNotDone = true;
		while (playerNotDone) {
			
			System.out.println("Now it is time to place a tile.");
			List<LakeTile> playerTiles = currentPlayer.getTiles();
			dispPlayerLakeTiles(game, currentPlayer.getId());
			int playerTileIndex = getValidInt("Select one of your tiles:", 0, playerTiles.size() - 1);

			System.out.println("Select one of the lake tiles to put your tile next to:");
			dispLake(game);
			List<LakeTile> lakeTiles = game.getLake();
			int existingTileIndex = getValidInt("", 0, lakeTiles.size() - 1);

			System.out.println("Select the side of the lake tiles to put your tile next to:");
			LakeTile lakeTile = lakeTiles.get(existingTileIndex);
			int existingTileSideIndex = getValidInt(getTileSidesString(lakeTile), 0, lakeTile.getSides().length - 1);

			System.out.println("Select the side your tile to put next to the lake tile:");
			LakeTile playerTile = playerTiles.get(playerTileIndex);
			int playerTileSideIndex = getValidInt(getTileSidesString(playerTile), 0, playerTile.getSides().length - 1);

			try {
				ActivePlayerService.getInstance().placeLakeTile(game, currentPlayer.getId(), playerTileIndex,
						existingTileIndex, existingTileSideIndex, playerTileSideIndex);
				playerNotDone = false;
			} catch (GameRuleViolationException e) {
				System.err.println(e.getMessage());
				System.out.println();
				System.out.println("Please reselect your tile and try again.");
			}
		}
	}

	private void showWinner(Set<Player> winners) {
		System.out.println("Game is ended!");

		System.out.println("Points for each player");
		for (Player player : game.getPlayers()) {
			int sumDedications = 0;
			for (DedicationToken dedicationToken : player.getDedications()) {
				sumDedications += dedicationToken.getTokenValue();
			}
			int sumCards = 0;
			for (LanternCardWrapper card : player.getCards()) {
				sumCards += card.getQuantity();
			}
			System.out.println("Player [" + player.getName() + "]:\tdedications=" + sumDedications + "\tfavors="
					+ player.getFavors() + "\tlanters=" + sumCards);
		}

		if (winners.size() > 1) {
			System.out.println("Game was a TIE!!!");
		}
		for (Player winner : winners) {
			System.out.println("Congratulations player [" + winner.getName() + "]! You are the winner!!!");
		}
	}

	private String coloursWithIndexesString() {
		StringBuffer sb = new StringBuffer();
		for (Colour colour : Colour.values()) {
			sb.append(colour.ordinal() + "=" + colour + "; ");
		}
		return sb.toString();
	}

	private String dedicationTypesString() {
		StringBuffer sb = new StringBuffer();
		for (DedicationType type : DedicationType.values()) {
			sb.append(type.ordinal() + "=" + type + "; ");
		}
		return sb.toString();
	}

	private String getTilesString(List<LakeTile> tiles) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < tiles.size(); i++) {
			sb.append(i + "=" + tiles.get(i) + "; ");
		}
		return sb.toString();
	}

	private String getTileSidesString(LakeTile tile) {
		StringBuffer sb = new StringBuffer();

		TileSide[] sides = tile.getSides();

		for (int i = 0; i < sides.length; i++) {
			sb.append(i + "=" + sides[i] + "; ");
		}
		return sb.toString();
	}

	private int getValidInt(final String message, final int min, final int max) {
		System.out.print(message);
		int userChoice = 0;
		boolean valid = false;
		while (!valid) {
			try {
				userChoice = keyboard.nextInt();
				if (userChoice >= min && userChoice <= max) {
					valid = true;
				}
			} catch (Exception e) {
				keyboard.nextLine();
			}
			if (!valid) {
				System.out.println("Invalid input. Please enter an integer between " + min + "-" + max);
			}
		}
		System.out.println(userChoice);
		return userChoice;
	}

	private String getValidString(final String message) {
		System.out.print(message);
		String value = null;
		boolean valid = false;
		while (!valid) {
			value = keyboard.next();
			if (value != null && value.length() > 0) {
				valid = true;
			} else {
				System.out.println("Invalid input. Please enter a valid String: ");
			}
		}
		System.out.println(value);
		return value;
	}

	public static void main(String[] args) {
		GameController game = new GameController();
		game.init();
	}
}
