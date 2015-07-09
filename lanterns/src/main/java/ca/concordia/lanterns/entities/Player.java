package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ca.concordia.lanterns.entities.enums.Colour;

/**
 * Player entity
 * @version 1.0 
 *
 */
public class Player {

	private final String name;
	private final Stack<LanternCard>[] cards;
	private final List<DedicationToken> dedications;
	private final List<LakeTile> tiles;
	private int favors;
	
	/**
	 * 
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	public Player(final String name) {
		super();
		this.name = name;
		this.cards = new Stack[Colour.values().length];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new Stack<LanternCard>();
		}
		this.dedications = new ArrayList<DedicationToken>();
		this.tiles = new ArrayList<LakeTile>();
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * 
	 */
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

	/**
	 * Returns the Favor tokens of a player to the constructor.
	 * @return Favor tokens of a player.
	 */
	public int getFavors() {
		return favors;
	}


	public void setFavors(int favors) {
		this.favors = favors;
	}

	/**
	 * Returns the Name of a player to the constructor.
	 * @return Player name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the Lantern Cards attributed to a player to the constructor.
	 * @return Lantern Cards attributed to a player.
	 */
	public Stack<LanternCard>[] getCards() {
		return cards;
	}

	/**
	 * Returns the dedication tokens attributed to a player to the constructor.
	 * @return Dedication tokens attributed to a player.
	 */
	public List<DedicationToken> getDedications() {
		return dedications;
	}

	/**
	 * Returns the Lake tiles attributed to a player to the constructor.
	 * @return Lake tiles attributed to a player.
	 */
	public List<LakeTile> getTiles() {
		return tiles;
	}
	
}
