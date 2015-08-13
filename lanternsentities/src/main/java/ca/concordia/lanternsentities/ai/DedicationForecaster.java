package ca.concordia.lanternsentities.ai;

import ca.concordia.lanternsentities.LanternCardWrapper;
import ca.concordia.lanternsentities.Player;


public class DedicationForecaster {
	
	public static DedicationForecaster getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final DedicationForecaster INSTANCE = new DedicationForecaster();
    }
	
	public boolean[] dedicationPossible(Player player){
		boolean[] dedicationsPossible = new boolean[3];
		
		dedicationsPossible[0] = fourOfKindPossible(player);
		dedicationsPossible[1] = threePairPossible(player);
		dedicationsPossible[2] = sevenUniquePossible(player);
		
		return dedicationsPossible;

	}
	
	public boolean fourOfKindPossible(Player player){
		boolean dedicationPossible = false;
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if(lanternCardWrapper.getQuantity() > 3){
				dedicationPossible = true;
				break;
			}
		}
		return dedicationPossible;
	}
	
	public boolean threePairPossible(Player player){
		int pairStacks = 0;
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if(lanternCardWrapper.getQuantity() > 1){
				pairStacks++;
			}
		}
		
		return (pairStacks > 2);
	}
	
	public boolean sevenUniquePossible(Player player){
		int uniqueStacks = 0;
		for (LanternCardWrapper lanternCardWrapper : player.getCards()) {
			if(lanternCardWrapper.getQuantity() > 0){
				uniqueStacks++;
			}
		}
		return (uniqueStacks == 7);
	}

	/**
	 * Find out if there is any dedication one card away from completion.
	 * @param player {@link Player}
	 * @return	A pair of {@link LanternCardWrapper} with the [card to give, card to receive], or null if there is no dedication one card away. 
	 */
	public LanternCardWrapper[] getNextDedication(Player player) {
		LanternCardWrapper[] giveReceive = getNextFourOfAKind(player);
		if (giveReceive == null) {
			giveReceive = getNextThreePairs(player);
			
			if (giveReceive == null) {
				giveReceive = getNextSevenUnique(player);
			}
		}
		return giveReceive;
	}
    
    /**
     * Get necessary exchange to make a four of a kind dedication to given player.
     * @param player {@link Player}
     * @return	A pair of {@link LanternCardWrapper} with the [card to give, card to receive], 
     * or null if there is no dedication one card away. 
     */
    public LanternCardWrapper[] getNextFourOfAKind(Player player) {
		LanternCardWrapper[] giveReceive = null;
		LanternCardWrapper give = null;
		LanternCardWrapper receive = null;
		// check if there is any card to receive 
		for (LanternCardWrapper lantern : player.getCards()) {
			if (lantern.getQuantity() == 3) {
				receive = lantern;
				break;
			}
		}

		// if can receive, check if there is any different card to give
		if (receive != null) {
			for (LanternCardWrapper lantern : player.getCards()) {
				if ((!lantern.equals(receive)) && (lantern.getQuantity() > 0)) {
					give = lantern;
					break;
				}
			}
			
			// if can give and receive, return it
			if (give != null) {
				giveReceive = new LanternCardWrapper[] {give, receive};
			}
		}
		return giveReceive;
	}
	
    /**
     * Get necessary exchange to make a three pairs dedication to given player.
     * @param player {@link Player}
     * @return	A pair of {@link LanternCardWrapper} with the [card to give, card to receive], 
     * or null if there is no dedication one card away. 
     */
    public LanternCardWrapper[] getNextThreePairs(Player player) {
		LanternCardWrapper[] giveReceive = null;
		LanternCardWrapper pair1 = null;
		LanternCardWrapper pair2 = null;
		LanternCardWrapper give = null;
		LanternCardWrapper receive = null;
		// search for first 2 pairs
		for (LanternCardWrapper lantern : player.getCards()) {
			if (lantern.getQuantity() >= 2) {
				// if there is more than 2, this is also the card to give
				if (lantern.getQuantity() >= 3) {
					give  = lantern;
				}
				if (pair1 == null) {
					pair1 = lantern;
				} else if (pair2 == null) {
					pair2 = lantern;
					break;
				}
			}
		}
		// if there is at least 2 pairs, check if there are other cards to give and receive
		if ((pair1 != null) && (pair2 != null)) {
			for (LanternCardWrapper lantern : player.getCards()) {
				if ((!lantern.equals(pair1)) && (!lantern.equals(pair2))) {
					if (lantern.getQuantity() > 0) {
						if (give == null) {
							give = lantern;
						} else if (receive == null) {
							receive = lantern;
							break;
						}
					}
				}
			}
		}
		
		// if can give and receive, return it
		if ((give != null) && (receive != null)) {
			giveReceive = new LanternCardWrapper[] {give, receive};
		}
		
		return giveReceive;
	}
	
    /**
     * Get necessary exchange to make a seven unique dedication to given player.
     * @param player {@link Player}
     * @return	A pair of {@link LanternCardWrapper} with the [card to give, card to receive], 
     * or null if there is no dedication one card away. 
     */
    public LanternCardWrapper[] getNextSevenUnique(Player player) {
		LanternCardWrapper[] giveReceive = null;
		LanternCardWrapper give = null;
		LanternCardWrapper receive = null;
		int countNotEmpty = 0;
		
		// search into lanterns to check if there is any with zero qty (to receive) and any with more than one (to give)
		for (LanternCardWrapper lantern : player.getCards()) {
			if (lantern.getQuantity() > 0) {
				countNotEmpty++;
				if (lantern.getQuantity() > 1) {
					give = lantern;
				}
			} else if (lantern.getQuantity() == 0) {
				receive = lantern;
			}
		}
		// there should be 6 not empty to be able to complete 7 after exchange
		if ((give != null) && (receive != null) && (countNotEmpty == 6)) {
			giveReceive = new LanternCardWrapper[] {give, receive};
		}
		
		return giveReceive;
	}
}
