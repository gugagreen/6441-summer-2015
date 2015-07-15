package ca.concordia.lanterns.entities;

import static org.junit.Assert.*;

import javax.swing.plaf.ColorUIResource;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.enums.Colour;
import ca.concordia.lanterns.entities.enums.DedicationType;
import ca.concordia.lanterns.services.SetupService;

public class LakeTileTest {
	
	private LakeTile lakeTile;
	Colour[] colours = {Colour.RED, Colour.BLUE, Colour.BLACK, Colour.GREEN};
	Colour[] othercolours = {Colour.BLACK, Colour.BLACK, Colour.BLACK, Colour.BLACK};
	
	@Before
	public void Setup(){
		this.lakeTile = new LakeTile();
		this.lakeTile.init(colours, true);
	}

	@Test
	public void testHashCode() {
		LakeTile matchingLakeTile = new LakeTile();	
		matchingLakeTile.init(colours, true);
		
		int expected = matchingLakeTile.hashCode();
		int result = this.lakeTile.hashCode();
		
		assertEquals(result, expected);
	}

	@Test
	public void testEqualsObject() {
		assertFalse(this.lakeTile.equals(null));

		String testString = new String();
		assertFalse(this.lakeTile.equals(testString));
		
		LakeTile nonMatchingColourLakeTile = new LakeTile();
		nonMatchingColourLakeTile.init(othercolours, true);
		assertFalse(this.lakeTile.equals(nonMatchingColourLakeTile));
		
		LakeTile nonMatchingPlatformLakeTile = new LakeTile();
		nonMatchingPlatformLakeTile.init(colours, false);
		assertFalse(this.lakeTile.equals(nonMatchingPlatformLakeTile));

		LakeTile matchingLakeTile = new LakeTile();
		matchingLakeTile.init(colours, true);
		assertTrue(this.lakeTile.equals(matchingLakeTile));
	}
}
