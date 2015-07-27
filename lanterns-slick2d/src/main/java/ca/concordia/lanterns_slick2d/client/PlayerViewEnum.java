package ca.concordia.lanterns_slick2d.client;

public enum PlayerViewEnum {

	P1(true, 930, 30), P2(false, 340, 650), P3(true, 200, 30), P4(false, 340, 10); 
	
	public final boolean vertical;
	public final int x;
	public final int y;
	
	private PlayerViewEnum(boolean vertical, int x, int y) {
		this.vertical = vertical;
		this.x = x;
		this.y = y;
	}
	
}
