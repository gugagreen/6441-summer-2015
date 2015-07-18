package ca.concordia.lanterns_slick2d.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class CardStack {

	private Card[] cards;

	public void init(GameContainer container) throws SlickException {
		// FIXME - get list from Colours Enum
		// ORANGE, GREEN, PURPLE, GRAY, BLUE, RED, BLACK
		String[] colours = new String[] {"orange", "green", "purple", "white", "blue", "red", "black"};
		cards = new Card[colours.length];
		
		for (int i = 0; i < colours.length; i++) {
			int y = 10 + i * (10 + Card.HEIGHT);
			cards[i] = new Card(container, "img/cards/" + colours[i] + ".jpg", 10, y);
		}
	}

	public void update(GameContainer container, int delta) {
		/*for(Card card : cards) {
			card.update(container, delta);
		}*/
	}

	public void render(GameContainer container, Graphics g) {
		for (int i = 0; i < cards.length; i++) {
			Card card = cards[i];
			card.render(container, g);
			// g.drawString("8x", 30, y + 10);
		}
	}

}
