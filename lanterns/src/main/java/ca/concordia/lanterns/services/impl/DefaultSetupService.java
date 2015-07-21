package ca.concordia.lanterns.services.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.DedicationTokenWrapper;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Lake;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;
import ca.concordia.lanternsentities.enums.PlayerID;
import ca.concordia.lanterns.services.SetupService;

public class DefaultSetupService implements SetupService {

	/**
	 * Create game creates game using player name
	 */
	@Override
	public Game createGame(String[] playerNames) {
		validatePlayersSet(playerNames);
		int playerCount = playerNames.length;
		
		Game game = new Game();
		game.init(playerNames);
		LakeTile[] totalTiles = generateTiles(playerCount);
		
		startLake(game.getLake(), totalTiles[0], game.getPlayers().length);
		dealPlayerTiles(totalTiles, game.getPlayers());
		drawTileStack(game.getTiles(), totalTiles, playerCount);
		separateLanternCards(game.getCards(), playerCount);
		setDedicationTokens(game.getDedications(), playerCount);
		distributeInitialLanterns(game.getLake(), game.getCards(), game.getPlayers());
		decideFirstPlayer (game) ;
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

	/**
	 * Generates tile stack depending on the number of players in the game.
	 * 
	 * @param playerCount
	 *            number of players in the game.
	 * @return Array of tiles.
	 */
	protected LakeTile[] generateTiles(final int playerCount) {
		LakeTile[] totalTiles = null;

		// TODO - maybe move quantity for each player count to properties/enum
		// TODO - maybe just create a single big stack of tiles, and let splitting per player on step 3
		switch (playerCount) {
		case 4:
			// 20 + 12 (3 tiles per player) + 1 initial tile
			totalTiles = new LakeTile[33];
			break;
		case 3:
			// 18 + 9 (3 tiles per player) + 1 initial tile
			totalTiles = new LakeTile[28];
			break;
		case 2:
			// 16 + 6 (3 tiles per player) + 1 initial tile
			totalTiles = new LakeTile[23];
			break;
		default:
			break;
		}

		// if total tiles is valid, populate it
		if (totalTiles != null) {
			
			// Start tile
			Colour[] startTileColour = new Colour[]{Colour.RED, Colour.BLACK, Colour.BLUE, Colour.WHITE } ;
			totalTiles[0] = new LakeTile () ;
			totalTiles[0].init(startTileColour, false);
			
			Random random = new Random();
			Colour[] colours = Colour.values();
			for (int i = 1; i < totalTiles.length; i++) {
				Colour[] tileColours = new Colour[LakeTile.TOTAL_SIDES];
				for (int j = 0; j < tileColours.length; j++) {
					int nextColour = random.nextInt(colours.length);
					tileColours[j] = colours[nextColour];
				}
				boolean platform = random.nextBoolean();
				totalTiles[i] = new LakeTile();
				totalTiles[i].init(tileColours, platform);
			}
		}

		return totalTiles;
	}

	@Override
	public void startLake(final Lake lake, final LakeTile initialTile, int playerCount) {
		LinkedList<PlayerID> id = new LinkedList<PlayerID>(Arrays.asList(PlayerID.values())) ;
		PlayerID[] orientation = new PlayerID[id.size()] ;
		Random random = new Random () ;
		int index = random.nextInt(playerCount) ;
		orientation[0] = id.get(index) ;
		id.remove(index) ;
		for ( int i = 1; i != orientation.length; ++i ) {
			index = random.nextInt(id.size()) ;
			orientation[i] =  id.get(index);
			id.remove(index) ;
		}
		lake.placeTile(initialTile, orientation);
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
	public void distributeInitialLanterns(final Lake lake, final LanternCardWrapper[] cards, final Player[] players) {
		if ((lake != null) && (lake.getTiles() != null) && (!lake.getTiles().isEmpty())) {
			LakeTile firstTile = lake.getTiles().get(0);
			List<Colour> colours = Arrays.asList(Colour.values());
			for ( int i = 0 ; i != players.length ;++i ) {
				Colour color = firstTile.getOrientation(players[i].getID()).getColour() ;
				int colourIndex = colours.indexOf(color);
				LanternCardWrapper cardWrapper = cards[colourIndex];
				players[i].getCards()[colourIndex].setQuantity(1);
				cardWrapper.setQuantity(cardWrapper.getQuantity() - 1);
			}
		} else {
			throw new IllegalArgumentException("Lake does not contain a first tile!");
		}
	}
	
	@Override
	public void decideFirstPlayer ( Game game) {
		LakeTile startTile = game.getLake().getTiles().get(0) ;
		HashMap<PlayerID, TileSide> orientation = startTile.getOrientation() ;
		for ( PlayerID id : PlayerID.values() ) {
			if ( orientation.get(id).getColour() == Colour.RED ) {
				game.setCurrentTurnPlayer(id);
				game.setStartPlayerMarker(id);
				break ;
			}
		}
		
	}

}
