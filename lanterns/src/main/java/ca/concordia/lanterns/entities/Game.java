package ca.concordia.lanterns.entities;

import java.util.HashMap;
import java.util.Stack;

import ca.concordia.lanterns.entities.enums.DedicationTokenType;
import ca.concordia.lanterns.entities.enums.PlayerID;


/**
 * 
 * 
 *
 * Entity that contains all game state.
 * @version 1.0
 */
public class Game {
	public static final int TOTAL_FAVORS = 20;

	/** Players in the game. */
	private final Player[] players;
	/** Lake where tiles are being displayed for all users. */
	private final Lake lake;
	/** Stack of Lake Tiles to be distributed. */
	private final Stack<LakeTile> tiles;
	/** Stack of Lantern Cards to be distributed. */
	private final HashMap<LanternCard, Integer> cards;
	/** Stack of Dedication Tokens to be distributed. */
	private final HashMap<DedicationTokenType, Stack<DedicationToken>> dedications;
	/** Quantity of favors to be distributed to players. */
	private int favors;
	private PlayerID startPlayerMaker ;
	

	/**
	 * Instantiates a new Game based on the player names.
	 * <p>This constructor will instantiate (but not populate) all attributes of this Game instance.
	 * 
	 * @param playerNames Names of each current player (ordered by login time)
	 */
//	@SuppressWarnings("unchecked")
	public Game(final String[] playerNames) {
		super();
		
		this.players = new Player[playerNames.length];
		PlayerID[] playerid = PlayerID.values() ;
		for (int i = 0; i < playerNames.length; i++) {
			this.players[i] = new Player(playerNames[i], playerid[i]);
		}
		
		this.lake = new Lake();
		
		this.tiles = new Stack<LakeTile>();
		
		this.cards = new HashMap<LanternCard, Integer> ();
				
		this.dedications = new HashMap<DedicationTokenType, Stack<DedicationToken>> () ;
				
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
	
	public Lake getLake() {
		return lake;
	}
	
	public Stack<LakeTile> getTiles() {
		return this.tiles;
	}

	public HashMap<LanternCard, Integer> getCards() {
		return cards;
	}

	public HashMap<DedicationTokenType, Stack<DedicationToken>> getDedications() {
		return dedications;
	}
	
	public PlayerID getStartPlayerMaker () {
		return startPlayerMaker ;
	}
	
	public void setStartPlayerMaker ( PlayerID startPlayerMaker ) {
		this.startPlayerMaker = startPlayerMaker ;
	}
	
	
}
