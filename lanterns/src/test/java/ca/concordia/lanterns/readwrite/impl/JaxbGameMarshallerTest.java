package ca.concordia.lanterns.readwrite.impl;

import ca.concordia.lanterns.GameStubber;
import ca.concordia.lanterns.readwrite.MarshallerManager;
import ca.concordia.lanternsentities.Game;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.*;

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
        
        System.out.println(result);

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
