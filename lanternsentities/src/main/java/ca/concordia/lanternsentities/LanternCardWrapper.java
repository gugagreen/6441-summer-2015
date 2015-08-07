package ca.concordia.lanternsentities;

import ca.concordia.lanternsentities.enums.Colour;

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

}
