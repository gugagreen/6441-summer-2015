package ca.concordia.lanterns.Controller;

import java.util.Arrays;
import java.util.Scanner;

import ca.concordia.lanterns.dao.impl.FileGameDao;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class GameController {
	private Scanner keyboard;
	private Game game;
	
//	private static final MarshallerManager<Game> marshaller = new JaxbGameMarshaller();
	
	public GameController() {
		keyboard = new Scanner(System.in);
	}

	public static void displayCurrentGameState(Game game) {
//		Writer writer = new StringWriter();
//		marshaller.marshall(game, writer);
//		System.out.println(writer.toString());
		
		System.out.println(game);
	}
	
	public void init() {
		showMenu();
		
		while (true) {
			int userChoice =  getValidInt("", 1, 3);
			menuSelection(userChoice);
		}
	}
	
	private void showMenu() {
		System.out.println("GROUP A SOEN 6441 LANTERNS BUILD 1");
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
		for (int i = 0; i < numberOfPlayers; i++) {
			playerNames[i] = Integer.toString(i);
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
		boolean isNotEnded = true; // FIXME - check if from service
		while(isNotEnded) {
			int currentIndex = game.getCurrentTurnPlayer();
			Player currentPlayer = game.getPlayers()[currentIndex];
			playTurn(currentPlayer);
			game.setCurrentTurnPlayer(game.getNextPlayer());
		}
	}
	
	private void playTurn(Player currentPlayer) {
		System.out.println("It is player '" + currentPlayer.getName() + "' turn.");
		
		exchangeLantern(currentPlayer);
		makeDedication(currentPlayer);
		// TODO - place a tile
		
		System.out.println("Player '" + currentPlayer.getName() + "' turn is finished.");
	}
	
	private void exchangeLantern(Player currentPlayer) {
		System.out.println("You have [" + currentPlayer.getFavors() + "] favors.");
		System.out.println("Here are your lanterns:" + Arrays.toString(currentPlayer.getCards()));
		
		int doExchange = getValidInt("Do you want to make an exchange?\n1) Yes\n2) No", 1, 2);
		
		if (doExchange == 1) {
			System.out.println("Select one card to give:");
			int giveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
			Colour giveCard = Colour.values()[giveCardIndex];
			System.out.println("Select one card to receive:");
			int receiveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
			Colour receiveCard = Colour.values()[receiveCardIndex];
			try {
				ActivePlayerService.getInstance().exchangeLanternCard(game, currentPlayer.getId(), giveCard, receiveCard);
			} catch (GameRuleViolationException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private void makeDedication(Player currentPlayer)  {
		System.out.println("Here are your lanterns:" + Arrays.toString(currentPlayer.getCards()));
		
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
			if(value != null && value.length() > 0) {
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
