package ca.concordia.lanterns.exchange.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;

public class GreedyExchangeTest {

	private GreedyExchange exchange;
	private Game game;
	private Player player;
	
	@Before
	public void setup() {
		this.exchange = new GreedyExchange();
		String[] playerNames = new String[] {"Alpha", "Bravo", "Charlie"};
		this.game = DefaultSetupService.getInstance().createGame(playerNames);
		this.player = game.getPlayers()[0];
	}
	
	@Test
	public void exchangeNotNecessary() {
		// setup game with 3 pairs already possible (no need to exchange to get a dedication)
		game.setFavors(game.getFavors() -2);
		player.setFavors(2);
		LanternCardWrapper[] cards = player.getCards();
		// set first 3 cards as pairs
		// leave next 3 cards with quantity=1, and last with quantity=0, 
		// so player would be ready for exchange (7 unique)
		int[] quantities = new int[] {3,3,3,1,1,1,0};
		for (int i = 0; i < cards.length; i++) {
			cards[i].setQuantity(quantities[i]);;
		}
		
		// then try to make a exchange
		exchange.makeExchange(game, player);
		
		// no exchange should have happend
		for (int i = 0; i < cards.length; i++) {
			assertEquals(quantities[i], cards[i].getQuantity());
		}
	}
	
	@Test
	public void exchangeForFourOfAKind() {
		// setup so player is one away from 4 of a kind
		game.setFavors(game.getFavors() -2);
		player.setFavors(2);
		LanternCardWrapper[] cards = player.getCards();
		LanternCardWrapper give = null;
		LanternCardWrapper receive = null;
		// select card with one (given during setup) to be given during exchange. 
		// Add 3 to another card (the one to receive)
		// and make sure all other have zero, so they cannot make an exchange
		for (LanternCardWrapper card : cards) {
			if (card.getQuantity() == 1) {
				give = card;
			} else if ((receive == null) && (card.getQuantity() == 0)) {
				receive = card;
				receive.setQuantity(3);
			}else {
				assertEquals(0, card.getQuantity());
			}
		}
		assertNotNull(give);
		assertNotNull(receive);
		
		// then try to make a exchange
		exchange.makeExchange(game, player);
		
		// exchange should have been made
		assertEquals(4, receive.getQuantity());
		assertEquals(0, give.getQuantity());
	}
}
