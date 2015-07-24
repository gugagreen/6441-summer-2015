package ca.concordia.lanterns.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import ca.concordia.lanterns.services.SetupService;
import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;
import ca.concordia.lanternsentities.enums.TileStack;

public class DefaultSetupService implements SetupService {
	
	private static class SingletonHolder {
		static final DefaultSetupService INSTANCE = new DefaultSetupService();
	}
	
	public static DefaultSetupService getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Create game creates game using player name
	 */
	@Override
	public Game createGame(String[] playerNames) {
		validatePlayersSet(playerNames);
		String[] sortedPlayerNames = decidePlayersOrder(playerNames);
		int playerCount = sortedPlayerNames.length;
		
		Game game = new Game();
		game.init(sortedPlayerNames);
		LakeTile[] totalTiles = generateTiles(playerCount);
		
		startLake(game.getLake(), totalTiles[0], game.getPlayers().length);
		dealPlayerTiles(totalTiles, game.getPlayers());
		drawTileStack(game.getTiles(), totalTiles, playerCount);
		separateLanternCards(game.getCards(), playerCount);
		setDedicationTokens(game.getDedications(), playerCount);
		distributeInitialLanterns(game.getLake(), game.getCards(), game.getPlayers());
		return game;
	}
	
	/**
	 * Validate Players Set validates the players names
	 * @param playerNames
	 */
	protected void validatePlayersSet(String[] playerNames) {
		if ((playerNames == null) || (playerNames.length < 2) || (playerNames.length > 4)) {
			throw new IllegalArgumentException("Number of players should be between 2 and 4!");
		} else {
			// verify if there is any duplicated name
			for (int i = 0; i < playerNames.length; i++) {
				for (int j = i+1; j < playerNames.length; j++) {
					if ((i != j) && (playerNames[i] == playerNames[j])) {
						throw new IllegalArgumentException("Player names cannot be duplicated! -> [" + playerNames[i] + "]");
					}
				}
			}
		}
	}
	
	@Override
	public String[] decidePlayersOrder(final String[] playerNames) {
		List<String> players = Arrays.asList(playerNames);
		Collections.shuffle(players);
		return players.toArray(new String[playerNames.length]);
	}

	/**
	 * Generates tile stack depending on the number of players in the game.
	 * 
	 * @param playerCount
	 *            number of players in the game.
	 * @return Array of tiles.
	 */
	protected LakeTile[] generateTiles(final int playerCount) {
		LakeTile[] gameTiles = null;

		switch (playerCount) {
		case 4:
			gameTiles = new LakeTile[FOUR_PLAYERS_TILE_COUNT];
			break;
		case 3:
			gameTiles = new LakeTile[THREE_PLAYERS_TILE_COUNT];
			break;
		case 2:
			gameTiles = new LakeTile[TWO_PLAYERS_TILE_COUNT];
			break;
		default:
			break;
		}

		// if total tiles is valid, populate it
		if (gameTiles != null) {
			List<TileStack> totalTiles = new ArrayList<TileStack>(Arrays.asList(TileStack.values()));
			
			// first, remove initial tile, and add to the gameTiles
			int initalIndex = totalTiles.indexOf(TileStack.T54);
			TileStack initialTile = totalTiles.remove(initalIndex);
			gameTiles[0] = initialTile.tile;
			
			// then shuffle remaining tiles and assign to gameTiles
			Collections.shuffle(totalTiles);
			for (int i = 1; i < gameTiles.length; i++) {
				gameTiles[i] = totalTiles.get(i).tile;
			}
		}

		return gameTiles;
	}

	@Override
	public void startLake(final List<LakeTile> lake, final LakeTile initialTile, int playerCount) {
		// RED on initial tile will always be element 1. And will be pointing the first player. 
		initialTile.setOrientation(1);
		lake.add(initialTile);
	}

	@Override
	public void dealPlayerTiles(final LakeTile[] totalTiles, final Player[] players) {
		int toAssign = (3 * players.length);
		if (totalTiles.length > toAssign) {
			while (toAssign > 0) {
				for (Player player : players) {
					player.getTiles().add(totalTiles[1 + toAssign--]);
				}
			}
		} else {
			throw new IllegalArgumentException("There are not enough tiles to be assigned!");
		}
	}

	@Override
	public void drawTileStack(final Stack<LakeTile> tiles, final LakeTile[] totalTiles, final int playerCount) {
		int count = 0;
		if (playerCount == 4) {
			count = 20;
		} else if (playerCount == 3) {
			count = 18;
		} else if (playerCount == 2) {
			count = 16;
		}

		// start with a shift: 1st tile + 3 tiles per player
		int shift = 1 + 3 * playerCount;

		for (int i = shift; i < (shift + count); i++) {
			tiles.add(totalTiles[i]);
		}
	}

	@Override
	public void separateLanternCards(final LanternCardWrapper[] cards, final int playerCount) {
		int count = 0;
		if (playerCount == 4) {
			count = 8;
		} else if (playerCount == 3) {
			count = 7;
		} else if (playerCount == 2) {
			count = 5;
		}
		
		Colour[] colours = Colour.values();
		
		if ((cards != null) && (cards.length == colours.length)) {
			for (int i = 0; i < colours.length; i++) {
				LanternCardWrapper colourStack = cards[i];
				colourStack.setQuantity(count);
			}
		} else {
			throw new IllegalArgumentException("Stack of cards to be populated is invalid!");
		}
	}
	
	@Override
	public void setDedicationTokens(final DedicationTokenWrapper[] dedications, final int playerCount) {
		DedicationType[] types = DedicationType.values();
		
		if ((dedications != null) && (dedications.length == types.length)) {
			for (int i = 0; i < types.length; i++) {
				DedicationTokenWrapper dedicationStack = dedications[i];
				DedicationType type = types[i];
				int[] values = null;
				if (playerCount == 4) {
					values = type.getValuesFour();
				} else if (playerCount == 3) {
					values = type.getValuesThree();
				} else if (playerCount == 2) {
					values = type.getValuesTwo();
				}
				
				for (int value : values) {
					DedicationToken token = new DedicationToken();
					token.init(value, type);
					dedicationStack.getStack().push(token);
				}
				
			}
		} else {
			throw new IllegalArgumentException("Stack of dedications to be populated is invalid!");
		}
	}
	
	
	@Override
	public void distributeInitialLanterns(final List<LakeTile> lake, final LanternCardWrapper[] cards, final Player[] players) {
		if ((lake != null) && (!lake.isEmpty())) {
			LakeTile firstTile = lake.get(0);
			List<Colour> colours = Arrays.asList(Colour.values());
			for ( int i = 0 ; i < players.length ; i++) {
				// first tile will always be oriented to first player, so can use normal index to find colour
				Colour color = firstTile.getSides()[i].getColour();
				int colourIndex = colours.indexOf(color);
				LanternCardWrapper cardWrapper = cards[colourIndex];
				players[i].getCards()[colourIndex].setQuantity(1);
				cardWrapper.setQuantity(cardWrapper.getQuantity() - 1);
			}
		} else {
			throw new IllegalArgumentException("Lake does not contain a first tile!");
		}
	}

}
