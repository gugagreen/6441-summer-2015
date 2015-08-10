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
		
		assertEquals(4, receive.getQuantity());
		assertEquals(0, give.getQuantity());
	}
}
