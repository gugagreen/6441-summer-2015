package ca.concordia.lanterns_slick2d.ui;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Player implements Game {
	
	private String name;
	private CardStacks cardStacks;
	private Tile tileStack;

	public Player(String name) {
		super();
		this.name = name;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		cardStacks = new CardStacks(false, 280, 30);
    	cardStacks.init(container);
    	// FIXME - get correct position
    	tileStack = new Tile(container, "img/tiles/back-tile.png", 200, 30);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		cardStacks.render(container, g);
    	tileStack.render(container, g);
    	// FIXME - get correct position
    	g.drawString(name, 200, 10);
    	// FIXME - print real amount of favors and tiles
    	g.drawString("3x", tileStack.getX() + 10, tileStack.getY() + 10);
	}

	@Override
	public boolean closeRequested() {
		return true;
	}

	@Override
	public String getTitle() {
		// unimplemented
		return null;
	}

}
