package ca.concordia.lanterns_slick2d.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

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
	}

	@Override
	public void render(GUIContext container, Graphics g) {
		image.draw(this.getX(), this.getY(), this.getWidth(), this.getHeight());

		if (isClicked) {
			g.drawString("> " + image.getResourceReference(), 300, getY() + 10);
		}
	}

	public boolean mouseHit(GameContainer container) {
		boolean hasClicked = false;
		// if mouse left button was pressed
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int mouseX = Mouse.getX();
			// getY() coordinates starts on bottom (0 is bottom, 768 is top), while container starts on top
			// (768 is bottom, 0 is top), so it needs to be translated to game coordinates
			int mouseY = container.getHeight() - Mouse.getY();

			// check if mouse was clicked on the button area
			if ((mouseX > getX()) && (mouseX < (getX() + getWidth())) && (mouseY > getY()) && (mouseY < (getY() + getHeight()))) {
				hasClicked = true;
			}
		}

		return hasClicked;
	}

}
