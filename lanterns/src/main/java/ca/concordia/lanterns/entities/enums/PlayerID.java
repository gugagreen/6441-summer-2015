package ca.concordia.lanterns.entities.enums;

public enum PlayerID {
	ONE(1),TWO(2),THREE(3),FOUR(4) ;
	
	private int id ;
	
	private PlayerID ( int id ) {
		this.id = id ;
	}
	
	public int getID() {
		return this.id ;
	}
}
