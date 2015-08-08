package ca.concordia.lanternsentities.entities;

import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.TileSide;
import ca.concordia.lanternsentities.enums.Colour;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LakeTileTest {

    Colour[] colours = {Colour.RED, Colour.BLUE, Colour.BLACK, Colour.GREEN};
    Colour[] othercolours = {Colour.BLACK, Colour.BLACK, Colour.BLACK, Colour.BLACK};
    private LakeTile lakeTile;

    @Before
    public void setup() {
        this.lakeTile = new LakeTile();
        this.lakeTile.init(colours, true);
    }

    @Test
    public void testHashCode() {
        LakeTile matchingLakeTile = new LakeTile();
        matchingLakeTile.init(colours, true);

        int expected = matchingLakeTile.hashCode();
        assertNotNull(expected);
        int result = this.lakeTile.hashCode();
        assertNotNull(result);

        assertEquals(result, expected);
    }

    @Test
    public void testEqualsObject() {
        String testString = new String();
        assertFalse(this.lakeTile.equals(testString));

        LakeTile nonMatchingColourLakeTile = new LakeTile();
        nonMatchingColourLakeTile.init(othercolours, true);
        assertNotNull(othercolours);
        assertFalse(this.lakeTile.equals(nonMatchingColourLakeTile));

        LakeTile nonMatchingPlatformLakeTile = new LakeTile();
        nonMatchingPlatformLakeTile.init(colours, false);
        assertFalse(this.lakeTile.equals(nonMatchingPlatformLakeTile));

        LakeTile matchingLakeTile = new LakeTile();
        matchingLakeTile.init(colours, true);
        assertTrue(this.lakeTile.equals(matchingLakeTile));
    }

    @Test
    public void setOrientationTest() {
        TileSide[] expectedOrientedSides = new TileSide[4];
        Colour[] newColours = {Colour.BLACK, Colour.GREEN, Colour.RED, Colour.BLUE};
        for (int i = 0; i != newColours.length; ++i) {
            expectedOrientedSides[i] = new TileSide();
            expectedOrientedSides[i].setColour(newColours[i]);
        }

        this.lakeTile.setOrientation(2);

        TileSide[] sides = lakeTile.getSides();

        for (int i = 0; i != newColours.length; ++i) {
            assertEquals(sides[i], expectedOrientedSides[i]);
        }

        this.lakeTile.setOrientation(0);
        sides = lakeTile.getSides();

        for (int i = 0; i != newColours.length; ++i) {
            assertEquals(sides[i], expectedOrientedSides[i]);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setOrientationThrowTest() {
        TileSide[] expectedOrientedSides = new TileSide[4];
        Colour[] newColours = {Colour.BLACK, Colour.GREEN, Colour.RED, Colour.BLUE};
        for (int i = 0; i != newColours.length; ++i) {
            expectedOrientedSides[i] = new TileSide();
            expectedOrientedSides[i].setColour(newColours[i]);
        }

        this.lakeTile.setOrientation(5);

    }
}
