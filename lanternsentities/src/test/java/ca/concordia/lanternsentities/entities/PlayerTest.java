package ca.concordia.lanternsentities.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanternsentities.Player;

/* player class test with junit test cases*/

public class PlayerTest {
	
	private Player player;
	
	@Before
	public void setup(){
		this.player = new Player();
		this.player.init("John", 1);
	}
	
	@Test
	public void testHashCode() {
		Player matchingPlayer = new Player();
		matchingPlayer.init("John", 1);	
		
		int expected = matchingPlayer.hashCode();
		int result = this.player.hashCode();
		assertNotNull(result);
		assertNotNull(expected);
		
	}

	@Test
	public void testEqualsObject() {
		assertFalse(this.player.equals(null));

		String testString = new String();
		assertFalse(this.player.equals(testString));
		
	}


}
