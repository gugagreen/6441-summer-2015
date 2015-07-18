package ca.concordia.lanterns_slick2d.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CardStack {

	public static final float SCALE = 0.1f;
	// 530 x 805
	public static final float ORIGINAL_WIDTH = 530;
	public static final float ORIGINAL_HEIGHT = 805;
	public static final float WIDTH = ORIGINAL_WIDTH * SCALE;
	public static final float HEIGHT = ORIGINAL_HEIGHT * SCALE;

	private Image[] images;

	public void init() throws SlickException {
		// FIXME - get list from Colours Enum
		// ORANGE, GREEN, PURPLE, GRAY, BLUE, RED, BLACK
		images = new Image[] { new Image("img/cards/orange.jpg"), new Image("img/cards/green.jpg"), 
				new Image("img/cards/purple.jpg"),
				new Image("img/cards/white.jpg"), new Image("img/cards/blue.jpg"), new Image("img/cards/red.jpg"),
				new Image("img/cards/black.jpg") };
	}

	public void update() {
		//
	}

	public void render(GameContainer container, Graphics g) {
		for (int i = 0; i < images.length; i++) {
			Image image = images[i];
			float y = 10 + i * (10 + HEIGHT);
			image.draw(10, y, WIDTH, HEIGHT);
			g.drawString("8x", 30, y + 10);
			
			// get input
			mouseClick(10, y, container, g, image);
		}
	}
	
	private void mouseClick(float x, float y, GameContainer container, Graphics g, Image image) {
		int mouseX = Mouse.getX();
		// getY() coordinates starts on bottom (0 is bottom, 768 is top), so it needs to be translated to game coordinates
		int mouseY = container.getHeight() - Mouse.getY();
		
		if ((mouseX > x) && (mouseX < (x + WIDTH)) && (mouseY > y) && (mouseY < (y+HEIGHT))) {
			// if mouse left button is down
			if (Mouse.isButtonDown(0)) {
				g.drawString("clicked: " + mouseX + ":" + mouseY, 100, 100);
				g.drawString("> " + image.getResourceReference(), 300, y + 10);
			}
		}
	}

}
