package ca.concordia.lanterns.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.services.SetupService;

public class DefaultSetupServiceTest {
	
	private SetupService service;
	
	@Before
	public void setup() {
		this.service = new DefaultSetupService();
	}
	
	@Test
	public void testCreateGameSuccess() {
		String player1 = "one";
		String player2 = "two";
		String player3 = "three";
		String player4 = "four";
		final Set<String> playerNames = new HashSet<String>(Arrays.asList(new String[] {player1, player2, player3, player4}));
		
		Game game = service.createGame(playerNames);
		
		Assert.assertNotNull(game);
		// FIXME - finish test
	}
}
