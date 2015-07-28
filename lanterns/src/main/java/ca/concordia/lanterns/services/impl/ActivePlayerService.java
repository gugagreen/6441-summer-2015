package ca.concordia.lanterns.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.GameEventListener;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.enums.Direction;
import ca.concordia.lanterns.services.helper.LakeHelper;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * This is an implementation of {@link PlayerService} based upon the rules of
 * the game as described in this <a
 * href=https://dl.dropboxusercontent.com/u/109385546
 * /Lanterns/preview/1-rulebook.pdf> rulebook</a>.
 * 
 * @author parth
 *
 */
public class ActivePlayerService implements PlayerService {

	private static final List<Colour> colors = Arrays.asList(Colour.values());
	private static final List<Direction> directions = Arrays.asList(Direction
			.values());
	private List<GameEventListener> gameEventListeners = new ArrayList<GameEventListener>();
	private String eventMessage = null;

	private static class SingletonHolder {
		static final ActivePlayerService INSTANCE = new ActivePlayerService();
	}

	public static ActivePlayerService getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void addListener(GameEventListener toAdd) {
		if (!gameEventListeners.contains(toAdd)) {
			gameEventListeners.add(toAdd);
		}
	}

	public void notifyObservers(String eventMessage) {
		if (!gameEventListeners.isEmpty()) {
			for (GameEventListener obj : gameEventListeners) {
				obj.displayEventMessage(eventMessage);
			}
		} else {
			System.out.println("please add some observers first");
		}
	}

	/**
	 * The rules for exchanging lantern cards
	 * <ul>
	 * <li>The exchange takes place between player and game. It can't happen
	 * among players.</li>
	 * <li>The player pays for the exchange with two favor tokens</li>
	 * </ul>
	 */
	@Override
	public void exchangeLanternCard(Game game, int id, Colour giveCard,
			Colour receiveCard) throws GameRuleViolationException {
		Player player = game.getPlayers()[id];
		int playerFavorToken = player.getFavors();

		if (playerFavorToken < 2) {
			throw new GameRuleViolationException(
					"You do not have enough favour tokens "
							+ "to make this exchange.");
		}

		LanternCardWrapper gameGiveCard = game.getCards()[colors
				.indexOf(receiveCard)];

		if (gameGiveCard.getQuantity() == 0) {
			throw new GameRuleViolationException(
					gameGiveCard.getColour().toString()
							+ " colored lantern cards are out of stock. Hence, you can't make this exchange");
		}

		LanternCardWrapper playerGiveCard = player.getCards()[colors
				.indexOf(giveCard)];

		if (playerGiveCard.getQuantity() == 0) {
			throw new GameRuleViolationException("You do not have "
					+ giveCard.toString() + " colored lantern card."
					+ " Hence, you can't make this exchange");
		}

		int gameFavorToken = game.getFavors();

		game.setFavors(gameFavorToken + 2);
		player.setFavors(playerFavorToken - 2);

		gameGiveCard.setQuantity(gameGiveCard.getQuantity() - 1);
		playerGiveCard.setQuantity(playerGiveCard.getQuantity() - 1);

		LanternCardWrapper gameReceiveCard = game.getCards()[colors
				.indexOf(giveCard)];
		LanternCardWrapper playerReceiveCard = player.getCards()[colors
				.indexOf(receiveCard)];

		gameReceiveCard.setQuantity(gameReceiveCard.getQuantity() + 1);
		playerReceiveCard.setQuantity(playerReceiveCard.getQuantity() + 1);

		eventMessage = player.getName()
				+ " spent 2 favor tokens to exchange a " + giveCard.toString()
				+ " lantern card to a " + receiveCard.toString()
				+ " lantern card";
		notifyObservers(eventMessage);
	}

	@Override
	public void makeDedication(Game game, int id,
			DedicationType dedicationType, Colour[] color)
			throws GameRuleViolationException {

		Player player = game.getPlayers()[id];
		List<DedicationType> dType = Arrays.asList(DedicationType.values());

		Stack<DedicationToken> dedicationStack = game.getDedications()[dType
				.indexOf(dedicationType)].getStack();
		Stack<DedicationToken> genericStack = game.getDedications()[dType
				.indexOf(DedicationType.GENERIC)].getStack();

		if (dedicationStack.isEmpty() && genericStack.isEmpty()) {
			throw new GameRuleViolationException(
					dedicationType.toString()
							+ " dedication tokens"
							+ " are out of stock. Hence, you can't make this dedication");
		}

		payDedicationCost(dedicationType, color, player, game);

		// Game gives player the earned dedication
		if (dedicationStack.isEmpty()) {
			DedicationToken convertedToken = genericStack.pop();
			convertedToken.setTokenType(dedicationType);
			player.getDedications().add(convertedToken);
		} else {
			player.getDedications().add(dedicationStack.pop());
		}
	}

	@Override
	public void placeLakeTile(Game game, int id, int playerTileIndex,
			int existingTileIndex, int existingTileSideIndex,
			int playerTileSideIndex) throws GameRuleViolationException {

		Player player = game.getPlayers()[id];
		LakeTile playerTile = player.getTiles().get(playerTileIndex);
		LakeTile existingTile = game.getLake().get(existingTileIndex);

		if (existingTile.getSides()[existingTileIndex].getAdjacent() != null) {
			throw new GameRuleViolationException(
					"The specified place in the lake is occupied by another tile."
							+ "Hence you can't place your tile at this place");
		}

		// The Index value of the TileSide indexed by playerTileSideIndex, when
		// playerTile is kept in the lake according to requested orientation
		int orientedPlayerTileSideIndex = directions.get(existingTileSideIndex)
				.getOppositeTileSideIndex();

		// The index value of the TileSide facing the first player when
		// playerTile is kept in the lake
		int firstPlayerTileSideIndex;

		if (playerTileSideIndex >= orientedPlayerTileSideIndex) {
			firstPlayerTileSideIndex = playerTileSideIndex
					- orientedPlayerTileSideIndex;
		} else {
			firstPlayerTileSideIndex = LakeTile.TOTAL_SIDES
					- (orientedPlayerTileSideIndex - playerTileSideIndex);
		}

		playerTile.setOrientation(firstPlayerTileSideIndex);

		LakeHelper.setAdjacentLakeTiles(playerTile, existingTile,
				directions.get(existingTileSideIndex));

		giveMatchingBonus(game, player, playerTile);
		distributeLanternCards(game, id, playerTile);

	}
	
	//TODO refactor duplicate code
	private void distributeLanternCards(Game game, int activePlayerID, LakeTile playerTile) {
		TileSide[] sides = playerTile.getSides();
		Player[] players = game.getPlayers();
		
		for (int i = activePlayerID; i != players.length; ++i) {
			Colour sideColor = sides[i].getColour();
			LanternCardWrapper gameCard = game.getCards()[colors.indexOf(sideColor)] ;
			
			if (gameCard.getQuantity() != 0) {
				LanternCardWrapper playerCard = players[i].getCards()[colors.indexOf(sideColor)];
				
				gameCard.setQuantity(gameCard.getQuantity() - 1);
				playerCard.setQuantity(playerCard.getQuantity() + 1);
			}
		}
		
		for (int i = 0; i != activePlayerID; ++i) {
			Colour sideColor = sides[i].getColour();
			LanternCardWrapper gameCard = game.getCards()[colors.indexOf(sideColor)] ;
			
			if (gameCard.getQuantity() != 0) {
				LanternCardWrapper playerCard = players[i].getCards()[colors.indexOf(sideColor)];
				
				gameCard.setQuantity(gameCard.getQuantity() - 1);
				playerCard.setQuantity(playerCard.getQuantity() + 1);
			}
		}
	}

	private void giveMatchingBonus(Game game, Player player, LakeTile playerTile) {
		TileSide[] sides = playerTile.getSides();
	
		for (int i = 0; i != sides.length; ++i) {
			LakeTile adjTile = sides[i].getAdjacent();
			
			if (adjTile != null) {
				Colour adjColor = adjTile.getSides()[directions.get(i)
						.getOppositeTileSideIndex()].getColour();

				if (adjColor.equals(sides[i].getColour())) {
				
					LanternCardWrapper gameCard = game.getCards()[colors
							.indexOf(adjColor)];
					
					if (gameCard.getQuantity() != 0) {
						gameCard.setQuantity(gameCard.getQuantity() - 1);
						LanternCardWrapper playerCard = player.getCards()[colors
								.indexOf(adjColor)];
						playerCard.setQuantity(playerCard.getQuantity() + 1);
					}

					if (adjTile.isPlatform() && game.getFavors() != 0) {
						game.setFavors(game.getFavors() - 1);
						player.setFavors(player.getFavors() + 1);
					}

				}
			}
		}
		if (playerTile.isPlatform() && game.getFavors() != 0) {
			game.setFavors(game.getFavors() - 1);
			player.setFavors(player.getFavors() + 1);
		}
	}

	// Helper method for makeDedication. It exchanges the lantern cards between
	// player and game for dedication of types mentioned in rulebook.
	// Throws appropriate exception when player do not have enough dedication
	// cards.
	private void payDedicationCost(DedicationType dedicationType,
			Colour[] color, Player player, Game game)
			throws GameRuleViolationException {

		DedicationCost cost = getDedicationCost(dedicationType);

		if (color.length == cost.getRequiredColors()) {
			LanternCardWrapper[] playerCard = new LanternCardWrapper[cost
					.getRequiredColors()];
			LanternCardWrapper[] gameCard = new LanternCardWrapper[cost
					.getRequiredColors()];

			// Get references to lantern cards of mentioned colors from player
			// as well as game
			for (int i = 0; i != color.length; ++i) {
				playerCard[i] = player.getCards()[colors.indexOf(color[i])];

				// check if player have enough cards for required colors to make
				// the dedication
				if (playerCard[i].getQuantity() < cost
						.getRequiredCardPerColor()) {
					throw new GameRuleViolationException(
							"You do not have enough "
									+ color[i].toString()
									+ " colored lantern cards to make this dedication.");
				}

				gameCard[i] = game.getCards()[colors.indexOf(color[i])];
			}

			// Player gives lantern card to the game i.e pays for the dedication
			for (int i = 0; i != color.length; ++i) {
				playerCard[i].setQuantity(playerCard[i].getQuantity()
						- cost.getRequiredCardPerColor());
				gameCard[i].setQuantity(gameCard[i].getQuantity()
						+ cost.getRequiredCardPerColor());
			}

		} else {
			throw new IllegalArgumentException(dedicationType.toString()
					+ " dedication requires " + cost.getRequiredColors()
					+ " different colors");
		}
	}

	public DedicationCost getDedicationCost(DedicationType dedicationType) {
		if (dedicationType.equals(DedicationType.FOUR_OF_A_KIND)) {
			return DedicationCost.FOUR_OF_A_KIND;
		} else if (dedicationType.equals(DedicationType.THREE_PAIRS)) {
			return DedicationCost.THREE_PAIRS;
		} else if (dedicationType.equals(DedicationType.SEVEN_UNIQUE)) {
			return DedicationCost.SEVEN_UNIQUE;
		} else {
			throw new IllegalArgumentException("Invalid Dedication type");
		}
	}
}
