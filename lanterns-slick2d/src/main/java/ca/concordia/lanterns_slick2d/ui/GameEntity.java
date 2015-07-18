package ca.concordia.lanterns_slick2d.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class GameEntity implements Game {
	
	public boolean mouseHit(float x, float y, int width, int height, GameContainer container, Graphics g) {
		boolean hasClicked = false;
		int mouseX = Mouse.getX();
		// getY() coordinates starts on bottom (0 is bottom, 768 is top), so it needs to be translated to game coordinates
		int mouseY = container.getHeight() - Mouse.getY();
		
		if ((mouseX > x) && (mouseX < (x + width)) && (mouseY > y) && (mouseY < (y+height))) {
			// if mouse left button is down
			if (Mouse.isButtonDown(0)) {
				hasClicked = true;
			}
		}
		
		return hasClicked;
	}

}
