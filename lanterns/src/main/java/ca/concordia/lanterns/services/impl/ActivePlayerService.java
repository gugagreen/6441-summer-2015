package ca.concordia.lanterns.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.GameEventListener;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * This is an implementation of {@link PlayerService} based upon the rules of the game as described in this <a
 * href=https://dl.dropboxusercontent.com/u/109385546/Lanterns/preview/1-rulebook.pdf> rulebook</a>.
 * 
 * @author parth
 *
 */
public class ActivePlayerService implements PlayerService {
	
	private static final List<Colour> colors = Arrays.asList(Colour.values());
	private List<GameEventListener> gameEventListeners = new ArrayList<GameEventListener>();
    private String eventMessage = null;

	private static class SingletonHolder {
		static final ActivePlayerService INSTANCE = new ActivePlayerService();
	}
	
	public static ActivePlayerService getInstance() {
		return SingletonHolder.INSTANCE;
	}

    public void addListener (GameEventListener toAdd)
    {
        if(!gameEventListeners.contains(toAdd))
            gameEventListeners.add(toAdd);
    }

    public void notifyObservers(String eventMessage)
    {
        if (!gameEventListeners.isEmpty()) {
            for (GameEventListener obj : gameEventListeners)
                obj.displayEventMessage(eventMessage);
        }else
            System.out.println("please add some observers first");
    }

	/**
	 * The rules for exchanging lantern cards
	 * <ul>
	 * <li>The exchange takes place between player and game. It can't happen among players.</li>
	 * <li>The player pays for the exchange with two favor tokens</li>
	 * </ul>
	 */
	@Override
	public void exchangeLanternCard(Game game, int id, Colour giveCard, Colour receiveCard) throws GameRuleViolationException {
		Player player = game.getPlayers()[id];
		int playerFavorToken = player.getFavors();

		if (playerFavorToken < 2) {
			throw new GameRuleViolationException("You do not have enough favour tokens " + "to make this exchange.");
		}

		LanternCardWrapper gameGiveCard = game.getCards()[colors.indexOf(receiveCard)];

		if (gameGiveCard.getQuantity() == 0) {
			throw new GameRuleViolationException(gameGiveCard.getColour().toString()
					+ " colored lantern cards are out of stock. Hence, you can't make this exchange");
		}

		LanternCardWrapper playerGiveCard = player.getCards()[colors.indexOf(giveCard)];

		if (playerGiveCard.getQuantity() == 0) {
			throw new GameRuleViolationException("You do not have " + giveCard.toString() + " colored lantern card."
					+ " Hence, you can't make this exchange");
		}

		int gameFavorToken = game.getFavors();

		game.setFavors(gameFavorToken + 2);
		player.setFavors(playerFavorToken - 2);

		gameGiveCard.setQuantity(gameGiveCard.getQuantity() - 1);
		playerGiveCard.setQuantity(playerGiveCard.getQuantity() - 1);

		LanternCardWrapper gameReceiveCard = game.getCards()[colors.indexOf(giveCard)];
		LanternCardWrapper playerReceiveCard = player.getCards()[colors.indexOf(receiveCard)];

		gameReceiveCard.setQuantity(gameReceiveCard.getQuantity() + 1);
		playerReceiveCard.setQuantity(playerReceiveCard.getQuantity() + 1);

        eventMessage = player.getName() + " spent 2 favor tokens to exchange a " + giveCard.toString() + " lantern card to a " + receiveCard.toString() + " lantern card";
        notifyObservers(eventMessage);
	}

	@Override
	public void makeDedication(Game game, int id, DedicationType dedicationType, Colour[] color)
			throws GameRuleViolationException {
		
		Player player = game.getPlayers()[id] ;
		List<DedicationType> dType = Arrays.asList(DedicationType.values()) ;
		
		Stack<DedicationToken> dedicationStack = game.getDedications()
				[dType.indexOf(dedicationType)].getStack() ;
		Stack<DedicationToken> genericStack = game.getDedications()
				[dType.indexOf(DedicationType.GENERIC)].getStack() ;
		
		if ( dedicationStack.isEmpty() && genericStack.isEmpty() ){
			throw new GameRuleViolationException ( dedicationType.toString() + " dedication tokens"
					+ " are out of stock. Hence, you can't make this dedication" ) ;
		}
		
		int requiredColors ;
		int  requiredCardPerColor;
		
		if ( dedicationType.equals(DedicationType.FOUR_OF_A_KIND)) {
			requiredColors = 1 ;
			requiredCardPerColor = 4 ;
		} else if ( dedicationType.equals(DedicationType.THREE_PAIRS)) {
			requiredColors = 3 ;
			requiredCardPerColor = 2 ;			
		} else if ( dedicationType.equals(DedicationType.SEVEN_UNIQUE)) {
			requiredColors = 7 ;
			requiredCardPerColor = 1 ;		
		} else {
			throw new IllegalArgumentException ( "Invalid Dedication type" ) ;
		}
		
		if ( color.length == requiredColors) {
			LanternCardWrapper[] playerCard = new LanternCardWrapper[requiredColors] ;
			LanternCardWrapper[] gameCard  = new LanternCardWrapper[requiredColors] ;
			
			// Get references to lantern cards of mentioned colors from player as well as game
			for ( int i = 0 ; i != color.length; ++i ) {
				playerCard[i] = player.getCards()[colors.indexOf(color[i])] ;
				
				// check if player have enough cards for required colors to make the dedication
				if ( playerCard[i].getQuantity() < requiredCardPerColor ) {
					throw new GameRuleViolationException ( "You do not have enough " +
							color[i].toString() + " colored lantern cards to make this dedication." ) ;
				}
				
				gameCard[i] = game.getCards()[colors.indexOf(color[i])] ;
			}
			
			// Player gives lantern card to the game i.e pays for the dedication
			for ( int i = 0 ; i != color.length; ++i ) {
				playerCard[i].setQuantity(playerCard[i].getQuantity() - requiredCardPerColor);
				gameCard[i].setQuantity(gameCard[i].getQuantity() + requiredCardPerColor);
			}
			
			// Game gives player the earned dedication
			
			if (  dedicationStack.isEmpty() ) {
				DedicationToken convertedToken = genericStack.pop() ;
				convertedToken.setTokenType(dedicationType); 
				player.getDedications().add(convertedToken);
			} else {
				player.getDedications().add(dedicationStack.pop()) ;
			}
			
		} else {
			throw new IllegalArgumentException ( dedicationType.toString() + " dedication requires"
					+ requiredColors + " different colors" ) ;
		}
	}

	@Override
	public void placeLakeTile(Game game, int id, int playerTileIndex, int lakeTileIndex, int lakeTileSideIndex,
			int playerTileSideIndex) throws GameRuleViolationException {
		// TODO Auto-generated method stub

	}

}
