package ca.concordia.lanterns.services.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.TileStack;

public class PlaceATileTest {
	
	private Game game;
	private static final List<Colour> colors = Arrays.asList(Colour.values());
	
	@Before
	public void setUp() {
		DefaultSetupService setUpService = new DefaultSetupService();
		
		String[] playerNames = {"a","b","c", "d"};
		
		// Initalize a new game by using some of the services of default setup but not all of them.
		setUpService.validatePlayersSet(playerNames);
		String[] sortedPlayerNames = setUpService.decidePlayersOrder(playerNames);
		int playerCount = sortedPlayerNames.length;
		game = new Game();
		//Initialize player data structures as well as favors for game
		game.init(sortedPlayerNames, "Test"); 

		// Custom initialization of lake
		List<LakeTile> tilesInLake = new ArrayList<LakeTile>();
		tilesInLake.add(TileStack.T54.getTile());
		tilesInLake.add(TileStack.T11.getTile());
		tilesInLake.add(TileStack.T33.getTile());
		
		tilesInLake.get(0).setOrientation(2);
		tilesInLake.get(1).setOrientation(0);
		tilesInLake.get(2).setOrientation(3);
		tilesInLake.get(0).getSides()[0].setAdjacent(tilesInLake.get(1));
		tilesInLake.get(1).getSides()[2].setAdjacent(tilesInLake.get(0));
		tilesInLake.get(1).getSides()[3].setAdjacent(tilesInLake.get(2));
		tilesInLake.get(2).getSides()[1].setAdjacent(tilesInLake.get(1));
		
		game.setLake(tilesInLake);

		// Added one tile to the stack of tiles in game.
		game.getTiles().add(TileStack.T12.getTile());
		
		setUpService.separateLanternCards(game.getCards(), playerCount);
		
		// Set purple to one so that only active player recieves it
		game.getCards()[colors.indexOf(Colour.PURPLE)].setQuantity(1);
		game.setCurrentTurnPlayer(1);
		
		// Custom tile for current Player
		game.getPlayers()[1].getTiles().add(TileStack.T42.getTile());
	}

	@Test
	public void validPlaceATile() {
		
		Player player = game.getPlayers()[1];
		// Record Data structures before placing a tile
		List<LakeTile> lake = game.getLake();
		int lakeSize = lake.size();
		
		LakeTile topTile = game.getTiles().peek() ;
		
		TileSide westZerothTile = lake.get(0).getSides()[3];
		TileSide southSecondTile = lake.get(2).getSides()[2];
		assertNull(westZerothTile.getAdjacent());
		assertNull(southSecondTile.getAdjacent());

		
		int purpleQuantity = game.getCards()[colors.indexOf(Colour.PURPLE)].getQuantity();
		int redQuantity = game.getCards()[colors.indexOf(Colour.RED)].getQuantity();
		int blackQuantity = game.getCards()[colors.indexOf(Colour.BLACK)].getQuantity();
		int favorQuantity = game.getFavors();
		
		LakeTile playerTile = player.getTiles().get(0);
		for (int i = 0; i != LakeTile.TOTAL_SIDES; ++i) {
			assertNull(playerTile.getSides()[i].getAdjacent());
		}
		
		ActivePlayerService service = new ActivePlayerService();
		try {
			service.placeLakeTile(game, 1, 0, 2, 2, 3);;
		} catch (GameRuleViolationException e) {
			fail("A Valid Tile placement failed");
		}
		
		assertSame(westZerothTile.getAdjacent(), playerTile);
		assertSame(southSecondTile.getAdjacent(), playerTile);
		assertSame(playerTile.getSides()[0].getAdjacent(), lake.get(2));
		assertSame(playerTile.getSides()[1].getAdjacent(), lake.get(0));
		
		assertEquals(purpleQuantity - 1, game.getCards()[colors.indexOf(Colour.PURPLE)].getQuantity());
		assertEquals(redQuantity - 2, game.getCards()[colors.indexOf(Colour.RED)].getQuantity());
		assertEquals(blackQuantity - 2, game.getCards()[colors.indexOf(Colour.BLACK)].getQuantity());
		assertEquals(favorQuantity - 2, game.getFavors());
		
		assertEquals(player.getCards()[colors.indexOf(Colour.PURPLE)].getQuantity(), 1);
		assertEquals(player.getCards()[colors.indexOf(Colour.RED)].getQuantity(), 2);
		assertEquals(player.getFavors(), 2); 
		
		assertEquals(game.getPlayers()[2].getCards()[colors.indexOf(Colour.BLACK)].getQuantity(), 1);
		assertEquals(game.getPlayers()[3].getCards()[colors.indexOf(Colour.BLACK)].getQuantity(), 1);
		assertEquals(game.getPlayers()[0].getCards()[colors.indexOf(Colour.PURPLE)].getQuantity(), 0);

		assertSame(game.getLake().get(3),playerTile);
		assertSame(topTile,player.getTiles().get(0));
		assertTrue(game.getTiles().isEmpty());
		assertEquals(service.getLastRoundCount(), -1) ;
	}

}
