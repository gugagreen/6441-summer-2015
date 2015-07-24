package ca.concordia.lanternsentities.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanternsentities.LakeTile;
import ca.concordia.lanternsentities.enums.Colour;

public class LakeTileTest {
	
	private LakeTile lakeTile;
	Colour[] colours = {Colour.RED, Colour.BLUE, Colour.BLACK, Colour.GREEN};
	Colour[] othercolours = {Colour.BLACK, Colour.BLACK, Colour.BLACK, Colour.BLACK};
	
	@Before
	public void Setup(){
		this.lakeTile = new LakeTile();
		assertNotNull(colours);
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
		assertFalse(this.lakeTile.equals(null));
		assertFalse(othercolours.equals(colours));
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
}