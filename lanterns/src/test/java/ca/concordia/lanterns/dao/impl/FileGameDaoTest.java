package ca.concordia.lanterns.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import ca.concordia.lanterns.GameStubber;
import ca.concordia.lanternsentities.Game;


import static org.custommonkey.xmlunit.XMLAssert.*;


public class FileGameDaoTest {
	
	public static final String TEST_FILE_FOLDER = "target/test-classes/";
	
	// base file to compare
	public static final String TEST_FILE_PATH = TEST_FILE_FOLDER + "jaxbGameTest.xml";

	private FileGameDao dao;
	
	private Game baseGame;
	
	@Before
	public void setup() {
		this.dao = new FileGameDao();
		this.baseGame = GameStubber.createGameStub();
	}
	
	@Test
	public void testSaveGame() throws IOException, SAXException {
		String newFilePath = TEST_FILE_FOLDER + System.currentTimeMillis();
		assertNotNull(newFilePath);
		// save game to file
		this.dao.saveGame(newFilePath, baseGame);
		
		File file = new File(newFilePath);
		
		assertTrue(file.exists());
		assertFalse(file.isDirectory());
		
		// check if new file content matches the base file
		String newContent = new String(Files.readAllBytes(Paths.get(newFilePath)));
		assertNotNull(newContent);
		String baseContent = new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)));
		assertNotNull(baseContent);
		assertXMLEqual(newContent, baseContent);
		
		// delete new file in the end
		file.delete();
	}
	
	@Test
	public void testLoadGame() {
		Game game = this.dao.loadGame(TEST_FILE_PATH);
		assertNotNull(game);
		assertEquals(baseGame.getPlayers().length, game.getPlayers().length);
		assertEquals(baseGame.getPlayers()[0].getName(), game.getPlayers()[0].getName());
	}
}
