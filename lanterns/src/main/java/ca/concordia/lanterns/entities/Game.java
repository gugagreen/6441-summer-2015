package ca.concordia.lanterns.entities;

import java.util.HashSet;
import java.util.Set;
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
	private final Set<Player> players;
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
	 * @param playerNames Names of each current player
	 */
	@SuppressWarnings("unchecked")
	public Game(final Set<String> playerNames) {
		super();
		
		this.players = new HashSet<Player>();
		for (String name : playerNames) {
			this.players.add(new Player(name));
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

	/**
	 * This method returns the number of Favor Tokens to the constructor.
	 * @return The number of favors.
	 */
	public int getFavors() {
		return favors;
	}

	/**
	 * This method sets the number of Favor Tokens.
	 * @param favors range <code>1..20</code>.
	 */
	public void setFavors(int favors) {
		this.favors = favors;
	}

	/**
	 * This method returns the number of players to the constructor.
	 * @return The number of players, range <code>2-4</code>.
	 */
	public Set<Player> getPlayers() {
		return players;
	}
	/** This method returns the Lake.
	 * 
	 * @return Lake object.
	 */
	public Lake getLake() {
		return lake;
	}
	
	public Stack<LakeTile> getTiles() {
		return this.tiles;
	}

	/**
	 * Returns the cards in the stack of lantern cards to the constructor.
	 * @return Lantern Cards, total 56.
	 */
	public Stack<LanternCard>[] getCards() {
		return cards;
	}

	/**
	 * Returns Dedication Tokens to the constructor.
	 * 
	 * @return Dedication Tokens, quantity dependent on number of players.
	 */
	public Stack<DedicationToken>[] getDedications() {
		return dedications;
	}
	
	
}
