package ca.concordia.lanternsentities;

import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * Lantern Card Wrapper
 *
 * @version 1.0
 */
public class LanternCardWrapper {

    private Colour colour;
    private int quantity;

    public LanternCardWrapper() {
    }

    @Override
    public String toString() {
        return "[" + colour.key + "=" + quantity + "]";
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The number of lantern cards can't be negative");
        }

        this.quantity = quantity;
    }
    
    public Colour[] sortedLanternColours(LanternCardWrapper[] lanternCards) {
    	int[] sortedLanternsQuantity = new int[lanternCards.length];
    	Colour[] sortedLanternsColour = new Colour[lanternCards.length];
    	
 		int key;
 		int j;
 		Colour color;
 		
 		sortedLanternsColour[0] = lanternCards[0].getColour();
 		sortedLanternsQuantity[0] = lanternCards[0].getQuantity();
 		
 		for (int i = 1; i != lanternCards.length; ++i){
 			
 			key = lanternCards[i].getQuantity();
 			color = lanternCards[i].getColour();
 			
 			j = i - 1;
			while (j != -1 && sortedLanternsQuantity[j] > key){
				sortedLanternsColour[j+1] = sortedLanternsColour[j];
				sortedLanternsQuantity[j+1] = sortedLanternsQuantity[j];
				--j ;
			}
			sortedLanternsColour[j+1] = color;
			sortedLanternsQuantity[j+1] = key;
 		}
 		return sortedLanternsColour;	
    }

}
