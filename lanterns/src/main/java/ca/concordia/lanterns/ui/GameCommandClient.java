package ca.concordia.lanterns.ui;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ca.concordia.lanterns.ai.AI;
import ca.concordia.lanterns.ai.impl.GreedyAI;
import ca.concordia.lanterns.ai.impl.HumanPlayer;
import ca.concordia.lanterns.ai.impl.RandomAI;
import ca.concordia.lanterns.ai.impl.UnfriendlyAI;
import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.impl.EndGameDetectService;
import ca.concordia.lanterns.services.strategies.NHonorPointsEndGameStrategy;
import ca.concordia.lanterns.services.strategies.NLakeTilesEndGameStrategy;
import ca.concordia.lanterns.services.strategies.NormalEndGameStrategy;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Serves as client for {@link GameController}. Acts using command line input.
 */
public class GameCommandClient {

    private static Scanner keyboard;
    private Game game;
    private AI[] playerIntelligence;
    private GameController controller;

    public static void main(String[] args) {
        GameCommandClient client = new GameCommandClient();
        client.init();
    }

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

    public static void displayPlayerLanterns(Game game, final int playerID) {
    	System.out.print("Lantern Cards:");
        for (int i = 0; i < game.getCards().length; i++) {
            System.out.print("\t" + game.getPlayers()[playerID].getCards()[i]);
        }
        System.out.println();
    }

    public static void displayPlayerLakeTiles(Game game, final int playerID) {
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

    public static void displayLake(Game game) {
        LakeTile[][] matrix = game.getLake();
        StringBuffer[] lines1 = new StringBuffer[matrix.length];
		StringBuffer[] lines2 = new StringBuffer[matrix.length];
		StringBuffer[] lines3 = new StringBuffer[matrix.length];
		
		for (int i = 0; i < matrix.length; i++) {
			lines1[i] = new StringBuffer();
			lines2[i] = new StringBuffer();
			lines3[i] = new StringBuffer();

			for (int j = 0; j < matrix[i].length; j++) {
				if (j == 0) {
					lines1[i].append("\t");
					lines2[i].append(i + "\t");
					lines3[i].append("\t");
				}
				
				String[] tileLines = LakeTile.get3Lines(matrix[i][j]);
				lines1[i].append(tileLines[0]);
				lines2[i].append(tileLines[1]);
				lines3[i].append(tileLines[2]);
			}
		}
		
		if (matrix.length > 0) {
			System.out.print("\t");
			for (int i = 0; i < matrix[0].length; i++) {
				System.out.printf("%4d   ", i);
			}
			System.out.println();
		}
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(lines1[i]);
			System.out.println(lines2[i]);
			System.out.println(lines3[i]);
			System.out.println();
		}
    }

    private static void displayPlayerDedications(Game game, final int playerID) {
        if (game.getPlayers()[playerID].getDedications().isEmpty()) {
            System.out.println(game.getPlayers()[playerID].getName() + " has no dedication tokens");
        } else {
            System.out.println("Dedications: " + game.getPlayers()[playerID].getDedications());
        }
    }

    public void init() {
        showMenu();

        while (true) {
            int userChoice = getValidInt("", 1, 4);
            menuSelection(userChoice);
        }
    }

    private void showMenu() {
        System.out.println("GROUP A SOEN 6441 LANTERNS BUILD 3");
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
        // FIXME - set player intelligence into game
        setPlayerIntelligence(numberOfPlayers);

        displayCurrentGameState(game);
        System.out.println("Successfully Initialized game");

        gameSelection();
    }
    
    private void setPlayerIntelligence(int numberOfPlayers){
        playerIntelligence = new AI[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
        	System.out.println("For player " +game.getPlayers()[i].getName());
        	String selectAI = "Specify the Behavior you desire:"
        			+ "\n1) Human Player"
        			+ "\n2) Random AI"
        			+ "\n3) Greedy AI ";
        	//TODO modify as more AI options become available
        	// + "\n4) Unfriendly AI"
        	// + "\n5) Unknown AI"
        	//TODO modify end bound as more AI options become available
        	int playerChoice = getValidInt(selectAI, 1, 3);
        	
        	
            switch (playerChoice) {
	            case 1:
	            	playerIntelligence[i] = new HumanPlayer(game, game.getPlayers()[i]);
	                break;
	            case 2:
	            	playerIntelligence[i] = new RandomAI(game, game.getPlayers()[i]);
	                break;
	            case 3:
	            	playerIntelligence[i] = new GreedyAI(game, game.getPlayers()[i]);
	            	break;
	            case 4:
	            	playerIntelligence[i] = new UnfriendlyAI(game, game.getPlayers()[i]);
	            	break;
	            default:
	                System.out.println("Invalid Input, please try again.");
            }
			
        }
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

        playerIntelligence[currentPlayer.getId()].performExchange();
        playerIntelligence[currentPlayer.getId()].performDedication();
        if (game.getPlayers()[currentPlayer.getId()].getTiles().size() != 0) {
        	playerIntelligence[currentPlayer.getId()].performTilePlay();
        }
        System.out.println("Player '" + currentPlayer.getName() + "'s turn is finished.");
        System.out.println("Game state after this player turn:");
        displayCurrentGameState(game);
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

	public static String dedicationTypesString() {
		StringBuffer sb = new StringBuffer();
		for (DedicationType type : DedicationType.values()) {
			sb.append(type.ordinal() + "=" + type + "; ");
		}
		return sb.toString();
	}

	public static String coloursWithIndexesString() {
		StringBuffer sb = new StringBuffer();
		for (Colour colour : Colour.values()) {
			sb.append(colour.ordinal() + "=" + colour + "; ");
		}
		return sb.toString();
	}
    
	public static String getTileSidesString(LakeTile tile) {
        StringBuffer sb = new StringBuffer();

        TileSide[] sides = tile.getSides();

        for (int i = 0; i < sides.length; i++) {
            sb.append(i + "=" + sides[i] + "; ");
        }
        return sb.toString();
    }

    public static int getValidInt(final String message, final int min, final int max) {
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

    public static String getValidString(final String message) {
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

    private void setEndGameStrategy()
    {
        System.out.println("Please select an end game strategy");
        System.out.println("1) The normal way");
        System.out.println("2) N Lake Tiles placed on the board strategy");
        System.out.println("3) N Honor points earned strategy");

        int userChoice = keyboard.nextInt();
        int nLakeTiles = 0;
        int nHonorPoint = 0;

        switch (userChoice)
        {
            case 1:
                EndGameDetectService.getInstance().setEndGameStrategy(new NormalEndGameStrategy());
                break;
            case 2:
                nLakeTiles = getValidInt("Please enter the value N", 2, game.getTiles().size()/game.getPlayers().length);
                EndGameDetectService.getInstance().setEndGameStrategy(new NLakeTilesEndGameStrategy(nLakeTiles));
                break;
            case 3:
                nHonorPoint = getValidInt("Please enter the value N", 4, sumDedicationValues()/game.getPlayers().length);
                EndGameDetectService.getInstance().setEndGameStrategy(new NHonorPointsEndGameStrategy(nHonorPoint));
                break;
        }

    }

    private int sumDedicationValues()
    {
        int sum = 0;

        for(DedicationTokenWrapper dedicationTokenWrapper : game.getDedications())
           for(DedicationToken dedicationToken : dedicationTokenWrapper.getStack())
               sum += dedicationToken.getTokenValue();

        return sum;
    }

}
