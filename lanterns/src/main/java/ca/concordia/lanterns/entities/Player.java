package ca.concordia.lanterns.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;
import ca.concordia.lanterns.entities.enums.PlayerID;

/**
 * Player entity
 * @version 1.0 
 *
 */
public class Player {

	private final String name;
	private final PlayerID id ;
	private final HashMap<LanternCard, Integer> cards;
	private final HashMap<DedicationType, List<DedicationToken>> dedications ;
	private final List<LakeTile> tiles;
	private int favors;
	
	/**
	 * 
	 * @param name
	 */
//	@SuppressWarnings("unchecked")
	public Player(final String name, PlayerID id ) {
		super();
		this.name = name;
		this.id = id ;
		this.cards = new HashMap<LanternCard, Integer> ();
		for ( Colour c : Colour.values() ) {
			cards.put(new LanternCard(c), 0) ;
		}
		this.dedications = new HashMap<DedicationType, List<DedicationToken>> () ;
		this.dedications.put(DedicationType.FOUR_OF_A_KIND, new ArrayList<DedicationToken>() );
		this.dedications.put(DedicationType.THREE_PAIRS, new ArrayList<DedicationToken>() );
		this.dedications.put(DedicationType.SEVEN_UNIQUE, new ArrayList<DedicationToken>() );
		
		this.tiles = new ArrayList<LakeTile>();
		favors = 0 ;
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
		if (id == other.id) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
	public HashMap<LanternCard, Integer> getCards() {
		return cards;
	}
	
	public boolean getCards ( Colour color, int numberOfCards) {
		LanternCard l = new LanternCard(color) ;
		int stock = cards.get(l) ;
		if ( stock >= numberOfCards ) {
			cards.put(l, stock - numberOfCards ) ;
			return true ;
		} else {
			return false ;
		}
	}
	
	public void addCards ( Colour color, int numberOfCards ) {
		LanternCard l = new LanternCard(color) ;
		int no = cards.get(l).intValue() + numberOfCards ;
		cards.put(l, no) ;
	}

	/**
	 * Returns the dedication tokens attributed to a player to the constructor.
	 * @return Dedication tokens attributed to a player.
	 */
	public HashMap<DedicationType, List<DedicationToken>> getDedications() {
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
