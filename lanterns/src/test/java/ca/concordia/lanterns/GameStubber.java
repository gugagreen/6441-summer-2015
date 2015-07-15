package ca.concordia.lanterns;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ca.concordia.lanterns.entities.Game;

public class GameStubber {
	
	@Test
	public static Game createGameStub() {
		Game game = new Game();
		game.init(new String[] {"p1", "p2", "p3"});
		assertNotNull(game);
		return game;
	}
}
