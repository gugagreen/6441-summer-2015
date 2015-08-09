package ca.concordia.lanterns.exchange.impl;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.exchange.ExchangeBehavior;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.ui.GameCommandClient;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.Colour;

// FIXME - move UI part to GameCommandClient
public class PlayerExchange implements ExchangeBehavior {

	@Override
	public void makeExchange(Game game, Player currentPlayer) {
        int doExchange = 2;
        
        GameCommandClient.displayPlayerLanterns(game, currentPlayer.getId());
        doExchange = GameCommandClient.getValidInt("Do you want to make an exchange?\n1) Yes\n2) No", 1, 2);

        if (doExchange == 1) {
            System.out.println("Select one card to give:");
            int giveCardIndex = GameCommandClient.getValidInt(GameCommandClient.coloursWithIndexesString(), 0, 6);
            Colour giveCard = Colour.values()[giveCardIndex];
            System.out.println("Select one card to receive:");
            int receiveCardIndex = GameCommandClient.getValidInt(GameCommandClient.coloursWithIndexesString(), 0, 6);
            Colour receiveCard = Colour.values()[receiveCardIndex];
            try {
            	ActivePlayerService.getInstance().exchangeLanternCard(game, currentPlayer.getId(), giveCard, receiveCard);
            } catch (GameRuleViolationException e) {
                System.err.println(e.getMessage());
            }
        }
	}
}
