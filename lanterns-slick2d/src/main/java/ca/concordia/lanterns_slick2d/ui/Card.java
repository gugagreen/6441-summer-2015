package ca.concordia.lanterns_slick2d.ui;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

public class Card extends Button {
	
	public static final float SCALE = 0.1f;
	// 530 x 805
	public static final int ORIGINAL_WIDTH = 530;
	public static final int ORIGINAL_HEIGHT = 805;
	public static final int WIDTH = (int) (ORIGINAL_WIDTH * SCALE);
	public static final int HEIGHT = (int) (ORIGINAL_HEIGHT * SCALE);

	public Card(GUIContext container, String imgPath, int x, int y) throws SlickException {
		super(container, imgPath, x, y, WIDTH, HEIGHT);
	}

}
