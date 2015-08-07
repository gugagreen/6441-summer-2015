package ca.concordia.lanterns_slick2d.ui.buttons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 * An abstracted class of our game buttons.
 */
public abstract class Button extends MouseOverArea {

    private final Image image;
    private boolean enabled;
    private float scale;
    private float angle;

    public Button(GUIContext container, String imgPath, int x, int y, int width, int height, float scale) throws SlickException {
        super(container, new Image(imgPath), x, y, width, height);
        this.enabled = true;
        this.scale = scale;

        this.image = new Image(imgPath);
    }

    @Override
    public void mousePressed(int button, int mx, int my) {
        super.mousePressed(button, mx, my);
    }

    @Override
    public void mouseReleased(int button, int mx, int my) {
        this.notifyListeners();
    }

    @Override
    public void render(GUIContext container, Graphics g) {
    	if (this.angle != 0) {
    		image.setRotation(this.angle);
    	}
        image.draw(this.getX(), this.getY(), this.getWidth() * this.scale, this.getHeight() * this.scale);
    }
    
    public void setAngle(float angle) {
    	this.angle = angle;
    }

}
