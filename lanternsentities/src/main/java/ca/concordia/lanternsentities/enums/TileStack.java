package ca.concordia.lanternsentities.enums;

import static ca.concordia.lanternsentities.enums.Colour.*;
import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.TileSide;

public enum TileStack {
	// sheet 1
	T11("1-1", GREEN, BLUE, BLACK, ORANGE, false), T12("1-2", WHITE, BLACK, ORANGE, BLACK, false),
	T13("1-3", GREEN, GREEN, GREEN, RED, false), T14("1-4", ORANGE, PURPLE, ORANGE, ORANGE, false),
	T15("1-5", RED, BLACK, GREEN, PURPLE, false), T16("1-6", RED, GREEN, BLUE, PURPLE, false),
	// sheet 2
	T21("2-1", WHITE, GREEN, ORANGE, GREEN, true), T22("2-2", RED, GREEN, BLACK, RED, false),
	T23("2-3", WHITE, BLUE, BLACK, BLUE, true), T24("2-4", GREEN, RED, ORANGE, WHITE, false),
	T25("2-5", BLUE, PURPLE, WHITE, RED, false), T26("2-6", RED, GREEN, GREEN, RED, true),
	// sheet 3
	T31("3-1", BLACK, PURPLE, BLUE, BLUE, false), T32("3-2", WHITE, WHITE, RED, GREEN, false),
	T33("3-3", ORANGE, PURPLE, WHITE, WHITE, true), T34("3-4", PURPLE, PURPLE, RED, GREEN, true),
	T35("3-5", GREEN, BLUE, ORANGE, BLUE, false), T36("3-6", BLUE, RED, BLACK, GREEN, false),
	// sheet 4
	T41("4-1", BLUE, BLACK, WHITE, GREEN, false), T42("4-2", RED, BLACK, BLACK, PURPLE, true),
	T43("4-3", RED, BLACK, RED, ORANGE, true), T44("4-4", PURPLE, BLUE, PURPLE, GREEN, true),
	T45("4-5", ORANGE, BLUE, WHITE, ORANGE, true), T46("4-6", BLUE, RED, WHITE, BLACK, false),
	// sheet 5
	T51("5-1", BLUE, WHITE, GREEN, PURPLE, false),  T52("5-2", ORANGE, BLUE, ORANGE, BLUE, true),
	T53("5-3", BLACK, WHITE, BLACK, WHITE, true), T54("5-4", BLUE, RED, WHITE, BLACK, false),
	T55("5-5", PURPLE, BLACK, WHITE, ORANGE, false), T56("5-6", RED, ORANGE, PURPLE, WHITE, false),
	// sheet 6
	T61("6-1", PURPLE, PURPLE, BLACK, PURPLE, false), T62("6-2", ORANGE, BLACK, PURPLE, RED, false),
	T63("6-3", BLUE, WHITE, ORANGE, RED, false), T64("6-4", BLUE, PURPLE, BLACK, ORANGE, false),
	T65("6-5", RED, WHITE, BLACK, ORANGE, false), T66("6-6", WHITE, GREEN, PURPLE, BLUE, false);
	
	private final String name;
	private final LakeTile tile;
	
	TileStack(String name, Colour colour1, Colour colour2, Colour colour3, Colour colour4, boolean platform) {
		this.name = name;
		this.tile = new LakeTile();
		tile.init(new Colour[] {colour1, colour2, colour3, colour4}, platform);
	}

	public String getName() {
		return name;
	}

	/**
	 * Get a safe copy of the TileStack tile
	 * @return	the tile
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
	
	// FIXME - delete
	public static String[] get3Lines(TileStack ts) {
		String[] lines = new String[3];
		/*
		 * [  W  ]
		 * [K * K]
		 * [  W  ]
		 */
		if (ts == null) {
			lines[0] = "[     ]";
			lines[1] = "[     ]";
			lines[2] = "[     ]";
		} else {
			LakeTile lt = ts.getTile();
			TileSide[] ss = lt.getSides();
			char plat = lt.isPlatform() ? '*' : '.';
			lines[0] = "[  "+ss[3].getColour().key+"  ]";
			lines[1] = "["+ss[2].getColour().key+" "+plat+" "+ss[0].getColour().key+"]";
			lines[2] = "[  "+ss[1].getColour().key+"  ]";
		}
		return lines;
	}
	
	// FIXME - delete
	public static void main(String[] args) {
		int SIZE = 8;
		TileStack[][] matrix = new TileStack[SIZE][];
		// init lines
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = new TileStack[SIZE];
		}
		
		matrix[1][1] = T54;
		matrix[1][2] = T42;
		StringBuffer[] lines1 = new StringBuffer[matrix.length];
		StringBuffer[] lines2 = new StringBuffer[matrix.length];
		StringBuffer[] lines3 = new StringBuffer[matrix.length];
		
		for (int i = 0; i < matrix.length; i++) {
			lines1[i] = new StringBuffer();
			lines2[i] = new StringBuffer();
			lines3[i] = new StringBuffer();
			
			for (int j = 0; j < matrix[i].length; j++) {
				String[] tileLines = get3Lines(matrix[i][j]);
				lines1[i].append(tileLines[0]);
				lines2[i].append(tileLines[1]);
				lines3[i].append(tileLines[2]);
			}
		}
		
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(lines1[i]);
			System.out.println(lines2[i]);
			System.out.println(lines3[i]);
			System.out.println();
		}
	}
	
}
