package ca.concordia.lanterns_slick2d.ui;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.concordia.lanterns_slick2d.constants.Constants;
import ca.concordia.lanterns_slick2d.ui.buttons.Tile;
import ca.concordia.lanternsentities.Player;

public class PlayerView implements Game {
	
	private Player player;
	private CardStacksView cardStacks;
	private Tile tileStack;
	private boolean vertical;
	private int x;
	private int y;

	public PlayerView(Player player, boolean vertical, int x, int y) {
		super();
		this.player = player;
		this.vertical = vertical;
		this.x = x;
		this.y = y;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		if (vertical) {
			// FIXME - recalculate when its vertical
			initCardStacks(280, (y + 20), container);
			initTileStack(x, (y + 20), container);
		} else {
			initCardStacks(280, (y + 20), container);
			initTileStack(x, (y + 20), container);
		}
    	
	}
	
	private void initCardStacks(int xStack, int yStack, GameContainer container) throws SlickException {
		cardStacks = new CardStacksView(vertical, xStack, yStack);
    	cardStacks.init(container);
	}
	
	private void initTileStack(int xTile, int yTile, GameContainer container) throws SlickException {
    	tileStack = new Tile(container, Constants.TILE_BACK_IMG, xTile, yTile);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		cardStacks.render(container, g);
    	tileStack.render(container, g);
    	g.drawString(player.getName(), x, y);
    	// FIXME - print real amount of favors and tiles
    	g.drawString("3x", tileStack.getX() + 10, tileStack.getY() + 10);
	}

	@Override
	public boolean closeRequested() {
		return true;
	}

	@Override
	public String getTitle() {
		return null;
	}

}
