package ca.concordia.lanterns.services.impl;

import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.DedicationType;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class EndGameDetectServiceTest {

    private Game game;

    @Before
    public void setUp() {
        String[] playerNames = {"A", "B", "C"};
        DefaultSetupService service = new DefaultSetupService();

        this.game = new Game();
        this.game = service.createGame(playerNames);
    }

    private void endGame(Game game) {
        // set empty tiles for all players
        for (Player player : game.getPlayers()) {
            player.setTiles(new ArrayList<LakeTile>());
        }
        // set empty tiles for game
        game.setTiles(new Stack<LakeTile>());
        // set turn to last player
        game.setCurrentTurnPlayer(game.getPlayers().length - 1);
    }

    @Test
    public void testIsGameEnded() {

        // looking at unmodified new game from setUp(), game should be at first turn.
        EndGameDetectService endGameService = EndGameDetectService.getInstance();
        assertFalse(endGameService.isGameEnded(this.game));
    }

    @Test
    public void testGetGameWinnerTrue() {
        endGame(game);
        DedicationType dedicationType = DedicationType.SEVEN_UNIQUE;

        // Give a dedication token to player 2 to make him the winner
        Player player = game.getPlayers()[1];
        List<DedicationType> dType = Arrays.asList(DedicationType.values());

        Stack<DedicationToken> dedicationStack = game.getDedications()[dType.indexOf(dedicationType)].getStack();

        player.getDedications().add(dedicationStack.pop());

        // looking at modified new game from setUp(), game should be at first turn, player 2 has a dedication token, others do
        // not.
        EndGameDetectService endGameService = EndGameDetectService.getInstance();

        Player potentialWinner = this.game.getPlayers()[1];
        Set<Player> winners = endGameService.getGameWinner(this.game);
        assertNotNull(winners);
        assertEquals(1, winners.size());
        Player actualWinner = winners.iterator().next();
        assertTrue(actualWinner == potentialWinner);
    }

}
