package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.GameStubber;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExchangeLanternCardTest {

    private static final Colour[] colors = Colour.values();
    private Game game;
    private PlayerService service;

    @Before
    public void setUp() {
        DefaultSetupService service = new DefaultSetupService();

        this.game = service.createGame(GameStubber.getPlayerNames(), GameStubber.getAITypes());
        this.service = new ActivePlayerService();

        game.setFavors(16);
        LanternCardWrapper[] cards = game.getCards();
        cards[colors.length - 1].setQuantity(0);

        Player firstPlayer = game.getPlayers()[0];
        firstPlayer.setFavors(4);
        cards = firstPlayer.getCards();
        cards[0].setQuantity(1);
        cards[1].setQuantity(2);
    }

    @Test
    public void validExchangeLanternCard() {
        Player firstPlayer = game.getPlayers()[0];

        int playerQuantity1 = firstPlayer.getCards()[0].getQuantity();
        int playerQuantity2 = firstPlayer.getCards()[3].getQuantity();
        int playerFavor = firstPlayer.getFavors();

        int gameQuantity1 = game.getCards()[0].getQuantity();
        int gameQuantity2 = game.getCards()[3].getQuantity();
        int gameFavor = game.getFavors();

        try {
            service.exchangeLanternCard(game, 0, colors[0], colors[3]);
        } catch (GameRuleViolationException e) {
            fail("A valid exchange of lanterns failed");
        }

        assertEquals(playerQuantity1 - 1, firstPlayer.getCards()[0].getQuantity());
        assertEquals(playerQuantity2 + 1, firstPlayer.getCards()[3].getQuantity());
        assertEquals(playerFavor - 2, firstPlayer.getFavors());

        assertEquals(gameQuantity1 + 1, game.getCards()[0].getQuantity());
        assertEquals(gameQuantity2 - 1, game.getCards()[3].getQuantity());
        assertEquals(gameFavor + 2, game.getFavors());
    }

    @Test
    public void emptyFavorTokens() {
        Player firstPlayer = game.getPlayers()[0];

        firstPlayer.setFavors(1);

        int playerQuantity1 = firstPlayer.getCards()[0].getQuantity();
        int playerQuantity2 = firstPlayer.getCards()[3].getQuantity();
        int playerFavor = firstPlayer.getFavors();

        int gameQuantity1 = game.getCards()[0].getQuantity();
        int gameQuantity2 = game.getCards()[3].getQuantity();
        int gameFavor = game.getFavors();

        try {
            service.exchangeLanternCard(game, 0, colors[0], colors[3]);
            fail("Exchange of lantern cards should be forbidden when player have less than two "
                    + "favour tokens.");
        } catch (GameRuleViolationException e) {
            String message = "You do not have enough favour tokens to make this exchange.";
            assertEquals(message, e.getMessage());
        }

        assertEquals(playerQuantity1, firstPlayer.getCards()[0].getQuantity());
        assertEquals(playerQuantity2, firstPlayer.getCards()[3].getQuantity());
        assertEquals(playerFavor, firstPlayer.getFavors());

        assertEquals(gameQuantity1, game.getCards()[0].getQuantity());
        assertEquals(gameQuantity2, game.getCards()[3].getQuantity());
        assertEquals(gameFavor, game.getFavors());
    }

    @Test
    public void emptyGameSupply() {
        assertEquals(game.getCards()[colors.length - 1].getQuantity(), 0);

        int playerQuantity1 = game.getPlayers()[0].getCards()[0].getQuantity();
        int playerQuantity2 = game.getPlayers()[0].getCards()[colors.length - 1].getQuantity();
        int playerFavours = game.getPlayers()[0].getFavors();

        int gameQuantity1 = game.getCards()[0].getQuantity();
        int gameFavours = game.getFavors();

        try {
            service.exchangeLanternCard(game, 0, colors[0], colors[colors.length - 1]);
            fail("The exchange should be forbidden when supply of lantern card inis empty");
        } catch (GameRuleViolationException e) {
            String message = colors[colors.length - 1].toString() +
                    " colored lantern cards are out of stock. Hence, you can't make this exchange";
            assertEquals(message, e.getMessage());
        }

        assertEquals(game.getCards()[colors.length - 1].getQuantity(), 0);

        assertEquals(playerQuantity1, game.getPlayers()[0].getCards()[0].getQuantity());
        assertEquals(playerQuantity2, game.getPlayers()[0].getCards()[colors.length - 1].getQuantity());
        assertEquals(playerFavours, game.getPlayers()[0].getFavors());

        assertEquals(gameQuantity1, game.getCards()[0].getQuantity());
        assertEquals(gameFavours, game.getFavors());
    }

    @Test
    public void emptyPlayerStock() {
        assertEquals(game.getPlayers()[0].getCards()[2].getQuantity(), 0);

        int playerQuantity = game.getPlayers()[0].getCards()[1].getQuantity();
        int playerFavours = game.getPlayers()[0].getFavors();

        int gameQuantity1 = game.getCards()[2].getQuantity();
        int gameQuantity2 = game.getCards()[1].getQuantity();
        int gameFavours = game.getFavors();

        try {
            service.exchangeLanternCard(game, 0, colors[2], colors[1]);
            fail("The exchange should be forbidden when stock of lantern card available to the "
                    + "player is empty");
        } catch (GameRuleViolationException e) {
            String message = "You do not have " + colors[2].toString() +
                    " colored lantern card." + " Hence, you can't make this exchange";
            assertEquals(message, e.getMessage());
        }

        assertEquals(game.getPlayers()[0].getCards()[2].getQuantity(), 0);
        assertEquals(playerQuantity, game.getPlayers()[0].getCards()[1].getQuantity());
        assertEquals(playerFavours, game.getPlayers()[0].getFavors());

        assertEquals(gameQuantity1, game.getCards()[2].getQuantity());
        assertEquals(gameQuantity2, game.getCards()[1].getQuantity());
        assertEquals(gameFavours, game.getFavors());
    }

}
