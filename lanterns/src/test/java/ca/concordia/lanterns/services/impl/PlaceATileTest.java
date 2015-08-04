package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.TileStack;
import ca.concordia.lanternsentities.helper.MatrixOrganizer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ca.concordia.lanternsentities.enums.Colour.BLACK;
import static ca.concordia.lanternsentities.enums.Colour.BLUE;
import static ca.concordia.lanternsentities.enums.Colour.GREEN;
import static ca.concordia.lanternsentities.enums.Colour.ORANGE;
import static ca.concordia.lanternsentities.enums.Colour.PURPLE;
import static ca.concordia.lanternsentities.enums.Colour.RED;
import static ca.concordia.lanternsentities.enums.Colour.WHITE;
import static  ca.concordia.lanternsentities.helper.MatrixOrganizer.Direction.*;
import static org.junit.Assert.*;

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

        T54.setOrientation(2);
        T11.setOrientation(0);
        T33.setOrientation(3);

        game.setLake(new LakeTile[][] {{T54}});
        game.setLake(MatrixOrganizer.addElement(game.getLake(), T11, NORTH, 0, 0));
        game.setLake(MatrixOrganizer.addElement(game.getLake(), T33, WEST, 0, 0));
        
        // [OPWW*][GBKO.]
        // [     ][BRWK.]
        

        // Added one tile to the stack of tiles in game.
        game.getTiles().add(TileStack.T12.getTile()); // "WKOK."

        setUpService.separateLanternCards(game.getCards(), playerCount);

        // Set purple to one so that only active player recieves it
        game.getCards()[colors.indexOf(Colour.PURPLE)].setQuantity(1);
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
            service.placeLakeTile(game, 1, 0, "BRWK.", 2, WEST.ordinal());
        } catch (GameRuleViolationException e) {
            fail("A Valid Tile placement failed");
        }
        
        lake = game.getLake();
        // [OPWW*][GBKO.]
        // [RKKP*][BRWK.]
        westZerothTile = MatrixOrganizer.findTile(lake, "BRWK.").getSides()[WEST.ordinal()];
        southSecondTile = MatrixOrganizer.findTile(lake, "OPWW*").getSides()[SOUTH.ordinal()];

        assertSame(westZerothTile.getAdjacent(), playerTile);
        assertSame(southSecondTile.getAdjacent(), playerTile);
        assertSame(playerTile.getSides()[NORTH.ordinal()].getAdjacent(), MatrixOrganizer.findTile(lake, "OPWW*"));
        assertSame(playerTile.getSides()[EAST.ordinal()].getAdjacent(), MatrixOrganizer.findTile(lake, "BRWK."));

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

        assertSame(MatrixOrganizer.findTile(game.getLake(), "RBBP*"), playerTile);
        assertSame(topTile, player.getTiles().get(0));
        assertTrue(game.getTiles().isEmpty());
    }

}
