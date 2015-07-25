package ca.concordia.lanterns_slick2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.concordia.lanterns_slick2d.client.GameClient;
import ca.concordia.lanterns_slick2d.constants.Constants;
import ca.concordia.lanterns_slick2d.ui.CardStacksView;
import ca.concordia.lanterns_slick2d.ui.PlayerView;
import ca.concordia.lanterns_slick2d.ui.buttons.FavorToken;
import ca.concordia.lanterns_slick2d.ui.buttons.Tile;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

/**
 * A game using Slick2d
 */
public class MainGame extends BasicGame {

    /** Screen width */
    private static final int WIDTH = 1024;
    /** Screen height */
    private static final int HEIGHT = 768;
    private static final boolean FULL_SCREEN = false;
    private static final boolean SHOW_FPS = true;
    
    private CardStacksView cardStacks;
    private FavorToken favors;
    private Tile tileStack;
    private PlayerView[] players;
    
    private GameClient client;

    public MainGame() {
        super(Constants.GAME_TITLE);
        this.client = new GameClient();
    }

    @Override
    public void init(GameContainer container) throws SlickException {
    	Game game = client.getGame();
        cardStacks = new CardStacksView(true, 10, 10);
    	cardStacks.init(container);
        favors = new FavorToken(container, 100, 350);
        tileStack = new Tile(container, Constants.TILE_BACK_IMG, 10, 670);
        initPlayers(game, container);
    }
    
    private void initPlayers(final Game game, final GameContainer container) throws SlickException {
    	Player[] ps = game.getPlayers();
    	players = new PlayerView[ps.length];
    	for (int i = 0; i < ps.length; i++) {
    		System.out.println(ps[i].getName());
    		boolean vertical = (i%2) == 0;
        	// FIXME - get correct position
    		players[i] = new PlayerView(ps[i], vertical, 200, 10);
    		players[i].init(container);
		}
        
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
    	renderPlayers(container, g);
    	// FIXME - print real amount of favors and tiles
    	g.drawString("20x", 120, favors.getY() + 5);
    	g.drawString("20x", tileStack.getX() + 10, tileStack.getY() + 10);
    }
    
    private void renderPlayers(GameContainer container, Graphics g) throws SlickException {
    	for (PlayerView pv : players) {
    		pv.render(container, g);
    	}
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new MainGame());
        app.setDisplayMode(WIDTH, HEIGHT, FULL_SCREEN);
        app.setShowFPS(SHOW_FPS);
        app.setForceExit(false);
        app.start();
    }

}
