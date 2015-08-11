package ca.concordia.lanterns.tileplacement.impl;

import java.util.Random;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.ai.TilePlayBehavior;

public class UnpredictableTile implements TilePlayBehavior {
	
	TilePlayBehavior currentBehavior;
	
	@Override
	public void placeTile(Game game, Player currentPlayer) {
		switch (selectBehavior()) {
		case 0:
			this.currentBehavior = new RandomTile();
			break;

		case 1:
			this.currentBehavior = new GreedyTile();
			break;
			
		case 2:
			this.currentBehavior = new WorstTile();
			break;
		}
		
		
		this.currentBehavior.placeTile(game, currentPlayer);
	}

	private int selectBehavior(){
		Random rand = new Random();
		//TODO change nextInt to 3 once worst dedication is implemented
		int possibleBehaviorIndex = rand.nextInt(2);
		
		return possibleBehaviorIndex;
	}

	

}
