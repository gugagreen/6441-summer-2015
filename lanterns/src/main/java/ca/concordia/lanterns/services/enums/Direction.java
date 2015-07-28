package ca.concordia.lanterns.services.enums;

import java.awt.Point;

import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.TileSide;
/**
 * This enum represents the four directions along which any {@link TileSide} can be oriented.
 * @author parth
 *
 */
public enum Direction {
	NORTH(0, 0, 1), EAST(1, 1, 0), SOUTH(2, 0, -1), WEST(3, -1, 0);
	
	/**
	 * The first integer coordinates along which this direction lies.
	 */
	private int x;
	private int y;
	
	/**
	 * Index of the tile side facing this direction.
	 * It also indicates the position of this direction within {@link Direction}
	 */
	private int tileSideIndex;
	
	private Direction (int x, int y, int tileSideIndex) {
		this.x = x;
		this.y = y;
		this.tileSideIndex = tileSideIndex;
	}
	
	public int getOppositeTileSideIndex() {
		int oppositeTileSideIndex = (this.tileSideIndex + 2) % 4;

		return oppositeTileSideIndex;
	}
	
	public void moveForward (Point currentPosition) {
		currentPosition.setLocation(currentPosition.getX() + this.x, currentPosition.getY() + this.y);
	}
	
	public Point getPoint() {
		return new Point (x,y);
	}
	
	public int getTileSideIndex() {
		return tileSideIndex;
	}
}
