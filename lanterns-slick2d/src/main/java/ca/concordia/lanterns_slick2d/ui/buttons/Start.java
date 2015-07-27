package ca.concordia.lanterns_slick2d.ui.buttons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

/**
 * Describes the start button in the client view.
 *
 */
public class Start extends Button {
	
	public static final String IMG_PATH = "img/start_button.png";
	public static final float SCALE = 0.3f;
	// 530 x 805
	public static final int ORIGINAL_WIDTH = 233;
	public static final int ORIGINAL_HEIGHT = 233;
	public static final int WIDTH = (int) (ORIGINAL_WIDTH * SCALE);
	public static final int HEIGHT = (int) (ORIGINAL_HEIGHT * SCALE);

	public Start(GUIContext container, int x, int y) throws SlickException {
		super(container, IMG_PATH, x, y, WIDTH, HEIGHT);
	}

}
