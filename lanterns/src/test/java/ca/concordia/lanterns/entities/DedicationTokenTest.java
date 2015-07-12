package ca.concordia.lanterns.entities;

import static org.junit.Assert.*;

import javax.naming.spi.DirStateFactory.Result;
import javax.print.DocFlavor.STRING;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.entities.enums.DedicationType;
import ca.concordia.lanterns.services.SetupService;

public class DedicationTokenTest {
	
	private DedicationToken dedicationToken;
	
	@Before
	public void setup(){
		this.dedicationToken = new DedicationToken(0, DedicationType.GENERIC);
	}
	
	@Test
	public void testHashCode() {
		DedicationToken matchingToken = new DedicationToken(0, DedicationType.GENERIC);	
		
		int expected = matchingToken.hashCode();
		int result = this.dedicationToken.hashCode();
		
		assertEquals(result, expected);
	}

	@Test
	public void testEqualsObject() {
		assertFalse(this.dedicationToken.equals(null));

		String testString = new String();
		assertFalse(this.dedicationToken.equals(testString));
		
		DedicationToken nonMatchingTypeToken = new DedicationToken(0, DedicationType.FOUR_OF_A_KIND);	
		assertFalse(this.dedicationToken.equals(nonMatchingTypeToken));
		
		DedicationToken nonMatchingValueToken = new DedicationToken(1, DedicationType.GENERIC);	
		assertFalse(this.dedicationToken.equals(nonMatchingValueToken));

		DedicationToken matchingToken = new DedicationToken(0, DedicationType.GENERIC);
		assertTrue(this.dedicationToken.equals(matchingToken));
	}

}
