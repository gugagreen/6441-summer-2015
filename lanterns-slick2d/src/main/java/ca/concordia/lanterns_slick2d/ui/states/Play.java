package ca.concordia.lanterns_slick2d.ui.states;

import static ca.concordia.lanterns_slick2d.constants.Constants.CARDS_X;
import static ca.concordia.lanterns_slick2d.constants.Constants.CARDS_Y;
import static ca.concordia.lanterns_slick2d.constants.Constants.FAVORS_X;
import static ca.concordia.lanterns_slick2d.constants.Constants.FAVORS_Y;
import static ca.concordia.lanterns_slick2d.constants.Constants.INIT_TILE_X;
import static ca.concordia.lanterns_slick2d.constants.Constants.INIT_TILE_Y;
import static ca.concordia.lanterns_slick2d.constants.Constants.JPG;
import static ca.concordia.lanterns_slick2d.constants.Constants.TILE_BACK_IMG;
import static ca.concordia.lanterns_slick2d.constants.Constants.TILE_IMG_FOLDER;
import static ca.concordia.lanterns_slick2d.constants.Constants.TILE_PREFIX;
import static ca.concordia.lanterns_slick2d.constants.Constants.TILE_STACK_X;
import static ca.concordia.lanterns_slick2d.constants.Constants.TILE_STACK_Y;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ca.concordia.lanterns_slick2d.client.GameClient;
import ca.concordia.lanterns_slick2d.client.PlayerViewEnum;
import ca.concordia.lanterns_slick2d.ui.buttons.FavorToken;
import ca.concordia.lanterns_slick2d.ui.buttons.Tile;
import ca.concordia.lanterns_slick2d.ui.views.CardStacksView;
import ca.concordia.lanterns_slick2d.ui.views.PlayerView;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;
import ca.concordia.lanternsentities.enums.TileStack;

public class Play extends BasicGameState {
	
	private int id;
	
	private CardStacksView cardStacks;
    private FavorToken favors;
    private Tile tileStack;
    private ArrayList<Tile> lake;
    private PlayerView[] players;
    
    private GameClient client;
    private Game game;
    
    public Play(int id) {
    	this.id = id;
        this.client = new GameClient();
        this.lake = new ArrayList<Tile>();
    }

	@Override
	public int getID() {
		return this.id;
	}
	
	@Override
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
    	game = client.createGame();
        cardStacks = new CardStacksView(true, CARDS_X, CARDS_Y);
    	cardStacks.init(container);
        favors = new FavorToken(container, FAVORS_X, FAVORS_Y);
        tileStack = new Tile(container, TILE_BACK_IMG, TILE_STACK_X, TILE_STACK_Y);
        initPlayers(game, container);
        initLake(game, container);
    }
    
    private void initPlayers(final Game game, final GameContainer container) throws SlickException {
    	Player[] ps = game.getPlayers();
    	players = new PlayerView[ps.length];
    	PlayerViewEnum[] pvEnum = PlayerViewEnum.values();
    	for (int i = 0; i < ps.length; i++) {
    		players[i] = new PlayerView(ps[i], pvEnum[i].vertical, pvEnum[i].x, pvEnum[i].y);
    		players[i].init(container);
		}
    }
    
    private void initLake(final Game game, final GameContainer container) throws SlickException {
    	String initialTilePath = TILE_IMG_FOLDER + TILE_PREFIX + TileStack.T54.name + JPG;
    	lake.add(new Tile(container, initialTilePath, INIT_TILE_X, INIT_TILE_Y));
    }

    @Override
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
    	cardStacks.update(container, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
    	cardStacks.render(container, g);
    	favors.render(container, g);
    	tileStack.render(container, g);
    	renderPlayers(container, g);
    	renderLake(container, g);
    	// FIXME - print real amount of favors and tiles
        //Game game = client.getGame();
    	g.drawString(game.getFavors() + "x", 120, favors.getY() + 5);
    	g.drawString(game.getTiles().size() + "x", tileStack.getX() + 10, tileStack.getY() + 10);
    }
    
    private void renderPlayers(GameContainer container, Graphics g) throws SlickException {
    	for (PlayerView pv : players) {
    		pv.render(container, g);
    	}
    }
    
    private void renderLake(GameContainer container, Graphics g) throws SlickException {
    	for (Tile tile : lake) {
    		tile.render(container, g);
    	}
    }

}
