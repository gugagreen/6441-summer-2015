package ca.concordia.lanterns.readwrite.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import ca.concordia.lanterns.GameStubber;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.readwrite.MarshallerManager;

import static org.custommonkey.xmlunit.XMLAssert.*;

public class JaxbGameMarshallerTest {
	public static final String TEST_FILE_PATH = "target/test-classes/jaxbGameTest.xml";
	
	private MarshallerManager<Game> marshaller;
	
	@Before
	public void setup() {
		this.marshaller = new JaxbGameMarshaller();
	}
	
	@Test
	public void testMarshallGame() throws Exception, IOException {
		Game game = GameStubber.createGameStub();
		assertNotNull(game);
		Writer writer = new StringWriter(); 
		marshaller.marshall(game, writer);
		String result = writer.toString().trim();
		
		assertNotNull(result);
		assertTrue(result.length() > 0);
		
		Scanner scanner = new Scanner(new File(TEST_FILE_PATH));
		String content = scanner.useDelimiter("\\Z").next().trim();
		scanner.close();
		assertNotNull(content);
		
		assertXMLEqual(content, result);
	}
	
	@Test
	public void testUnMarshallGame() throws FileNotFoundException {
		FileReader reader = new FileReader(new File(TEST_FILE_PATH));
		Game game = marshaller.unmarshall(reader);
		assertNotNull(game);
		assertNotNull(game.getPlayers());
		assertEquals(3, game.getPlayers().length);
	}
}
