package ca.concordia.lanterns.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.enums.Colour;


public class LanternCardTest {

	private LanternCard lanternCard;

	@Before
	public void setup(){
		this.lanternCard = new LanternCard();
		this.lanternCard.init(Colour.ORANGE);
	}
	
	

	@Test
	public void testHashCode() {
		LanternCard matchingColour = new LanternCard();
		matchingColour.init(Colour.ORANGE);	
		
		int expected = matchingColour.hashCode();
		int result = this.lanternCard.hashCode();
		assertNotNull(result);
		assertNotNull(expected);
		assertEquals(result, expected);
	}
	
	@Test
	public void testEqualsObject() {
		assertFalse(this.lanternCard.equals(null));
			
		LanternCard nonmatchingColour = new LanternCard();
		nonmatchingColour.init(Colour.GREEN);
		assertFalse(this.lanternCard.equals(nonmatchingColour));

		LanternCard matchingColour = new LanternCard();
		matchingColour.init(Colour.ORANGE);
		assertTrue(this.lanternCard.equals(matchingColour));
	}
	
}
