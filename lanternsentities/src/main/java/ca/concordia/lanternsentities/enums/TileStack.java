package ca.concordia.lanternsentities.enums;

import static ca.concordia.lanternsentities.enums.Colour.BLACK;
import static ca.concordia.lanternsentities.enums.Colour.BLUE;
import static ca.concordia.lanternsentities.enums.Colour.GREEN;
import static ca.concordia.lanternsentities.enums.Colour.ORANGE;
import static ca.concordia.lanternsentities.enums.Colour.PURPLE;
import static ca.concordia.lanternsentities.enums.Colour.RED;
import static ca.concordia.lanternsentities.enums.Colour.WHITE;
import ca.concordia.lanternsentities.LakeTile;

public enum TileStack {
	// sheet 1
	T11("1-1", GREEN, BLUE, BLACK, ORANGE, false), T12("1-2", WHITE, BLACK, ORANGE, BLACK, false), T13("1-3", GREEN, GREEN,
			GREEN, RED, false), T14("1-4", ORANGE, PURPLE, ORANGE, ORANGE, false), T15("1-5", RED, BLACK, GREEN, PURPLE, false), T16(
			"1-6", RED, GREEN, BLUE, PURPLE, false),
	// sheet 2
	T21("2-1", WHITE, GREEN, ORANGE, GREEN, true), T22("2-2", RED, GREEN, BLACK, RED, false), T23("2-3", WHITE, BLUE, BLACK,
			BLUE, true), T24("2-4", GREEN, RED, ORANGE, WHITE, false), T25("2-5", BLUE, PURPLE, WHITE, RED, false), T26("2-6",
			RED, GREEN, GREEN, RED, true),
	// sheet 3
	T31("3-1", BLACK, PURPLE, BLUE, BLUE, false), T32("3-2", WHITE, WHITE, RED, GREEN, false), T33("3-3", ORANGE, PURPLE, WHITE,
			WHITE, true), T34("3-4", PURPLE, PURPLE, RED, GREEN, true), T35("3-5", GREEN, BLUE, ORANGE, BLUE, false), T36("3-6",
			BLUE, RED, BLACK, GREEN, false),
	// sheet 4
	T41("4-1", BLUE, BLACK, WHITE, GREEN, false), T42("4-2", RED, BLACK, BLACK, PURPLE, true), T43("4-3", RED, BLACK, RED,
			ORANGE, true), T44("4-4", PURPLE, BLUE, PURPLE, GREEN, true), T45("4-5", ORANGE, BLUE, WHITE, ORANGE, true), T46(
			"4-6", BLUE, RED, WHITE, BLACK, false),
	// sheet 5
	T51("5-1", BLUE, WHITE, GREEN, PURPLE, false), T52("5-2", ORANGE, BLUE, ORANGE, BLUE, true), T53("5-3", BLACK, WHITE, BLACK,
			WHITE, true), T54("5-4", BLUE, RED, WHITE, BLACK, false), T55("5-5", PURPLE, BLACK, WHITE, ORANGE, false), T56("5-6",
			RED, ORANGE, PURPLE, WHITE, false),
	// sheet 6
	T61("6-1", PURPLE, PURPLE, BLACK, PURPLE, false), T62("6-2", ORANGE, BLACK, PURPLE, RED, false), T63("6-3", BLUE, WHITE,
			ORANGE, RED, false), T64("6-4", BLUE, PURPLE, BLACK, ORANGE, false), T65("6-5", RED, WHITE, BLACK, ORANGE, false), T66(
			"6-6", WHITE, GREEN, PURPLE, BLUE, false);

	private final String name;
	private final LakeTile tile;

	TileStack(String name, Colour colour1, Colour colour2, Colour colour3, Colour colour4, boolean platform) {
		this.name = name;
		this.tile = new LakeTile();
		tile.init(new Colour[] { colour1, colour2, colour3, colour4 }, platform);
	}

	public String getName() {
		return name;
	}

	/**
	 * Get a safe copy of the TileStack tile
	 *
	 * @return the tile
	 */
	public LakeTile getTile() {
		LakeTile copy = new LakeTile();
		Colour[] colours = new Colour[this.tile.getSides().length];
		for (int i = 0; i < colours.length; i++) {
			colours[i] = this.tile.getSides()[i].getColour();
		}
		copy.init(colours, this.tile.isPlatform());
		return copy;
	}

}
