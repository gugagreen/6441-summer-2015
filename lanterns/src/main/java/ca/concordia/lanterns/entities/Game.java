package ca.concordia.lanterns.entities;

import java.util.Stack;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;


/**
 * 
 * 
 *
 * Entity that contains all game state.
 * @version 1.0
 */
public class Game {

	/** Players in the game. */
	private final Player[] players;
	/** Lake where tiles are being displayed for all users. */
	private final Lake lake;
	/** Stack of Lake Tiles to be distributed. */
	private final Stack<LakeTile> tiles;
	/** Stack of Lantern Cards to be distributed. */
	private final Stack<LanternCard>[] cards;
	/** Stack of Dedication Tokens to be distributed. */
	private final Stack<DedicationToken>[] dedications;
	/** Quantity of favors to be distributed to players. */
	private int favors;
	

	/**
	 * Instantiates a new Game based on the player names.
	 * <p>This constructor will instantiate (but not populate) all attributes of this Game instance.
	 * 
	 * @param playerNames Names of each current player (ordered by login time)
	 */
	@SuppressWarnings("unchecked")
	public Game(final String[] playerNames) {
		super();
		
		this.players = new Player[playerNames.length];
		for (int i = 0; i < playerNames.length; i++) {
			this.players[i] = new Player(playerNames[i]);
		}
		
		this.lake = new Lake();
		
		this.tiles = new Stack<LakeTile>();
		
		this.cards = new Stack[Colour.values().length];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new Stack<LanternCard>();
		}
		
		this.dedications = new Stack[DedicationType.values().length];
		for (int i = 0; i < dedications.length; i++) {
			dedications[i] = new Stack<DedicationToken>();
		}
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
	
	public Lake getLake() {
		return lake;
	}
	
	public Stack<LakeTile> getTiles() {
		return this.tiles;
	}

	public Stack<LanternCard>[] getCards() {
		return cards;
	}

	public Stack<DedicationToken>[] getDedications() {
		return dedications;
	}
	
	
}
