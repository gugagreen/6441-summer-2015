package ca.concordia.lanterns.controllers;

import static ca.concordia.lanterns.ui.GameCommandClient.getPlayerIntelligences;
import static ca.concordia.lanterns.ui.GameCommandClient.getValidInt;

import java.util.Scanner;
import java.util.Set;

import ca.concordia.lanterns.ai.impl.GreedyAI;
import ca.concordia.lanterns.ai.impl.HumanPlayer;
import ca.concordia.lanterns.ai.impl.RandomAI;
import ca.concordia.lanterns.ai.impl.UnfriendlyAI;
import ca.concordia.lanterns.ai.impl.UnpredictableAI;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.services.impl.DefaultGameCacheService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanterns.services.impl.EndGameDetectService;
import ca.concordia.lanterns.services.strategies.NHonorPointsEndGameStrategy;
import ca.concordia.lanterns.services.strategies.NLakeTilesEndGameStrategy;
import ca.concordia.lanterns.services.strategies.NormalEndGameStrategy;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.AIType;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Basic game controller that is responsible for accessing game services.
 */
public class GameController {

    public Game createGame(String[] playerNames, AIType[] aiTypes) {
        return DefaultSetupService.getInstance().createGame(playerNames, aiTypes);
    }

    public Game loadGame(String loadFileName) {
        return DefaultGameCacheService.getInstance().loadGame(loadFileName);
    }

    public void saveGame(Game game, String saveFileName) {
        DefaultGameCacheService.getInstance().saveGame(saveFileName, game);
    }

    public boolean isGameEnded(Game game) {
        return EndGameDetectService.getInstance().isGameEnded(game);
    }

    public Set<Player> getGameWinner(Game game) {
        return EndGameDetectService.getInstance().getGameWinner(game);
    }

    public void exchangeLanternCard(Game game, int id, Colour giveCard, Colour receiveCard) throws GameRuleViolationException {
        ActivePlayerService.getInstance().exchangeLanternCard(game, id, giveCard, receiveCard);
    }

    public DedicationCost getDedicationCost(DedicationType dedicationType) throws GameRuleViolationException {
        return ActivePlayerService.getInstance().getDedicationCost(dedicationType);
    }

    public int getRequiredColors(DedicationType dedicationType) {
        DedicationCost cost = ActivePlayerService.getInstance().getDedicationCost(dedicationType);
        ;
        return cost.getRequiredColors();
    }

    public void makeDedication(Game game, int id, DedicationType dedicationType, Colour[] colours)
            throws GameRuleViolationException {
        ActivePlayerService.getInstance().makeDedication(game, id, dedicationType, colours);
    }

    public void placeLakeTile(Game game, int id, int playerTileIndex, String lakeTileId, int existingTileSideIndex,
                              int playerTileSideIndex) throws GameRuleViolationException {
        ActivePlayerService.getInstance().placeLakeTile(game, id, playerTileIndex, lakeTileId, existingTileSideIndex,
                playerTileSideIndex);
    }
    
    /**
     * Sets the end of game strategy.
     *
     * @param {@link Game} object.
     */
    public void setEndGameStrategy(Game game)
    {
        System.out.println("Please select an end game strategy [Integer 1-3 only]");
        System.out.println("1) The normal way");
        System.out.println("2) N Lake Tiles placed on the board strategy");
        System.out.println("3) N Honor points earned strategy");

        int userChoice = 0;
        int nLakeTiles = 0;
        int nHonorPoint = 0;
        Scanner keyboard = new Scanner(System.in);

        while (userChoice < 1 || userChoice > 3)
            userChoice = keyboard.nextInt();

        switch (userChoice)
        {
            case 1:
                EndGameDetectService.getInstance().setEndGameStrategy(new NormalEndGameStrategy());
                break;
            case 2:
                nLakeTiles = getValidInt("Please enter the value N:", 2, game.getTiles().size()/game.getPlayers().length);
                EndGameDetectService.getInstance().setEndGameStrategy(new NLakeTilesEndGameStrategy(nLakeTiles));
                break;
            case 3:
                nHonorPoint = getValidInt("Please enter the value N:", 4, sumDedicationValues(game)/game.getPlayers().length);
                EndGameDetectService.getInstance().setEndGameStrategy(new NHonorPointsEndGameStrategy(nHonorPoint));
                break;
        }
    }
    
    /**
     * It resets the players intelligence(AI).
     *
     * @param {@link Game} object.
     */
    
    public void reSetPlayersAI(Game game)
    {
        String[] playerNames = new String[game.getPlayers().length];

        for (int i = 0; i < playerNames.length; i++) {
            playerNames[i] = game.getPlayers()[i].getName();
        }

        AIType[] aiTypes = getPlayerIntelligences(playerNames);

        for (int i = 0; i < aiTypes.length; i++) {

            switch (aiTypes[i]) {
                case HUMAN:
                    game.getAiPlayers()[i] = new HumanPlayer(game, game.getAiPlayers()[i].getPlayer());
                    break;
                case RANDOM:
                    game.getAiPlayers()[i] = new RandomAI(game, game.getAiPlayers()[i].getPlayer());
                    break;
                case GREEDY:
                    game.getAiPlayers()[i] = new GreedyAI(game, game.getAiPlayers()[i].getPlayer());
                    break;
                case UNFRIENDLY:
                    game.getAiPlayers()[i] = new UnfriendlyAI(game, game.getAiPlayers()[i].getPlayer());
                    break;
                case UNPREDICTABLE:
                    game.getAiPlayers()[i] = new UnpredictableAI(game, game.getAiPlayers()[i].getPlayer());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid AIType [" + aiTypes[i] + "]");
            }
        }
    }
    
    /**
     * Loads a {@link Game} object from a given resource.
     *
     * @param The {@link Game} object.
     * @return Sum of dedication tokens/values.
     */
    private int sumDedicationValues(Game game)
    {
        int sum = 0;

        for(DedicationTokenWrapper dedicationTokenWrapper : game.getDedications())
            for(DedicationToken dedicationToken : dedicationTokenWrapper.getStack())
                sum += dedicationToken.getTokenValue();

        return sum;
    }
}
