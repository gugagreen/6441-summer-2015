package ca.concordia.lanterns.readwrite.impl;

import java.io.Reader;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.readwrite.MarshallerManager;


public class JaxbGameMarshaller implements MarshallerManager<Game> {

	@Override
	public void marshall(final Game game, final Writer writer) {
		try {
			JAXBContext context = JAXBContext.newInstance(Game.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(game, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new InternalError("Error during game marshalling");
		}
	}

	@Override
	public Game unmarshall(Reader reader) {
		try {
			JAXBContext context = JAXBContext.newInstance(Game.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Game game = (Game)unmarshaller.unmarshal(reader);
			return game;
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new InternalError("Error during game unmarshalling");
		}
	}
}
