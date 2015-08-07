package ca.concordia.lanternsentities.entities;

import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TileSideTest {

    private TileSide tileSide;

    @Before
    public void setup() {
        this.tileSide = new TileSide();
        this.tileSide.init(Colour.ORANGE);
    }

    @Test
    public void testHashCode() {
        TileSide matchingTile = new TileSide();
        matchingTile.init(Colour.ORANGE);

        int expected = matchingTile.hashCode();
        int result = this.tileSide.hashCode();
        assertNotNull(result);
        assertNotNull(expected);

    }

    @Test
    public void testEqualsObject() {
        assertFalse(this.tileSide.equals(null));

        TileSide nonMatchingTile = new TileSide();
        nonMatchingTile.init(Colour.GREEN);
        assertFalse(this.tileSide.equals(nonMatchingTile));

        TileSide matchingTile = new TileSide();
        matchingTile.init(Colour.ORANGE);
        assertTrue(this.tileSide.equals(matchingTile));
    }


}
