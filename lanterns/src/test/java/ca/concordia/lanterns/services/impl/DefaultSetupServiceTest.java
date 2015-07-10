package ca.concordia.lanterns.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.DedicationToken;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.Lake;
import ca.concordia.lanterns.entities.LakeTile;
import ca.concordia.lanterns.entities.LanternCard;
import ca.concordia.lanterns.entities.Player;
import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;

public class DefaultSetupServiceTest {
	
	private DefaultSetupService service;
	
	@Before
	public void setup() {
		this.service = new DefaultSetupService();
	}
	
	@Test
	public void testCreateGameSuccess() {
		final String[] playerNames = createPlayerNames(4);
		
		Game game = service.createGame(playerNames);
		
		assertNotNull(game);
		assertNotNull(game.getPlayers());
		assertEquals(playerNames.length, game.getPlayers().length);
		assertNotNull(game.getCards());
		assertNotNull(game.getDedications());
		assertNotNull(game.getLake());
		assertNotNull(game.getTiles());
		assertEquals(Game.TOTAL_FAVORS, game.getFavors());
	}
	
	@Test
	public void testGenerateTiles4Players() {
		// expected 33 = 20 + 12 (3 tiles per player) + 1 initial tile
		callGenerateTiles(4, 33);
	}
	
	@Test
	public void testGenerateTiles3Players() {
		// expected 33 = 18 + 9 (3 tiles per player) + 1 initial tile
		callGenerateTiles(3, 28);
	}
	
	@Test
	public void testGenerateTiles2Players() {
		// expected 33 = 16 + 6 (3 tiles per player) + 1 initial tile
		callGenerateTiles(2, 23);
	}
	
	@Test
	public void testGenerateTiles0Players() {
		LakeTile[] tiles = service.generateTiles(0);
		assertNull(tiles);
	}
	
	private void callGenerateTiles(int playerCount, int expected) {
		LakeTile[] tiles = service.generateTiles(playerCount);
		assertNotNull(tiles);
		// 33 = 20 + 12 (3 tiles per player) + 1 initial tile
		assertEquals(expected, tiles.length);
	}
	
	@Test
	public void testStartLake() {
		Lake lake = new Lake();
		Colour[] colours = {Colour.RED, Colour.BLUE, Colour.GREEN, Colour.PURPLE};
		LakeTile initialTile = new LakeTile(colours, false);
		service.startLake(lake, initialTile);
		
		assertEquals(1, lake.getTiles().size());
		assertEquals(initialTile, lake.getTiles().get(0));
	}
	
	@Test
	public void testDealPlayerTiles() {
		final Player[] players = createPlayers(3);
		final LakeTile[] totalTiles = new LakeTile[36];
		for (int i = 0; i < totalTiles.length; i++) {
			totalTiles[i] = new LakeTile(new Colour[4], false);
		}
		
		service.dealPlayerTiles(totalTiles, players);
		for (Player player : players) {
			assertEquals(3, player.getTiles().size());
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDealPlayerTilesWithNoTiles() {
		final Player[] players = createPlayers(3);
		final LakeTile[] totalTiles = {};
		service.dealPlayerTiles(totalTiles, players);
	}
	
	@Test
	public void testDrawTileStackFourPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 4);
		assertEquals(20, tiles.size());
	}
	
	@Test
	public void testDrawTileStackThreePlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 3);
		assertEquals(18, tiles.size());
	}
	
	@Test
	public void testDrawTileStackTwoPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 2);
		assertEquals(16, tiles.size());
	}
	
	@Test
	public void testDrawTileStackZeroPlayers() {
		final Stack<LakeTile> tiles = new Stack<LakeTile>();
		final LakeTile[] totalTiles = new LakeTile[36];
		service.drawTileStack(tiles, totalTiles, 0);
		assertTrue(tiles.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSeparateLanternCardsFourPlayers() {
		final Stack<LanternCard>[] cards = (Stack<LanternCard>[]) new Stack[Colour.values().length];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new Stack<LanternCard>();
		}
		
		service.separateLanternCards(cards, 4);
		for (Stack<LanternCard> stack : cards) {
			assertEquals(8, stack.size());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void testSeparateLanternCardsWrongStack() {
		final Stack<LanternCard>[] cards = (Stack<LanternCard>[]) new Stack[Colour.values().length + 1];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new Stack<LanternCard>();
		}
		
		service.separateLanternCards(cards, 4);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSetDedicationTokensFourPlayers() {
		final Stack<DedicationToken>[] dedications = new Stack[DedicationType.values().length];
		for (int i = 0; i < dedications.length; i++) {
			dedications[i] = new Stack<DedicationToken>();
		}
		
		service.setDedicationTokens(dedications, 4);
		
		for (int i = 0; i < dedications.length; i++) {
			Stack<DedicationToken> dedication = dedications[i];
			assertNotNull(dedication);
			assertEquals(DedicationType.values()[i].getValuesFour().length, dedication.size());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDistributeInitialLanterns() {
		List<Colour> colourValues = Arrays.asList(Colour.values());
		final Lake lake = new Lake();
		Colour[] colours = {Colour.RED, Colour.BLUE, Colour.GREEN, Colour.PURPLE};
		LakeTile initialTile = new LakeTile(colours, false);
		lake.getTiles().add(initialTile);
		final Stack<LanternCard>[] cards = (Stack<LanternCard>[]) new Stack[colourValues.size()];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new Stack<LanternCard>();
			cards[i].push(new LanternCard(colourValues.get(i)));
		}
		Player[] players = createPlayers(4);
		
		service.distributeInitialLanterns(lake, cards, players);
		
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];
			Colour colour = initialTile.getSides()[i].getColour();
			
			final Stack<LanternCard>[] playerCards = player.getCards();
			for (int j = 0; j < playerCards.length; j++) {
				Stack<LanternCard> colourStack = playerCards[j];
				if (j == colourValues.indexOf(colour)) {
					assertEquals(1, colourStack.size());
				} else {
					assertEquals(0, colourStack.size());
				}
			}
		}
	}
	
	private String[] createPlayerNames(int quantity) {
		String[] playerNames = new String[quantity];
		for (int i = 0; i < quantity; i++) {
			playerNames[i] = Integer.toString(i);
		}
		return playerNames;
	}
	
	private Player[] createPlayers(int quantity) {
		Player[] players = new Player[quantity];
		for (int i = 0; i < quantity; i++) {
			players[i] = new Player(Integer.toString(i));
		}
		return players;
	}
}
