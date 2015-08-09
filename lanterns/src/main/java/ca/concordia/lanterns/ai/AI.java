package ca.concordia.lanterns.ai;

import ca.concordia.lanterns.dedication.DedicationBehavior;
import ca.concordia.lanterns.dedication.DedicationForecaster;
import ca.concordia.lanterns.exchange.ExchangeBehavior;
import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public abstract class AI {
	protected DedicationBehavior dedicationBehavior;
	protected ExchangeBehavior exchangeBehavior;
	protected TilePlayBehavior tilePlayBehavior;
	protected Game game;
	protected Player player;
	
	public AI(Game game, Player currentPlayer){
		this.game = game;
		this.player = currentPlayer;
	}
	
	public void performExchange() {
		//Players require a minimum of 2 favors to make an exchange
		if (player.getFavors() > 1){
			this.exchangeBehavior.makeExchange(game, player);
		}
	}
	
	public void performDedication() {
		boolean[] dedicationsPossible = DedicationForecaster.getInstance().dedicationPossible(player);
		//checking if any of the 3 types of dedications are possible
		if(dedicationsPossible[0] || dedicationsPossible[1] || dedicationsPossible[2]) {
			this.dedicationBehavior.makeDedication(game, player, dedicationsPossible);
		}
	}
	
	public void performTilePlay() {
		this.tilePlayBehavior.placeTile(game, player);
	}
	
	
	public void setExchangeBehavior(ExchangeBehavior exchangeMood ){
		this.exchangeBehavior = exchangeMood;
	}
	
	public void setDedicationBehavior(DedicationBehavior dedicationMood) {
		this.dedicationBehavior = dedicationMood;
	}
	
	public void setTilePlayBehavior(TilePlayBehavior tilePlayMood) {
		this.tilePlayBehavior = tilePlayMood;
	}
}
