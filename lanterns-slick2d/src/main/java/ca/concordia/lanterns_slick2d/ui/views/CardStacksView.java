package ca.concordia.lanterns_slick2d.ui.views;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import ca.concordia.lanterns_slick2d.constants.Constants;
import ca.concordia.lanterns_slick2d.ui.buttons.Card;
import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.enums.Colour;

/**
 * UI Entity that controls the stacks of cards showing on the table.
 */
public class CardStacksView implements Game {

	private Card[] cardButtons;
	private boolean vertical;
	private int x;
	private int y;
	
	private LanternCardWrapper[] cards;

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
		cardButtons = new Card[colours.length];
		
		for (int i = 0; i < colours.length; i++) {
			String ref = Constants.CARD_IMG_FOLDER + colours[i].name().toLowerCase() + Constants.JPG;
			if (vertical) {
				int cardY = this.y + i * (10 + Card.HEIGHT);
				cardButtons[i] = new Card(container, ref, this.x, cardY);
			} else {
				int cardX = this.x + i * (10 + Card.WIDTH);
				cardButtons[i] = new Card(container, ref, cardX, this.y);
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
		for (int i = 0; i < cardButtons.length; i++) {
			Card card = cardButtons[i];
			card.render(container, g);
			if (cards != null) {
				String qty = cards[i].getQuantity() + "x";
				g.drawString(qty, card.getX() + 10, card.getY() + 10);
			}
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

	public void setCards(LanternCardWrapper[] cards) {
		this.cards = cards;
	}

}
