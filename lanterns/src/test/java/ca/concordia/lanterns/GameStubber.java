package ca.concordia.lanterns;

import ca.concordia.lanterns.ai.impl.HumanPlayer;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.AI;
import ca.concordia.lanternsentities.enums.AIType;

public class GameStubber {

    public static Game createGameStub() {
        Game game = new Game();
        AI[] ais = createAIs(game, getPlayerNames());
        game.init(ais, "testGame");
        return game;
    }
    
    public static AI[] createAIs(Game game, String[] playerNames) {
    	AI[] ais = new AI[playerNames.length];
        for (int i = 0; i < ais.length; i++) {
        	Player player = new Player();
        	player.init(playerNames[i], i);
        	ais[i] = new HumanPlayer(game, player);
        }
        return ais;
    }
    
    public static String[] getPlayerNames() {
    	return new String[] {"p1", "p2", "p3"};
    }
    
    public static AIType[] getAITypes() {
    	return new AIType[] {AIType.HUMAN, AIType.HUMAN, AIType.HUMAN};
    }
}
