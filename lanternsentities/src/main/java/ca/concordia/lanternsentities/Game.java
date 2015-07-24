package ca.concordia.lanternsentities;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;


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
	private List<LakeTile> lake;
	/** Stack of Lake Tiles to be distributed. */
	private Stack<LakeTile> tiles;
	/** Stack of Lantern Cards to be distributed. */
	private LanternCardWrapper[] cards;
	/** Stack of Dedication Tokens to be distributed. */
	private DedicationTokenWrapper[] dedications;
	/** Quantity of favors to be distributed to players. */
	private int favors;
	/** Index to mark current player in {@link #players} */
	private int currentTurnPlayer ;
	
	/**
	 * Initializes a new Game based on the player names.
	 * <p>This constructor will instantiate (but not populate) all attributes of this Game instance.
	 * 
	 * @param playerNames Names of each current player (ordered by login time)
	 */
	public void init(final String[] playerNames) {
		
		this.players = new Player[playerNames.length];
		for (int i = 0; i < playerNames.length; i++) {
			this.players[i] = new Player();
			this.players[i].init(playerNames[i], i);
		}
		
		this.lake = new ArrayList<LakeTile>();
		
		this.tiles = new Stack<LakeTile>();
		
		Colour[] colours = Colour.values();
		this.cards = new LanternCardWrapper[colours.length];
		for (int i = 0; i < cards.length; i++) {
			LanternCardWrapper lantern = new LanternCardWrapper();
			lantern.setColour(colours[i]);
			this.cards[i] = lantern;
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

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public List<LakeTile> getLake() {
		return lake;
	}

	public void setLake(List<LakeTile> lake) {
		this.lake = lake;
	}

	public Stack<LakeTile> getTiles() {
		return tiles;
	}

	public void setTiles(Stack<LakeTile> tiles) {
		this.tiles = tiles;
	}

	public LanternCardWrapper[] getCards() {
		return cards;
	}

	public void setCards(LanternCardWrapper[] cards) {
		this.cards = cards;
	}

	public DedicationTokenWrapper[] getDedications() {
		return dedications;
	}

	public void setDedications(DedicationTokenWrapper[] dedications) {
		this.dedications = dedications;
	}
	
	public int getCurrentTurnPlayer() {
		return currentTurnPlayer ;
	}
	
	public void setCurrentTurnPlayer(int id) {
		currentTurnPlayer = id ;
	}
	
}