package ca.concordia.lanterns_slick2d.ui.states;

import static ca.concordia.lanterns_slick2d.constants.Constants.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ca.concordia.lanterns_slick2d.MainGame;
import ca.concordia.lanterns_slick2d.ui.buttons.Start;
import ca.concordia.lanterns_slick2d.ui.helper.FontHelper;

public class Menu extends BasicGameState implements ComponentListener {
	
	private int id;
	private TextField player1;
	private TextField player2;
	private TextField player3;
	private TextField player4;
	private UnicodeFont font;
	
	private Start startButton;
	
	public Menu(int id) {
		this.id = id;
		this.font = FontHelper.getDefaultFont();
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		font.loadGlyphs();
		player1 = new TextField(container, font, PLAYER1_X, PLAYER1_Y, PLAYER_WIDTH, PLAYER_HEIGHT); // FIXME - set position from constants
		player2 = new TextField(container, font, PLAYER2_X, PLAYER2_Y, PLAYER_WIDTH, PLAYER_HEIGHT); // FIXME - set position from constants
		player3 = new TextField(container, font, PLAYER3_X, PLAYER3_Y, PLAYER_WIDTH, PLAYER_HEIGHT); // FIXME - set position from constants
		player4 = new TextField(container, font, PLAYER4_X, PLAYER4_Y, PLAYER_WIDTH, PLAYER_HEIGHT); // FIXME - set position from constants
		startButton = new Start(container, START_BUTTON_X, START_BUTTON_Y); // FIXME - set position from constants
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		player1.render(container, g);
		player2.render(container, g);
		player3.render(container, g);
		player4.render(container, g);
		startButton.render(container, g);
		startButton.addListener(this);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// nothing
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if (source instanceof Start) {
			Start start = (Start) source;
			if (start.isMouseOver()) {
				MainGame.getInstance().switchState(MainGame.STATE_PLAY);	
			}
		}
	}

}
