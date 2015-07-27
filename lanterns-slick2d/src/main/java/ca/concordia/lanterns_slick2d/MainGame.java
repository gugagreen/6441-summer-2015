package ca.concordia.lanterns_slick2d;

import static ca.concordia.lanterns_slick2d.constants.Constants.GAME_TITLE;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ca.concordia.lanterns_slick2d.client.GameClient;
import ca.concordia.lanterns_slick2d.ui.states.End;
import ca.concordia.lanterns_slick2d.ui.states.Menu;
import ca.concordia.lanterns_slick2d.ui.states.Play;
import ca.concordia.lanternsentities.Game;

/**
 * A game using Slick2d
 */
public class MainGame extends StateBasedGame {

	private static class SingletonHolder {
		static final MainGame INSTANCE = new MainGame();
	}

	public static MainGame getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/** Screen width */
	private static final int WIDTH = 1024;
	/** Screen height */
	private static final int HEIGHT = 768;
	private static final boolean FULL_SCREEN = false;
	private static final boolean SHOW_FPS = true;

	// states
	public static final int STATE_MENU = 1;
	public static final int STATE_PLAY = 2;
	public static final int STATE_END = 3;

	public MainGame() {
		super(GAME_TITLE);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new Menu(STATE_MENU));
		this.addState(new Play(STATE_PLAY));
		this.addState(new End(STATE_END));
	}
	
	public void enterPlay(String[] playerNames) throws SlickException {
		if (playerNames != null) {
			Game game = GameClient.getInstance().createGame(playerNames);
			Play play = (Play) this.getState(MainGame.STATE_PLAY);
			play.setGame(game);
			this.enterState(MainGame.STATE_PLAY);
		} else {
			// FIXME - add message to ui
			throw new SlickException("No player names specified.");
		}
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(MainGame.getInstance());
		app.setDisplayMode(WIDTH, HEIGHT, FULL_SCREEN);
		app.setShowFPS(SHOW_FPS);
		app.setForceExit(false);
		app.start();
	}
}
