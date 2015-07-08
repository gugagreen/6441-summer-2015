package ca.concordia.lanterns.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;

public class Game {

	private final Set<Player> players;
	private final Lake lake;
	private final Stack<LanternCard>[] cards;
	private final Stack<DedicationToken>[] dedications;
	private int favors;
	
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
