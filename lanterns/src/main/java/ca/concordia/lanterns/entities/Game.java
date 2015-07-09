package ca.concordia.lanterns.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;

/**
 * Entity that contains all game state.
 */
public class Game {

	/** Players in the game. */
	private final Set<Player> players;
	/** Lake where tiles are being displayed for all users. */
	private final Lake lake;
	/** Stack of Lantern Cards to be distributed. */
	private final Stack<LanternCard>[] cards;
	/** Stack of Dedication Tokens to be distributed. */
	private final Stack<DedicationToken>[] dedications;
	/** Quantity of favors to be distributed to players. */
	private int favors;
	
	/**
	 * Instantiates a new Game based on the player names.<br/>
	 * This constructor will instantiate (but not populate) all attributes of this Game instance.
	 * 
	 * @param playerNames
	 */
	@SuppressWarnings("unchecked")
	public Game(final Set<String> playerNames) {
		super();
		
		this.players = new HashSet<Player>();
		for (String name : playerNames) {
			this.players.add(new Player(name));
		}
		
		this.lake = new Lake();
		
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

	public void setFavors(int favors) {
		this.favors = favors;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public Lake getLake() {
		return lake;
	}

	public Stack<LanternCard>[] getCards() {
		return cards;
	}

	public Stack<DedicationToken>[] getDedications() {
		return dedications;
	}
	
	
}
