package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.PlayerID;

/**
 * Player entity
 * @version 1.0 
 *
 */
public class Player {

	private String name;
	private PlayerID id ;
	private LanternCardWrapper[] cards;
	private List<DedicationToken> dedications;
	private List<LakeTile> tiles;
	private int favors;
	
	/**
	 * @param name
	 */
	public void init(final String name, final PlayerID id) {
		this.name = name;
		this.id = id ;
		this.cards = new LanternCardWrapper[Colour.values().length];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new LanternCardWrapper();
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

	public PlayerID getID () {
		return id ;
	}
	
	public LanternCardWrapper[] getCards() {
		return cards;
	}

	public void setCards(LanternCardWrapper[] cards) {
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
