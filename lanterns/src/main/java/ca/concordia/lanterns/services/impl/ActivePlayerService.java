package ca.concordia.lanterns.services.impl;

import java.util.Arrays;
import java.util.List;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * This is an implementation of {@link PlayerService} based upon the rules of the game as described
 * in this <a href=https://dl.dropboxusercontent.com/u/109385546/Lanterns/preview/1-rulebook.pdf>
 * rulebook</a>.
 * 
 * @author parth
 *
 */
public class ActivePlayerService implements PlayerService {

	private static final List<Colour> colors = Arrays.asList(Colour.values()) ;
	
	/**
	 * The rules for exchanging lantern cards
	 * <ul>
	 * <li>The exchange takes place between player and game. It can't happen among players.</li>
	 * <li>The player pays for the exchange with two favor tokens</li>
	 * </ul>
	 */
	@Override
	public void exchangeLanternCard(Game game, int id, Colour giveCard,
			Colour receiveCard) throws GameRuleViolationException {
		// TODO Auto-generated method stub

		Player player = game.getPlayers()[id] ;
		int playerFavorToken = player.getFavors() ;
		
		if ( playerFavorToken < 2 ) {
			throw new GameRuleViolationException ( "You do not have enough favour tokens "
					+ "to make this exchange." ) ;
		}
		
		LanternCardWrapper gameGiveCard = game.getCards()[colors.indexOf(receiveCard)] ;
		
		if ( gameGiveCard.getQuantity() == 0 ) {
			throw new GameRuleViolationException ( gameGiveCard.getColour().toString() + 
					" colored lantern cards are out of stock. Hence, you can't make this exchange" );
		}
		
		LanternCardWrapper playerGiveCard = player.getCards()[colors.indexOf(giveCard)] ;
		
		if ( playerGiveCard.getQuantity() == 0 ) {
			throw new GameRuleViolationException ( "You do not have " + giveCard.toString() + 
					" colored lantern card." + " Hence, you can't make this exchange" ) ;
		}
		
		int gameFavorToken = game.getFavors() ;
		
		game.setFavors(gameFavorToken + 2);
		player.setFavors(playerFavorToken - 2);
		
		gameGiveCard.setQuantity(gameGiveCard.getQuantity() - 1);
		playerGiveCard.setQuantity(playerGiveCard.getQuantity() - 1);
		
		LanternCardWrapper gameReceiveCard = game.getCards()[colors.indexOf(giveCard)] ;
		LanternCardWrapper playerReceiveCard = player.getCards()[colors.indexOf(receiveCard)] ;
		
		gameReceiveCard.setQuantity(gameReceiveCard.getQuantity() + 1);
		playerReceiveCard.setQuantity(playerReceiveCard.getQuantity() + 1);
		
	}

	@Override
	public void makeDedication(Game game, int id,
			DedicationType dedicationType, Colour[] color)
			throws GameRuleViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void placeLakeTile(Game game, int id, int playerTileIndex,
			int lakeTileIndex, int lakeTileSideIndex, int playerTileSideIndex)
			throws GameRuleViolationException {
		// TODO Auto-generated method stub

	}

}
