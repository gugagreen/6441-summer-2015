package ca.concordia.lanterns_slick2d.ui;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.concordia.lanterns_slick2d.ui.buttons.Card;
import ca.concordia.lanternsentities.enums.Colour;

/**
 * UI Entity that controls the stacks of cards showing on the table.
 */
public class CardStacksView implements Game {

	private Card[] cards;
	private boolean vertical;
	private int x;
	private int y;

	/**
	 * Constructor of CardStacks.
	 * @param vertical	true if cards should be rendered vertically, false if horizontally.
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 */
	public CardStacksView(boolean vertical, int x, int y) {
		super();
		this.vertical = vertical;
		this.x = x;
		this.y = y;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		Colour[] colours = Colour.values();
		cards = new Card[colours.length];
		
		for (int i = 0; i < colours.length; i++) {
			String ref = "img/cards/" + colours[i].name().toLowerCase() + ".jpg";
			if (vertical) {
				int cardY = this.y + i * (10 + Card.HEIGHT);
				cards[i] = new Card(container, ref, this.x, cardY);
			} else {
				int cardX = this.x + i * (10 + Card.WIDTH);
				cards[i] = new Card(container, ref, cardX, this.y);
			}
		}
	}

	@Override
	public void update(GameContainer container, int delta) {
		/*for(Card card : cards) {
			card.update(container, delta);
		}*/
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		for (int i = 0; i < cards.length; i++) {
			Card card = cards[i];
			card.render(container, g);
			// FIXME - print real amount on each stack
			g.drawString("8x", 30, card.getY() + 10);
		}
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
