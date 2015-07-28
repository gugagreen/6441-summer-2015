package ca.concordia.lanterns.services.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.services.impl.EndGameDetectService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.DedicationType;
import ca.concordia.lanterns.services.impl.DefaultSetupService;


public class EndGameDetectServiceTest {

	private Game game ;
	
	@Before
	public void setUp() {
		String[] playerNames = {"A", "B", "C" } ;
		DefaultSetupService service = new DefaultSetupService () ;
		
		game = new Game();
		this.game = service.createGame(playerNames) ;

	}

	@Test
	public void testIsGameEnded() {
		
		//looking at unmodified new game from setUp(), game should be at first turn.
		EndGameDetectService endGameService = new EndGameDetectService();
		assertFalse(endGameService.isGameEnded(this.game));
	}
	
	
	
	@Test
	public void testGetGameWinnerTrue() {
	
	DedicationType dedicationType = DedicationType.SEVEN_UNIQUE;

	//Give a dedication token to player 2 to make him the winner
	Player player = game.getPlayers()[1];
	List<DedicationType> dType = Arrays.asList(DedicationType.values());

	Stack<DedicationToken> dedicationStack = game.getDedications()[dType
		.indexOf(dedicationType)].getStack();

	player.getDedications().add(dedicationStack.pop());

	
	//looking at modified new game from setUp(), game should be at first turn, player 2 has a dedication token, others do not.
	EndGameDetectService endGameService = new EndGameDetectService();
	

		Player potentialWinner = this.game.getPlayers()[1];
		Player actualWinner = endGameService.getGameWinner(this.game);
		assertTrue(actualWinner == potentialWinner);		
	
}



	@Test
	public void testGetGameWinner() {
		
		//looking at unmodified new game from setUp(), game should be at first turn, should be a 4 way tie if game is force ended.
		EndGameDetectService endGameService = new EndGameDetectService();
		
		for(int i = 0; i < game.getPlayers().length ; i++){
			Player potentialWinner = this.game.getPlayers()[i];
			Player actualWinner = endGameService.getGameWinner(this.game);
			assertTrue(actualWinner == potentialWinner);		
		}


		
	}

}
