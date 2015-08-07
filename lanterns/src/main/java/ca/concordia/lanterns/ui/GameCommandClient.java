package ca.concordia.lanterns.ui;

import ca.concordia.lanterns.Controller.GameController;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanternsentities.*;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Serves as client for {@link GameController}. Acts using command line input.
 */
public class GameCommandClient {

    private Scanner keyboard;
    private Game game;

    private GameController controller;

    public GameCommandClient() {
        keyboard = new Scanner(System.in);
        controller = new GameController();
    }

    public static void displayCurrentGameState(Game game) {
        for (int i = 0; i < game.getPlayers().length; i++) {
            System.out.println("_______________________________");
            System.out.println("Player " + game.getPlayers()[i].getId() + ": " + game.getPlayers()[i].getName());

            System.out.println("Favor Tokens: " + game.getPlayers()[i].getFavors());
            displayPlayerLanterns(game, i);
            displayPlayerLakeTiles(game, i);
            displayPlayerDedications(game, i);
        }

        //System.out.println("_______________________________");

        System.out.println("###############################");
        System.out.println("The Game Board contains: \n");

        System.out.println("Lantern Cards:");
        for (int i = 0; i < game.getCards().length; i++) {
            System.out.print(game.getCards()[i] + "\t");
        }
        System.out.println();

        System.out.println("\nDedication Cards: ");
        for (int i = 0; i < game.getDedications().length; i++) {
            System.out.println(game.getDedications()[i]);
        }
        System.out.println("\nFavor Tokens: " + game.getFavors());

        System.out.println("\nThe lake currently has the following tiles in it:");
        displayLake(game);

        System.out.println("###############################");
    }

    private static void displayPlayerLanterns(Game game, final int playerID) {
    	System.out.print("Lantern Cards:");
        for (int i = 0; i < game.getCards().length; i++) {
            System.out.print("\t" + game.getPlayers()[playerID].getCards()[i]);
        }
        System.out.println();
    }

    private static void displayPlayerLakeTiles(Game game, final int playerID) {
    	List<LakeTile> playerTiles = game.getPlayers()[playerID].getTiles();
        for (int i = 0; i < playerTiles.size(); i++) {
            System.out.println("Lake Tile: " + i);
            LakeTile tile = playerTiles.get(i);
            String[] lines = LakeTile.get3Lines(tile);
            for(String line : lines) {
            	System.out.println(line);
            }
        }

    }

    private static void displayLake(Game game) {
        LakeTile[][] matrix = game.getLake();
        
        StringBuffer[] lines1 = new StringBuffer[matrix.length];
		StringBuffer[] lines2 = new StringBuffer[matrix.length];
		StringBuffer[] lines3 = new StringBuffer[matrix.length];
		
		for (int i = 0; i < matrix.length; i++) {
			lines1[i] = new StringBuffer();
			lines2[i] = new StringBuffer();
			lines3[i] = new StringBuffer();

			for (int j = 0; j < matrix[i].length; j++) {
				String[] tileLines = LakeTile.get3Lines(matrix[i][j]);
				lines1[i].append(tileLines[0]);
				lines2[i].append(tileLines[1]);
				lines3[i].append(tileLines[2]);
			}
		}

		for (int i = 0; i < matrix.length; i++) {
			System.out.println(lines1[i]);
			System.out.println(lines2[i]);
			System.out.println(lines3[i]);
			System.out.println();
		}
		
		System.out.println("ids");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != null) {
					System.out.println(matrix[i][j].getId());
				}
			}
		}
    }

    private static void displayPlayerDedications(Game game, final int playerID) {
        if (game.getPlayers()[playerID].getDedications().isEmpty()) {
            System.out.println(game.getPlayers()[playerID].getName() + " has no dedication tokens");
        } else {
            System.out.println("Dedications: " + game.getPlayers()[playerID].getDedications());
        }
    }

    public static void main(String[] args) {
        GameCommandClient client = new GameCommandClient();
        client.init();
    }

    public void init() {
        showMenu();

        while (true) {
            int userChoice = getValidInt("", 1, 4);
            menuSelection(userChoice);
        }
    }

    private void showMenu() {
        System.out.println("GROUP A SOEN 6441 LANTERNS BUILD 2");
        System.out.println("Select the following: ");
        System.out.println("1) Start a new game");
        System.out.println("2) Load game from file");
        System.out.println("3) Load game from file and validate that file");
        System.out.println("4) Quit");

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
                loadValidatedGame();
            case 4:
                quit();
            default:
                System.out.println("Invalid Input, please try again.");
        }
    }

    private void startGame() {
        int numberOfPlayers = getValidInt("Specify the number of players (2-4)", 2, 4);
        String[] playerNames = new String[numberOfPlayers];
        String[] hardcodeNames = new String[]{"Alpha", "Bravo", "Charlie", "Delta"};
        for (int i = 0; i < numberOfPlayers; i++) {
            playerNames[i] = hardcodeNames[i];
        }

        game = controller.createGame(playerNames);

        displayCurrentGameState(game);
        System.out.println("Successfully Initialized game");

        gameSelection();
    }

    private void loadGame() {
        String loadFileName = getValidString("Specify the name of the load file with the extension (.xml)");

        game = controller.loadGame(loadFileName);

        displayCurrentGameState(game);
        System.out.println("Successfully loaded game");

        gameSelection();
    }

    private void loadValidatedGame() {
        String loadFileName = getValidString("Specify the name of the load file with the extension (.xml)");

        game = controller.loadValidatedGame(loadFileName);

        displayCurrentGameState(game);
        System.out.println("Successfully loaded game");

        gameSelection();
    }

    private void quit() {
        System.out.println("So long, farewell, auf Wiedersehen, goodbye!");
        keyboard.close();
        System.exit(0);
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

                    controller.saveGame(game, saveFileName);

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

    public void play() {
        boolean isEnded = controller.isGameEnded(game);
        while (!isEnded) {
            int currentIndex = game.getCurrentTurnPlayer();
            Player currentPlayer = game.getPlayers()[currentIndex];
            playTurn(currentPlayer);
            game.setCurrentTurnPlayer(game.getNextPlayer());
            isEnded = controller.isGameEnded(game);
        }

        //after isEnded returns true there is one more turn to play for exchanges
        for (int i = 0; i < game.getPlayers().length; i++) {

            int currentIndex = game.getCurrentTurnPlayer();
            Player currentPlayer = game.getPlayers()[currentIndex];
            playTurn(currentPlayer);
            game.setCurrentTurnPlayer(game.getNextPlayer());

        }

        // when game is ended, show the winner
        try {
            Set<Player> winners = controller.getGameWinner(game);
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
        if (game.getPlayers()[currentPlayer.getId()].getTiles().size() != 0) {
            placeTile(currentPlayer);
        }
        System.out.println("Player '" + currentPlayer.getName() + "'s turn is finished.");
        System.out.println("Game state after this player turn:");
        displayCurrentGameState(game);
    }

    private void exchangeLantern(Player currentPlayer) {
        System.out.println("You have [" + currentPlayer.getFavors() + "] favors.");

        int doExchange = 2;
        if (currentPlayer.getFavors() > 1) {
            displayPlayerLanterns(game, currentPlayer.getId());
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
                controller.exchangeLanternCard(game, currentPlayer.getId(), giveCard, receiveCard);
            } catch (GameRuleViolationException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void makeDedication(Player currentPlayer) {
        displayPlayerLanterns(game, currentPlayer.getId());

        int doDedication = getValidInt("Do you want to make a dedication?\n1) Yes\n2) No", 1, 2);

        if (doDedication == 1) {
            System.out.println("Select one dedication type:");
            int typeIndex = getValidInt(dedicationTypesString(), 0, 3);
            DedicationType type = DedicationType.values()[typeIndex];
            int requiredColours = controller.getRequiredColors(type);
            Colour[] colours = new Colour[requiredColours];
            for (int i = 0; i < colours.length; i++) {
                System.out.println("Select one colour:");
                int giveCardIndex = getValidInt(coloursWithIndexesString(), 0, 6);
                colours[i] = Colour.values()[giveCardIndex];
            }
            try {
                controller.makeDedication(game, currentPlayer.getId(), type, colours);
                displayPlayerDedications(game, currentPlayer.getId());
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
            displayPlayerLakeTiles(game, currentPlayer.getId());
            int playerTileIndex = getValidInt("Select one of your tiles:", 0, playerTiles.size() - 1);

            System.out.println("Select one of the lake tiles to put your tile next to:");
            displayLake(game);
            
            LakeTile lakeTile = null;
            while (lakeTile == null) {
            	String lakeTileId = getValidString("");
            	lakeTile = MatrixOrganizer.findTile(game.getLake(), lakeTileId);
            	if (lakeTile == null) {
            		System.out.println("Invalid tile id. Please try again:");
            	}
            }

            System.out.println("Select the side of the lake tiles to put your tile next to:");
            int existingTileSideIndex = getValidInt(getTileSidesString(lakeTile), 0, lakeTile.getSides().length - 1);

            System.out.println("Select the side your tile to put next to the lake tile:");
            LakeTile playerTile = playerTiles.get(playerTileIndex);
            int playerTileSideIndex = getValidInt(getTileSidesString(playerTile), 0, playerTile.getSides().length - 1);

            try {
                controller.placeLakeTile(game, currentPlayer.getId(), playerTileIndex,
                		lakeTile.getId(), existingTileSideIndex, playerTileSideIndex);
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
            return;
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

    private String getTileSidesString(LakeTile tile) {
        StringBuffer sb = new StringBuffer();

        TileSide[] sides = tile.getSides();

        for (int i = 0; i < sides.length; i++) {
            sb.append(i + "=" + sides[i] + "; ");
        }
        return sb.toString();
    }

    public int getValidInt(final String message, final int min, final int max) {
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

    public String getValidString(final String message) {
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

}
