package ca.concordia.lanterns.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;

/**  Sets up game for players.
 * 
 * @version 1.0
 *
 */
public class Game {

	private final Set<Player> players;
	private final Lake lake;
	private final Stack<LanternCard>[] cards;
	private final Stack<DedicationToken>[] dedications;
	private int favors;
	
	/** Game constructor.
	 * 
	 * @param playerNames should be alphanumeric representations of player identification.
	 */
	@SuppressWarnings("unchecked")
	public Game(final Set<String> playerNames) {
		super();
		
		// TODO - validate there are only 2-4 players
		this.players = new HashSet<Player>();
		for (String name : playerNames) {
			this.players.add(new Player(name));
		}
		
		this.lake = new Lake();
		
		this.cards = new Stack[Colour.values().length];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = new Stack<LanternCard>();
		}
		// TODO - generate cards accordingly to players
		
		this.dedications = new Stack[DedicationType.values().length];
		for (int i = 0; i < dedications.length; i++) {
			dedications[i] = new Stack<DedicationToken>();
		}
		// TODO - generate dedications accordingly to players
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
