package ca.concordia.lanternsentities;

import java.util.Arrays;
import java.util.Stack;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanternsentities.ai.AI;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;


/**
 * Entity that contains all game state.
 *
 * @version 1.0
 */
@XmlRootElement
public class Game {
    public static final int TOTAL_FAVORS = 20;

    private String id;
    /**
     * AI Players in the game.
     */
    private AI[] aiPlayers;
    /**
     * Lake where tiles are being displayed for all users.
     */
    private LakeTile[][] lake;
    /**
     * Stack of Lake Tiles to be distributed.
     */
    private Stack<LakeTile> tiles;
    /**
     * Stack of Lantern Cards to be distributed.
     */
    private LanternCardWrapper[] cards;
    /**
     * Stack of Dedication Tokens to be distributed.
     */

    private DedicationTokenWrapper[] dedications;
    /**
     * Quantity of favors to be distributed to players.
     */
    private int favors;
    /**
     * Index to mark current player in {@link #players}
     */
    private int currentTurnPlayer;
    /**
     * Says if game is already started.
     */
    private boolean started;

    /**
     * Initializes a new Game based on the player names.
     * <p>This constructor will instantiate (but not populate) all attributes of this Game instance.
     *
     * @param playerNames Names of each current player (ordered by login time)
     * @param id          The game id.
     */
    public void init(final AI[] ais, final String id) {
        this.id = id;

        this.aiPlayers = ais;

        this.lake = new LakeTile[0][0];

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

    @Override
    public String toString() {
        return "Game [id=" + id
                + ", favors=" + favors
                + ", currentTurnPlayer=" + currentTurnPlayer
                + ", started=" + started
                + ", \naiPlayers=" + Arrays.toString(aiPlayers)
                + ", \nlake=" + lake
                + ", \ntiles=" + tiles
                + ", \ncards=" + Arrays.toString(cards)
                + ", \ndedications=" + Arrays.toString(dedications) + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFavors() {
        return favors;
    }

    /**
     * This method sets the number of Favor Tokens.
     *
     * @param favors range <code>0..20</code>.
     */
    public void setFavors(int favors) {
        if (favors >= 0 && favors <= 20) {
            this.favors = favors;
        } else {
            throw new IllegalArgumentException("Number of Favor Tokens should be in the range [0..20]");
        }
    }

    public int getNextPlayer() {
        int next = currentTurnPlayer + 1;
        if (next >= aiPlayers.length) {
            next = 0;
        }
        return next;
    }

    public Player[] getPlayers() {
    	Player[] players = new Player[aiPlayers.length];
    	for (int i = 0; i < players.length; i++) {
			players[i] = aiPlayers[i].getPlayer();
		}
    	return players;
    }

    public AI[] getAiPlayers() {
		return aiPlayers;
	}

	public void setAiPlayers(AI[] aiPlayers) {
		this.aiPlayers = aiPlayers;
	}

	public LakeTile[][] getLake() {
        return lake;
    }

    public void setLake(LakeTile[][] lake) {
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

    @XmlElementWrapper(required = true, nillable = true)
    public DedicationTokenWrapper[] getDedications() {
        return dedications;
    }

    public void setDedications(DedicationTokenWrapper[] dedications) {
        this.dedications = dedications;
    }

    public int getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public void setCurrentTurnPlayer(int id) {
        currentTurnPlayer = id;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
    public Player getPlayer(int playerID){
    	for (Player p: this.getPlayers()){
			if (p.getId() == playerID){
				return p;
			}
		}
    	
    	return null;
    }

}
