package ca.concordia.lanterns.services.impl;

import ca.concordia.lanternsentities.*;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

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
        //... validate content
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
        List<LakeTile> lake = new ArrayList<LakeTile>();
        Colour[] colours = {Colour.RED, Colour.BLACK, Colour.BLUE, Colour.WHITE};
        LakeTile initialTile = new LakeTile();
        initialTile.init(colours, false);
        service.startLake(lake, initialTile);

        assertEquals(1, lake.size());
        assertEquals(initialTile, lake.get(0));
    }

    @Test
    public void testDealPlayerTiles() {
        final Player[] players = createPlayers(3);
        final LakeTile[] totalTiles = new LakeTile[36];
        Colour[] colours = {Colour.RED, Colour.BLACK, Colour.BLUE, Colour.WHITE};
        for (int i = 0; i < totalTiles.length; i++) {
            totalTiles[i] = new LakeTile();
            totalTiles[i].init(colours, false);
        }

        service.dealPlayerTiles(totalTiles, players);
        for (Player player : players) {
            assertEquals(3, player.getTiles().size());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDealPlayerTilesWithNoTiles() {
        final Player[] players = createPlayers(3);
        assertNotNull(players);
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

    @Test
    public void testSeparateLanternCardsFourPlayers() {
        final LanternCardWrapper[] cards = new LanternCardWrapper[Colour.values().length];
        for (int i = 0; i < cards.length; i++) {
            LanternCardWrapper lantern = new LanternCardWrapper();
            lantern.setColour(Colour.values()[i]);
            cards[i] = lantern;
        }

        service.separateLanternCards(cards, 4);
        assertNotNull(cards);
        for (LanternCardWrapper wrapper : cards) {
            assertEquals(8, wrapper.getQuantity());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSeparateLanternCardsWrongStack() {
        final LanternCardWrapper[] cards = new LanternCardWrapper[Colour.values().length + 1];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new LanternCardWrapper();
        }

        service.separateLanternCards(cards, 4);
        assertNotNull(cards);
    }

    @Test
    public void testSetDedicationTokensFourPlayers() {
        final DedicationTokenWrapper[] dedications = new DedicationTokenWrapper[DedicationType.values().length];
        for (int i = 0; i < dedications.length; i++) {
            dedications[i] = new DedicationTokenWrapper();
        }

        service.setDedicationTokens(dedications, 4);
        assertTrue(dedications.length > 0);
        for (int i = 0; i < dedications.length; i++) {
            Stack<DedicationToken> dedication = dedications[i].getStack();
            assertNotNull(dedication);
            assertEquals(DedicationType.values()[i].getValuesFour().length, dedication.size());
        }
    }

    @Test
    public void testDistributeInitialLanterns() {
        List<Colour> colourValues = Arrays.asList(Colour.values());
        Colour[] colours = {Colour.RED, Colour.BLACK, Colour.BLUE, Colour.WHITE};

        final List<LakeTile> lake = new ArrayList<LakeTile>();
        LakeTile initialTile = new LakeTile();
        initialTile.init(colours, false);

        service.startLake(lake, initialTile);

        final LanternCardWrapper[] cards = new LanternCardWrapper[colourValues.size()];

        for (int i = 0; i < cards.length; i++) {
            LanternCardWrapper wrapper = cards[i];
            if (wrapper == null) {
                wrapper = new LanternCardWrapper();
                wrapper.setColour(Colour.values()[i]);
                wrapper.setQuantity(1);
            } else {
                wrapper.setQuantity(wrapper.getQuantity() + 1);
            }
            cards[i] = wrapper;
        }

        Player[] players = createPlayers(2);

        service.distributeInitialLanterns(lake, cards, players);

        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            Colour colour = lake.get(0).getSides()[i].getColour();

            final LanternCardWrapper[] playerCards = player.getCards();

            for (int j = 0; j < playerCards.length; j++) {
                LanternCardWrapper colourStack = playerCards[j];
                if (j == colourValues.indexOf(colour)) {
                    assertEquals(1, colourStack.getQuantity());
                } else {
                    assertEquals(0, colourStack.getQuantity());

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
            players[i] = new Player();
            players[i].init(Integer.toString(i), i);
        }
        return players;
    }
}
