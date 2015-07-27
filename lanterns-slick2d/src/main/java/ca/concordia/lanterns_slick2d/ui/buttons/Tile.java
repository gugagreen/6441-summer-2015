package ca.concordia.lanterns_slick2d.ui.buttons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

/**
 * Describes the Lake Tile buttons in the client view.
 *
 */
public class Tile extends Button {

	public static final float SCALE = 0.1f;
	// 530 x 805
	public static final int ORIGINAL_WIDTH = 715;
	public static final int ORIGINAL_HEIGHT = 715;
	public static final int WIDTH = (int) (ORIGINAL_WIDTH * SCALE);
	public static final int HEIGHT = (int) (ORIGINAL_HEIGHT * SCALE);

	public Tile(GUIContext container, String imgPath, int x, int y) throws SlickException {
		super(container, imgPath, x, y, WIDTH, HEIGHT);
	}
}
