package ca.concordia.lanterns.Controller;

import java.util.Set;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.services.impl.DefaultGameCacheService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanterns.services.impl.EndGameDetectService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Basic game controller that is responsible for accessing game services.
 */
public class GameController {

	public Game createGame(String[] playerNames) {
		return DefaultSetupService.getInstance().createGame(playerNames);
	}

	public Game loadGame(String loadFileName) {
		return DefaultGameCacheService.getInstance().loadGame(loadFileName);
	}
	
	public Game loadValidatedGame(String loadFileName) {
		return DefaultGameCacheService.getInstance().loadValidatedGame(loadFileName);
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
		DedicationCost cost = ActivePlayerService.getInstance().getDedicationCost(dedicationType);;
		return cost.getRequiredColors();
	}

	public void makeDedication(Game game, int id, DedicationType dedicationType, Colour[] colours)
			throws GameRuleViolationException {
		ActivePlayerService.getInstance().makeDedication(game, id, dedicationType, colours);
	}

	public void placeLakeTile(Game game, int id, int playerTileIndex, int existingTileIndex, int existingTileSideIndex,
			int playerTileSideIndex) throws GameRuleViolationException {
		ActivePlayerService.getInstance().placeLakeTile(game, id, playerTileIndex, existingTileIndex, existingTileSideIndex,
				playerTileSideIndex);
	}
}
