package ca.concordia.lanterns_slick2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.concordia.lanterns_slick2d.ui.CardStack;

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
    
    private CardStack stack;

    public Game() {
        super("Lanterns - Team A");
        stack = new CardStack();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        stack.render(container, g);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        stack.init(container);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        stack.update(container, delta);
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH, HEIGHT, FULL_SCREEN);
        app.setShowFPS(SHOW_FPS);
        app.setForceExit(false);
        app.start();
    }

}
