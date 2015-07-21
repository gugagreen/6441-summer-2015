package ca.concordia.lanternsentities.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanternsentities.DedicationToken;
import ca.concordia.lanternsentities.enums.DedicationType;

public class DedicationTokenTest {
	
	private DedicationToken dedicationToken;
	
	@Before
	public void setup(){
		this.dedicationToken = new DedicationToken();
		this.dedicationToken.init(0, DedicationType.GENERIC);
	}
	
	@Test
	public void testHashCode() {
		DedicationToken matchingToken = new DedicationToken();
		matchingToken.init(0, DedicationType.GENERIC);	
		
		int expected = matchingToken.hashCode();
		int result = this.dedicationToken.hashCode();
		assertNotNull(result);
		assertNotNull(expected);
		assertEquals(result, expected);
	}

	@Test
	public void testEqualsObject() {
		assertFalse(this.dedicationToken.equals(null));

		String testString = new String();
		assertFalse(this.dedicationToken.equals(testString));
		
		DedicationToken nonMatchingTypeToken = new DedicationToken();
		nonMatchingTypeToken.init(0, DedicationType.FOUR_OF_A_KIND);
		assertFalse(this.dedicationToken.equals(nonMatchingTypeToken));
		
		DedicationToken nonMatchingValueToken = new DedicationToken();
		nonMatchingValueToken.init(1, DedicationType.GENERIC);
		assertFalse(this.dedicationToken.equals(nonMatchingValueToken));

		DedicationToken matchingToken = new DedicationToken();
		matchingToken.init(0, DedicationType.GENERIC);
		assertTrue(this.dedicationToken.equals(matchingToken));
	}

}
