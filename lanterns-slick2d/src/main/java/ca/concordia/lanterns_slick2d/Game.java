package ca.concordia.lanterns_slick2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.concordia.lanterns_slick2d.ui.CardStacks;
import ca.concordia.lanterns_slick2d.ui.FavorToken;
import ca.concordia.lanterns_slick2d.ui.Player;
import ca.concordia.lanterns_slick2d.ui.Tile;

/**
 * A game using Slick2d
 */
public class Game extends BasicGame {

    /** Screen width */
    private static final int WIDTH = 1024;
    /** Screen height */
    private static final int HEIGHT = 768;
    private static final boolean FULL_SCREEN = false;
    private static final boolean SHOW_FPS = true;
    
    private CardStacks cardStacks;
    private FavorToken favors;
    private Tile tileStack;
    private Player[] players;

    public Game() {
        super("Lanterns - Team A");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        cardStacks = new CardStacks(true, 10, 10);
    	cardStacks.init(container);
        favors = new FavorToken(container, 100, 350);
        tileStack = new Tile(container, "img/tiles/back-tile.png", 10, 670);
        players = new Player[] {new Player("John")}; // FIXME - add up to 4 players
        players[0].init(container);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	cardStacks.update(container, delta);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	cardStacks.render(container, g);
    	favors.render(container, g);
    	tileStack.render(container, g);
    	players[0].render(container, g); // FIXME - render all players
    	// FIXME - print real amount of favors and tiles
    	g.drawString("20x", 120, favors.getY() + 5);
    	g.drawString("20x", tileStack.getX() + 10, tileStack.getY() + 10);
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH, HEIGHT, FULL_SCREEN);
        app.setShowFPS(SHOW_FPS);
        app.setForceExit(false);
        app.start();
    }

}
