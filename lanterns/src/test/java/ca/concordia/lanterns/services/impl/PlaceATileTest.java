package ca.concordia.lanterns.services.impl;

import static ca.concordia.lanternsentities.enums.Colour.BLACK;
import static ca.concordia.lanternsentities.enums.Colour.PURPLE;
import static ca.concordia.lanternsentities.enums.Colour.RED;
import static ca.concordia.lanternsentities.helper.MatrixOrganizer.Direction.EAST;
import static ca.concordia.lanternsentities.helper.MatrixOrganizer.Direction.NORTH;
import static ca.concordia.lanternsentities.helper.MatrixOrganizer.Direction.SOUTH;
import static ca.concordia.lanternsentities.helper.MatrixOrganizer.Direction.WEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

public class PlaceATileTest {

    private static final List<Colour> colors = Arrays.asList(Colour.values());
    private Game game;

    @Before
    public void setUp() {
        DefaultSetupService setUpService = new DefaultSetupService();

        String[] playerNames = {"a", "b", "c", "d"};

        // Initalize a new game by using some of the services of default setup but not all of them.
        setUpService.validatePlayersSet(playerNames);
        String[] sortedPlayerNames = setUpService.decidePlayersOrder(playerNames);
        int playerCount = sortedPlayerNames.length;
        game = new Game();
        //Initialize player data structures as well as favors for game
        game.init(sortedPlayerNames, "Test");

        // Custom initialization of lake
        LakeTile T54 = TileStack.T54.getTile(); // "BRWK."
        LakeTile T11 = TileStack.T11.getTile(); // "GBKO."
        LakeTile T33 = TileStack.T33.getTile(); // "OPWW*"

        T54.setOrientation(3);
        T11.setOrientation(0);
        T33.setOrientation(3);

        game.setLake(new LakeTile[][] {{T54}});
        game.setLake(MatrixOrganizer.addElement(game.getLake(), T11, NORTH, 0, 0));
        game.setLake(MatrixOrganizer.addElement(game.getLake(), T33, WEST, 0, 0));
        // lake at this point
        // [OPWW*][GBKO.]
        // [     ][BRWK.]

        // Added one tile to the stack of tiles in game.
        game.getTiles().add(TileStack.T12.getTile()); // "WKOK."

        setUpService.separateLanternCards(game.getCards(), playerCount);

        // Set purple to one so that only active player recieves it
        game.getCards()[colors.indexOf(PURPLE)].setQuantity(1);
        game.setCurrentTurnPlayer(1);

        // Custom tile for current Player
        game.getPlayers()[1].getTiles().add(TileStack.T42.getTile()); // "RKKP*"
    }

    @Test
    public void validPlaceATile() {

        Player player = game.getPlayers()[1];
        // Record Data structures before placing a tile
        LakeTile[][] lake = game.getLake();
        // [OPWW*][GBKO.]
        // [     ][BRWK.]

        LakeTile topTile = game.getTiles().peek();

        TileSide westZerothTile = MatrixOrganizer.findTile(lake, "BRWK.").getSides()[WEST.ordinal()];
        TileSide southSecondTile = MatrixOrganizer.findTile(lake, "OPWW*").getSides()[SOUTH.ordinal()];
        assertNull(westZerothTile.getAdjacent());
        assertNull(southSecondTile.getAdjacent());

        int purpleQuantity = getGameColourQty(game, PURPLE);
        int redQuantity = getGameColourQty(game, RED);
        int blackQuantity = getGameColourQty(game, BLACK);
        int favorQuantity = game.getFavors();

        LakeTile playerTile = player.getTiles().get(0);
        for (int i = 0; i != LakeTile.TOTAL_SIDES; ++i) {
            assertNull(playerTile.getSides()[i].getAdjacent());
        }

        ActivePlayerService service = new ActivePlayerService();
        try {
            service.placeLakeTile(game, 1, 0, "BRWK.", WEST.ordinal(), EAST.ordinal());
        } catch (GameRuleViolationException e) {
            fail("A Valid Tile placement failed");
        }
        
        lake = game.getLake();
        // [OPWW*][GBKO.]
        // [RKKP*][BRWK.]
        
        // [  W  ][  O  ]
        // [P * W][K . G]
        // [  O  ][  B  ]
        
        // [  P  ][  W  ]
        // [K * R][R . K]
        // [  K  ][  B  ]
        
        westZerothTile = MatrixOrganizer.findTile(lake, "BRWK.").getSides()[WEST.ordinal()];
        southSecondTile = MatrixOrganizer.findTile(lake, "OPWW*").getSides()[SOUTH.ordinal()];

        assertSame(westZerothTile.getAdjacent(), playerTile);
        assertSame(southSecondTile.getAdjacent(), playerTile);
        assertSame(playerTile.getSides()[NORTH.ordinal()].getAdjacent(), MatrixOrganizer.findTile(lake, "OPWW*"));
        assertSame(playerTile.getSides()[EAST.ordinal()].getAdjacent(), MatrixOrganizer.findTile(lake, "BRWK."));

        assertEquals(purpleQuantity - 1, getGameColourQty(game, PURPLE));
        assertEquals(redQuantity - 2, getGameColourQty(game, RED));
        assertEquals(blackQuantity - 2, getGameColourQty(game, BLACK));
        assertEquals(favorQuantity - 1, game.getFavors());

        assertEquals(1, getPlayerColourQty(game, player.getId(), BLACK));
        assertEquals(1, getPlayerColourQty(game, player.getId(), RED));
        assertEquals(1, player.getFavors());

        assertEquals(getPlayerColourQty(game, 2, BLACK), 1);
        assertEquals(getPlayerColourQty(game, 3, PURPLE), 1);
        assertEquals(getPlayerColourQty(game, 0, RED), 1);

        assertSame(MatrixOrganizer.findTile(game.getLake(), "RKKP*"), playerTile);
        assertSame(topTile, player.getTiles().get(0));
        assertTrue(game.getTiles().isEmpty());
    }
    
    private int getGameColourQty(Game game, Colour colour) {
    	return game.getCards()[colour.ordinal()].getQuantity();
    }
    
    private int getPlayerColourQty(Game game, int playerId, Colour colour) {
    	return game.getPlayers()[playerId].getCards()[colour.ordinal()].getQuantity();
    }

}
