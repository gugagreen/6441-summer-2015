package ca.concordia.lanterns.controllers;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.services.impl.DefaultGameCacheService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanterns.services.impl.EndGameDetectService;
import ca.concordia.lanterns.services.strategies.EndGameStrategy;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.AIType;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

import java.util.Set;

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

    public void setEndGameStrategy(EndGameStrategy endGameStrategy)
    {
        EndGameDetectService.getInstance().setEndGameStrategy(endGameStrategy);
    }
}
