package ca.concordia.lanterns;

import ca.concordia.lanternsentities.Game;

public class GameStubber {
	

	public static Game createGameStub() {
		Game game = new Game();
		game.init(new String[] {"p1", "p2", "p3"}, "testGame");
		return game;
	}
}
