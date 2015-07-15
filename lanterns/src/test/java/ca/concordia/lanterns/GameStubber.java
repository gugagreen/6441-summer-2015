package ca.concordia.lanterns;

import ca.concordia.lanterns.entities.Game;

public class GameStubber {
	
	public static Game createGameStub() {
		Game game = new Game();
		game.init(new String[] {"p1", "p2", "p3"});
		return game;
	}
}
