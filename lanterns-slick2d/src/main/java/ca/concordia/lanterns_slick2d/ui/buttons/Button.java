package ca.concordia.lanterns_slick2d.ui.buttons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 * An abstracted class of our game buttons.
 */
public abstract class Button extends MouseOverArea {

    private final Image image;
    private boolean enabled;
    private boolean isClicked;

    public Button(GUIContext container, String imgPath, int x, int y, int width, int height) throws SlickException {
        super(container, new Image(imgPath), x, y, width, height);
        this.enabled = true;
        this.isClicked = false;

        this.image = new Image(imgPath);
    }

    @Override
    public void mousePressed(int button, int mx, int my) {
        super.mousePressed(button, mx, my);
        if ((button == Input.MOUSE_LEFT_BUTTON) && isMouseOver() && enabled) {
            isClicked = true;
        } else {
            isClicked = false;
        }
    }

    @Override
    public void mouseReleased(int button, int mx, int my) {
        isClicked = false;
        this.notifyListeners();
    }

    @Override
    public void render(GUIContext container, Graphics g) {
        image.draw(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

}
