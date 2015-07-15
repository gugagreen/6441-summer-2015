package ca.concordia.lanterns.entities;

import java.util.Stack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;


/**
 * Entity that contains all game state.
 * @version 1.0
 */
@XmlRootElement
public class Game {
	public static final int TOTAL_FAVORS = 20;

	/** Players in the game. */
	private Player[] players;
	/** Lake where tiles are being displayed for all users. */
	private Lake lake;
	/** Stack of Lake Tiles to be distributed. */
	private Stack<LakeTile> tiles;
	/** Stack of Lantern Cards to be distributed. */
	private LanternCardWrapper[] cards;
	/** Stack of Dedication Tokens to be distributed. */
	private DedicationTokenWrapper[] dedications;
	/** Quantity of favors to be distributed to players. */
	private int favors;

	/**
	 * Initializes a new Game based on the player names.
	 * <p>This constructor will instantiate (but not populate) all attributes of this Game instance.
	 * 
	 * @param playerNames Names of each current player (ordered by login time)
	 */
	@SuppressWarnings("unchecked")
	public void init(final String[] playerNames) {
		
		this.players = new Player[playerNames.length];
		for (int i = 0; i < playerNames.length; i++) {
			this.players[i] = new Player();
			this.players[i].init(playerNames[i]);
		}
		
		this.lake = new Lake();
		
		this.tiles = new Stack<LakeTile>();
		
		this.cards = new LanternCardWrapper[Colour.values().length];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new LanternCardWrapper();
		}
		
		this.dedications = new DedicationTokenWrapper[DedicationType.values().length];
		for (int i = 0; i < dedications.length; i++) {
			dedications[i] = new DedicationTokenWrapper();
		}
		
		this.favors = TOTAL_FAVORS;
	}
	
	

	public int getFavors() {
		return favors;
	}

	/**
	 * This method sets the number of Favor Tokens.
	 * @param favors range <code>0..20</code>.
	 */
	public void setFavors(int favors) {
		if (favors >= 0 && favors <= 20) {
			this.favors = favors;
		} else {
			throw new IllegalArgumentException("Number of Favor Tokens should be in the range [0..20]");
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public synchronized void setPlayers(Player[] players) {
		if (this.players != null) {
			throw new IllegalAccessError("Attribute players can only be set once.");
		}
		this.players = players;
	}

	public Lake getLake() {
		return lake;
	}

	public synchronized void setLake(Lake lake) {
		if (this.lake != null) {
			throw new IllegalAccessError("Attribute lake can only be set once.");
		}
		this.lake = lake;
	}

	public Stack<LakeTile> getTiles() {
		return tiles;
	}

	public synchronized void setTiles(Stack<LakeTile> tiles) {
		if (this.tiles != null) {
			throw new IllegalAccessError("Attribute tiles can only be set once.");
		}
		this.tiles = tiles;
	}

	public LanternCardWrapper[] getCards() {
		return cards;
	}

	public synchronized void setCards(LanternCardWrapper[] cards) {
		if (this.cards != null) {
			throw new IllegalAccessError("Attribute cards can only be set once.");
		}
		this.cards = cards;
	}

	public DedicationTokenWrapper[] getDedications() {
		return dedications;
	}

	public synchronized void setDedications(DedicationTokenWrapper[] dedications) {
		if (this.dedications != null) {
			throw new IllegalAccessError("Attribute dedications can only be set once.");
		}
		this.dedications = dedications;
	}
	
	
}
