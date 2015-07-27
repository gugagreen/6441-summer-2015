package ca.concordia.lanterns_slick2d.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This extends  {@link BasicGameState} to allow us to have a game state where all of the gameplay is finished.
 *
 */
public class End extends BasicGameState {

	private int id;

	public End(int id) {
		this.id = id;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

}
