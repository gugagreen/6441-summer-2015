package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanterns.entities.enums.Colour;

/**
 * Player entity
 * @version 1.0 
 *
 */
public class Player {

	private String name;
	private Stack<LanternCard>[] cards;
	private List<DedicationToken> dedications;
	private List<LakeTile> tiles;
	private int favors;
	
	/**
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	public void init(final String name) {
		this.name = name;
		this.cards = new Stack[Colour.values().length];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new Stack<LanternCard>();
		}
		this.dedications = new ArrayList<DedicationToken>();
		this.tiles = new ArrayList<LakeTile>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stack<LanternCard>[] getCards() {
		return cards;
	}

	public void setCards(Stack<LanternCard>[] cards) {
		this.cards = cards;
	}

	public List<DedicationToken> getDedications() {
		return dedications;
	}

	public void setDedications(List<DedicationToken> dedications) {
		this.dedications = dedications;
	}

	public List<LakeTile> getTiles() {
		return tiles;
	}

	public void setTiles(List<LakeTile> tiles) {
		this.tiles = tiles;
	}

	public int getFavors() {
		return favors;
	}

	public void setFavors(int favors) {
		this.favors = favors;
	}
	
	
}
