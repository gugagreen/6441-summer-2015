package ca.concordia.lanterns.services.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class MakeDedicationTest {

	private Game game ;
	private PlayerService service ;
	private static final Colour[] colors = Colour.values() ;
	private static final List<DedicationType> dType = Arrays.asList(DedicationType.values()) ;
	
	@Before
	public void setUp () {
		String[] playerNames = {"A","B","C"} ;
		
		game = new Game () ;
		game.init(playerNames,"makeDedicationTest");
		this.service = new ActivePlayerService () ;
		
		// Setup only dedication tokens
		DefaultSetupService setUpService = new DefaultSetupService () ;
		setUpService.setDedicationTokens(game.getDedications(), playerNames.length);
		
		//The following setup allows first player to make any of the three kinds of dedication
		Player firstPlayer = game.getPlayers()[0] ;
		LanternCardWrapper[] card = firstPlayer.getCards() ;
	
		// Use for Four_OF_A_Kind and SEVEN_UNIQUE
		card[0].setQuantity(5);
		
		// Use for THREE_PAIRS and SEVEN_UNIQUE
		card[1].setQuantity(3);
		card[2].setQuantity(3);
		card[3].setQuantity(3);
		
		// Use for SEVEN_UNIQUE and SEVEN_UNIQUE
		card[4].setQuantity(1);
		card[5].setQuantity(1);
		card[6].setQuantity(1);
	}
	
	@Test
	public void validMakeDedication () {
		Player firstPlayer = game.getPlayers()[0] ;
		
		for ( int k = 0 ; k != dType.size() ; ++k ) {
			if ( dType.get(k).equals(DedicationType.GENERIC)) {
				continue ;
			}
			
			int requiredColors = -1 ;
			int requiredCardPerColor = -1 ;
			int firstParticipatingCardIndex = -1 ;
			
			if ( dType.get(k).equals(DedicationType.FOUR_OF_A_KIND)) {
				requiredColors = 1 ;
				requiredCardPerColor = 4 ;
				firstParticipatingCardIndex = 0 ;
			} else if ( dType.get(k).equals(DedicationType.THREE_PAIRS)) {
				requiredColors = 3 ;
				requiredCardPerColor = 2 ;
				firstParticipatingCardIndex = 1 ;
			} else if ( dType.get(k).equals(DedicationType.SEVEN_UNIQUE)) {
				requiredColors = 7 ;
				requiredCardPerColor = 1 ;
				firstParticipatingCardIndex = 0 ;
			} 
			
			int[] playerCardQuantity = new int[requiredColors] ;
			int[] gameCardQuantity = new int[requiredColors] ;
			
			for ( int i = 0, j = firstParticipatingCardIndex; i != requiredColors; ++i, ++j ) {
				playerCardQuantity[i] = firstPlayer.getCards()[j].getQuantity() ;
				gameCardQuantity[i] = game.getCards()[j].getQuantity() ;
			}
			
			DedicationToken topToken = game.getDedications()[k].getStack().peek() ;
			DedicationToken lastReceivedToken ;
			
			if ( firstPlayer.getDedications().isEmpty() ) {
				lastReceivedToken = null ;
			} else {
				lastReceivedToken = firstPlayer.getDedications().
						get(firstPlayer.getDedications().size() - 1) ;
			}
			
			assertNotSame ( lastReceivedToken, topToken ) ;
			
			try {
				service.makeDedication(game, 0, dType.get(k), Arrays.copyOfRange(colors, 
						firstParticipatingCardIndex, firstParticipatingCardIndex + requiredColors));
			} catch (GameRuleViolationException e) {
				fail ( "A valid make Dedication failed" ) ;
			}
			
			for ( int i = 0, j = firstParticipatingCardIndex; i != requiredColors; ++i, ++j ) {
				assertEquals ( playerCardQuantity[i] - requiredCardPerColor, 
						firstPlayer.getCards()[j].getQuantity()) ;
				assertEquals ( gameCardQuantity[i] + requiredCardPerColor, 
						game.getCards()[j].getQuantity()) ;
			}
			
			DedicationToken currentLastToken ;
			if ( firstPlayer.getDedications().isEmpty() ) {
				currentLastToken = null ;
			} else {
				currentLastToken = firstPlayer.getDedications().
						get(firstPlayer.getDedications().size() -1) ;
			}
			
			DedicationToken currentTopToken = game.getDedications()[k].getStack().peek() ;
			
			assertNotSame (lastReceivedToken, currentLastToken) ;
			assertNotSame(topToken, currentTopToken ) ;

			assertSame(topToken, currentLastToken) ;

			
		}
		
	}
	
	@Test
	public void insufficientPlayerLanternCard() {
		Player firstPlayer = game.getPlayers()[0] ;
		LanternCardWrapper[] card = firstPlayer.getCards() ;
		card[0].setQuantity(3);
		card[2].setQuantity(1);
		card[6].setQuantity(0);
		
		for ( int k = 0 ; k != dType.size() ; ++k ) {
			if ( dType.get(k).equals(DedicationType.GENERIC)) {
				continue ;
			}
			
			int requiredColors = -1 ;
			int firstParticipatingCardIndex = -1 ;
			int lessCardIndex = -1 ;
			
			if ( dType.get(k).equals(DedicationType.FOUR_OF_A_KIND)) {
				requiredColors = 1 ;
				firstParticipatingCardIndex = 0 ;
				lessCardIndex = 0 ;
			} else if ( dType.get(k).equals(DedicationType.THREE_PAIRS)) {
				requiredColors = 3 ;
				firstParticipatingCardIndex = 1 ;
				lessCardIndex = 2 ;
			} else if ( dType.get(k).equals(DedicationType.SEVEN_UNIQUE)) {
				requiredColors = 7 ;
				firstParticipatingCardIndex = 0 ;
				lessCardIndex = 6 ;
			} 
			
			int[] playerCardQuantity = new int[requiredColors] ;
			int[] gameCardQuantity = new int[requiredColors] ;
			
			for ( int i = 0, j = firstParticipatingCardIndex; i != requiredColors; ++i, ++j ) {
				playerCardQuantity[i] = firstPlayer.getCards()[j].getQuantity() ;
				gameCardQuantity[i] = game.getCards()[j].getQuantity() ;
			}
			
			DedicationToken topToken = game.getDedications()[k].getStack().peek() ;
			DedicationToken lastReceivedToken ;
			
			if ( firstPlayer.getDedications().isEmpty() ) {
				lastReceivedToken = null ;
			} else {
				lastReceivedToken = firstPlayer.getDedications().
						get(firstPlayer.getDedications().size() - 1) ;
			}
			
			assertNotSame ( lastReceivedToken, topToken ) ;
			
			try {
				service.makeDedication(game, 0, dType.get(k), Arrays.copyOfRange(colors, 
						firstParticipatingCardIndex, firstParticipatingCardIndex + requiredColors));
				fail ( "A Invalid make Dedication happened" ) ;
			} catch (GameRuleViolationException e) {
				String message = "You do not have enough " +
						colors[lessCardIndex].toString() + " colored lantern cards to make this dedication." ;
				assertEquals ( message, e.getMessage() ) ;				
			}
			
			for ( int i = 0, j = firstParticipatingCardIndex; i != requiredColors; ++i, ++j ) {
				assertEquals ( playerCardQuantity[i], firstPlayer.getCards()[j].getQuantity()) ;
				assertEquals ( gameCardQuantity[i], game.getCards()[j].getQuantity()) ;
			}
			
			DedicationToken currentLastToken ;
			if ( firstPlayer.getDedications().isEmpty() ) {
				currentLastToken = null ;
			} else {
				currentLastToken = firstPlayer.getDedications().
						get(firstPlayer.getDedications().size() -1) ;
			}
			
			DedicationToken currentTopToken = game.getDedications()[k].getStack().peek() ;
			
			assertSame (lastReceivedToken, currentLastToken) ;
			assertSame(topToken, currentTopToken ) ;

			assertNotSame(topToken, currentLastToken) ;

			
		}
		
	}

	@Test
	public void emptyGameDedicationStack() {
		Player firstPlayer = game.getPlayers()[0] ;
		
		game.getDedications()[dType.indexOf(DedicationType.FOUR_OF_A_KIND)].getStack().clear();
		game.getDedications()[dType.indexOf(DedicationType.THREE_PAIRS)].getStack().clear();
		game.getDedications()[dType.indexOf(DedicationType.SEVEN_UNIQUE)].getStack().clear();

		for ( int k = 0 ; k != dType.size() ; ++k ) {
			if ( dType.get(k).equals(DedicationType.GENERIC)) {
				continue ;
			}
			
			int requiredColors = -1 ;
			int requiredCardPerColor = -1 ;
			int firstParticipatingCardIndex = -1 ;
			
			if ( dType.get(k).equals(DedicationType.FOUR_OF_A_KIND)) {
				requiredColors = 1 ;
				requiredCardPerColor = 4 ;
				firstParticipatingCardIndex = 0 ;
			} else if ( dType.get(k).equals(DedicationType.THREE_PAIRS)) {
				requiredColors = 3 ;
				requiredCardPerColor = 2 ;
				firstParticipatingCardIndex = 1 ;
			} else if ( dType.get(k).equals(DedicationType.SEVEN_UNIQUE)) {
				requiredColors = 7 ;
				requiredCardPerColor = 1 ;
				firstParticipatingCardIndex = 0 ;
			} 
			
			int[] playerCardQuantity = new int[requiredColors] ;
			int[] gameCardQuantity = new int[requiredColors] ;
			
			for ( int i = 0, j = firstParticipatingCardIndex; i != requiredColors; ++i, ++j ) {
				playerCardQuantity[i] = firstPlayer.getCards()[j].getQuantity() ;
				gameCardQuantity[i] = game.getCards()[j].getQuantity() ;
			}
			
			DedicationToken topGenericToken = game.getDedications()[dType.indexOf(DedicationType.GENERIC)].getStack().peek() ;
			DedicationToken lastReceivedToken ;
			
			if ( firstPlayer.getDedications().isEmpty() ) {
				lastReceivedToken = null ;
			} else {
				lastReceivedToken = firstPlayer.getDedications().
						get(firstPlayer.getDedications().size() - 1) ;
			}
			
			assertNotSame ( lastReceivedToken, topGenericToken ) ;
			
			try {
				service.makeDedication(game, 0, dType.get(k), Arrays.copyOfRange(colors, 
						firstParticipatingCardIndex, firstParticipatingCardIndex + requiredColors));
			} catch (GameRuleViolationException e) {
				fail ( "A valid make Dedication failed" ) ;
			}
			
			for ( int i = 0, j = firstParticipatingCardIndex; i != requiredColors; ++i, ++j ) {
				assertEquals ( playerCardQuantity[i] - requiredCardPerColor, 
						firstPlayer.getCards()[j].getQuantity()) ;
				assertEquals ( gameCardQuantity[i] + requiredCardPerColor, 
						game.getCards()[j].getQuantity()) ;
			}
			
			DedicationToken currentLastToken ;
			if ( firstPlayer.getDedications().isEmpty() ) {
				currentLastToken = null ;
			} else {
				currentLastToken = firstPlayer.getDedications().
						get(firstPlayer.getDedications().size() -1) ;
			}
			
			DedicationToken currentTopGenericToken ;
			if ( game.getDedications()[dType.indexOf(DedicationType.GENERIC)].getStack().isEmpty() ) {
				currentTopGenericToken = null ;
			} else {
				currentTopGenericToken = game.getDedications()[dType.indexOf(DedicationType.GENERIC)].getStack().peek() ;
			}
			
			assertNotSame (lastReceivedToken, currentLastToken) ;
			assertNotSame(topGenericToken, currentTopGenericToken ) ;

			assertSame(topGenericToken, currentLastToken) ;
			assertEquals (currentLastToken.getTokenType(), dType.get(k)) ;
			
		}
	}
	
	@Test
	public void emptyGameDedicationAndGenericStack() {
		
		Player firstPlayer = game.getPlayers()[0] ;
		
		game.getDedications()[dType.indexOf(DedicationType.THREE_PAIRS)].getStack().clear();
		game.getDedications()[dType.indexOf(DedicationType.GENERIC)].getStack().clear() ;
		
		int[] playerCardQuantity = new int[3] ;
		int[] gameCardQuantity = new int[3] ;
		
		for ( int i = 0, j = 1; i != 3; ++i, ++j ) {
			playerCardQuantity[i] = firstPlayer.getCards()[j].getQuantity() ;
			gameCardQuantity[i] = game.getCards()[j].getQuantity() ;
		}
		
		DedicationToken lastReceivedToken ;
		
		if ( firstPlayer.getDedications().isEmpty() ) {
			lastReceivedToken = null ;
		} else {
			lastReceivedToken = firstPlayer.getDedications().
					get(firstPlayer.getDedications().size() - 1) ;
		}
		
		
		try {
			service.makeDedication(game, 0, dType.get(dType.indexOf(DedicationType.THREE_PAIRS)), Arrays.copyOfRange(colors, 
					1, 1 + 3));
			fail ( "A Invalid make Dedication happened" ) ;
		} catch (GameRuleViolationException e) {
			String message = dType.get(dType.indexOf(DedicationType.THREE_PAIRS)).toString() + " dedication tokens"
					+ " are out of stock. Hence, you can't make this dedication" ;
			assertEquals ( message, e.getMessage() ) ;				
		}
		
		for ( int i = 0, j = 1; i != 3; ++i, ++j ) {
			assertEquals ( playerCardQuantity[i], firstPlayer.getCards()[j].getQuantity()) ;
			assertEquals ( gameCardQuantity[i], game.getCards()[j].getQuantity()) ;
		}
		
		DedicationToken currentLastToken ;
		if ( firstPlayer.getDedications().isEmpty() ) {
			currentLastToken = null ;
		} else {
			currentLastToken = firstPlayer.getDedications().
					get(firstPlayer.getDedications().size() -1) ;
		}
		
		assertSame(lastReceivedToken, currentLastToken) ;		
	}	
}	
