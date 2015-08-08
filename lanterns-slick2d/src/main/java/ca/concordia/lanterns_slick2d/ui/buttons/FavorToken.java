package ca.concordia.lanterns_slick2d.ui.buttons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;

/**
 * Describes the Favor Token Button in the view.
 */
public class FavorToken extends Button {

    public static final String IMG_PATH = "img/favor_token.png";
    public static final float SCALE = 0.1f;
    // 530 x 805
    public static final int WIDTH = 233;
    public static final int HEIGHT = 233;

    public FavorToken(GUIContext container, int x, int y) throws SlickException {
        super(container, IMG_PATH, x, y, WIDTH, HEIGHT, SCALE);
    }
}
