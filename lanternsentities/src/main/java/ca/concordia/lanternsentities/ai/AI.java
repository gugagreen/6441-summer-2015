package ca.concordia.lanternsentities.ai;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.AIType;

/**
 * AI class allows each player to have the choice of 4 specific behaviors if they are a computer player,
 * or to be controlled by a human player.
 */
@XmlRootElement
public class AI {
	protected DedicationBehavior dedicationBehavior;
	protected ExchangeBehavior exchangeBehavior;
	protected TilePlayBehavior tilePlayBehavior;
	protected Game game;
	protected Player player;
	private AIType type;
	
	public AI() {
	}
	
	public AI(AIType type, Game game, Player currentPlayer){
		this.game = game;
		this.player = currentPlayer;
		this.type = type;
	}
	
	/**
	 * Each player must have the choice to perform a lantern card exchange when the appropriate 
	 * number of favor tokens is in their inventory.
	 */
	public void performExchange() {
		//Players require a minimum of 2 favors to make an exchange
		if (player.getFavors() > 1){
			this.exchangeBehavior.makeExchange(game, player);
		}
	}
	
	/**
	 * Each player must have the choice to perform a dedication when the appropriate 
	 * number of lantern cards is in their inventory.
	 */
	public void performDedication() {
		boolean[] dedicationsPossible = DedicationForecaster.getInstance().dedicationPossible(player);
		
		//checking if any of the 3 types of dedications are possible
		if(dedicationsPossible[0] || dedicationsPossible[1] || dedicationsPossible[2]) {
			this.dedicationBehavior.makeDedication(game, player, dedicationsPossible);
		}
	}
	
	/**
	 * Each player must perform a lake tile play every turn.
	 */
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

	@XmlTransient
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@XmlElement
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public AIType getType() {
		return type;
	}

	public void setType(AIType type) {
		this.type = type;
	}
	
	
}
