package ca.concordia.lanterns.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ca.concordia.lanterns.dao.GameDao;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.readwrite.MarshallerManager;
import ca.concordia.lanterns.readwrite.impl.JaxbGameMarshaller;

public class FileGameDao implements GameDao {
	
	private MarshallerManager<Game> marshaller;
	
	public FileGameDao() {
		this.marshaller = new JaxbGameMarshaller();
	}

	@Override
	public void saveGame(String resource, Game game) {
		try {
			Writer writer = new StringWriter(); 
			marshaller.marshall(game, writer);
			
			Path path = Paths.get(resource);
			Files.write(path, writer.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Game loadGame(String resource) {
		Game game = null;
		try {
			FileReader reader = new FileReader(new File(resource));
			game = marshaller.unmarshall(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}

}
