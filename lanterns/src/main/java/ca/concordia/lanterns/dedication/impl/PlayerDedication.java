package ca.concordia.lanterns.dedication.impl;

import ca.concordia.lanterns.controllers.GameController;
import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.enums.DedicationCost;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.ui.GameCommandClient;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.DedicationBehavior;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * This class performs the  dedications for Human player.
 */
public class PlayerDedication implements DedicationBehavior {
	
	private GameController controller = new GameController();
	/**
     * Ask human player to make a dedication if player wants to do and perform dedication.
     *
     * @param The {@link Game} object.
      * @param The {@link Player} object.
       * @param The Boolean array of all possible dedications. 
     */
	@Override
	public void makeDedication(Game game, Player currentPlayer, boolean[] dedicationsPossible) {
		GameCommandClient.displayPlayerLanterns(game, currentPlayer.getId());
		int doDedication = GameCommandClient.getValidInt("Do you want to make a dedication?\n1) Yes\n2) No", 1, 2);

		if (doDedication == 1) {
			System.out.println("Select one dedication type:");
			int typeIndex = GameCommandClient.getValidInt(GameCommandClient.dedicationTypesString(), 0, 3);
			DedicationType type = DedicationType.values()[typeIndex];
			int requiredColours = getRequiredColors(type);
			Colour[] colours = new Colour[requiredColours];
			for (int i = 0; i < colours.length; i++) {
				System.out.println("Select one colour:");
				int giveCardIndex = GameCommandClient.getValidInt(GameCommandClient.coloursWithIndexesString(), 0, 6);
				colours[i] = Colour.values()[giveCardIndex];
			}
			try {
				controller.makeDedication(game, currentPlayer.getId(), type, colours);
			} catch (GameRuleViolationException e) {
				System.err.println(e.getMessage());
			}
		}

	}
	
	 /**
     * Gives the required colors to make dedications.
     * 
     * @param resource save game name
     * @param DedicationType object.
     */
	public int getRequiredColors(DedicationType dedicationType) {
		DedicationCost cost = controller.getDedicationCost(dedicationType);
		return cost.getRequiredColors();
	}
}
