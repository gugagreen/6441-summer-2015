package ca.concordia.lanterns_slick2d.ui.states;

import java.util.ArrayList;
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
	private TextField[] playerNames;
	private UnicodeFont font;
	
	private Start startButton;
	
	public Menu(int id) {
		this.id = id;
		this.font = FontHelper.getDefaultFont();
		this.playerNames = new TextField[4];
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		font.loadGlyphs();
		for (int i = 0; i < playerNames.length; i++) {
			// FIXME - set position from constants
			playerNames[i] = new TextField(container, font, 100, (200 + 50*i), 100, 25);
		}
		startButton = new Start(container, START_BUTTON_X, START_BUTTON_Y); // FIXME - set position from constants
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for (TextField tf : playerNames) {
			tf.render(container, g);
		}
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
				try {
					MainGame.getInstance().enterPlay(getPlayers());
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String[] getPlayers() {
		ArrayList<String> names = new ArrayList<String>();
		
		for (TextField tf : playerNames) {
			String name = tf.getText();
			if ((name != null) && (name.trim().length() > 0)) {
				names.add(name);
			}
		}
		
		return names.toArray(new String[0]);
	}

}
