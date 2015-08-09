package ca.concordia.lanterns.ai;

import ca.concordia.lanterns.dedication.DedicationBehavior;
import ca.concordia.lanterns.exchange.ExchangeBehavior;
import ca.concordia.lanterns.tileplacement.TilePlayBehavior;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;

public abstract class AI {
	protected DedicationBehavior dedicationBehavior;
	protected ExchangeBehavior exchangeBehavior;
	protected TilePlayBehavior tilePlayBehavior;
	protected Game game;
	protected Player currentPlayer;
	
	public AI(Game game, Player currentPlayer){
		this.game = game;
		this.currentPlayer = currentPlayer;
	}
	
	public void performExchange(){
		//Players require a minimum of 2 favors to make an exchange
		if (currentPlayer.getFavors() > 1){
			this.exchangeBehavior.makeExchange(game, currentPlayer);
		}
	}
	
	public void performDedication(){
		boolean[] dedicationsPossible = dedicationPossible(currentPlayer);
		//checking if any of the 3 types of dedications are possible
		if(dedicationsPossible[0]||dedicationsPossible[1]||dedicationsPossible[2]){
			this.dedicationBehavior.makeDedication(game, currentPlayer, dedicationsPossible);
		}
	}
	
	public void performTilePlay(){
		this.tilePlayBehavior.placeTile(game, currentPlayer);
	}
	
	
	public void setExchangeBehavior(ExchangeBehavior exchangeMood){
		this.exchangeBehavior = exchangeMood;
	}
	
	public void setDedicationBehavior(DedicationBehavior dedicationMood){
		this.dedicationBehavior = dedicationMood;
	}
	
	public void setTilePlayBehavior(TilePlayBehavior tilePlayMood){
		this.tilePlayBehavior = tilePlayMood;
	}
	
	public static boolean[] dedicationPossible(Player player){
		boolean[] dedicationsPossible = new boolean[3];
		
		dedicationsPossible[0] = fourOfKindPossible(player);
		dedicationsPossible[1] = threePairPossible(player);
		dedicationsPossible[2] = sevenUniquePossible(player);
		return dedicationsPossible;

	}
	
	public static boolean fourOfKindPossible(Player player){
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if(lanternCardWrapper.getQuantity()>3){
				boolean dedicationPossible = true;
				return dedicationPossible;
			}
		}
		boolean dedicationPossible = false;
		return dedicationPossible;
		
	}
	
	public static boolean threePairPossible(Player player){
		int pairStacks = 0;
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if(lanternCardWrapper.getQuantity()>1){
				pairStacks++;
			}
		}
		if(pairStacks>2){
			boolean dedicationPossible = true;
			return dedicationPossible;
		}
		else{
			boolean dedicationPossible = false;
			return dedicationPossible;
		}
	}
	
	public static boolean sevenUniquePossible(Player player){
		int uniqueStacks = 0;
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if(lanternCardWrapper.getQuantity()>0){
				uniqueStacks++;
			}
		}
		if(uniqueStacks==7){
			boolean dedicationPossible = true;
			return dedicationPossible;
		}
		else{
			boolean dedicationPossible = false;
			return dedicationPossible;
		}
	}
}
