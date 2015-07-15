package ca.concordia.lanterns.readwrite.impl;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.readwrite.MarshallerManager;
import ca.concordia.lanterns.services.impl.DefaultSetupService;


public class JaxbGameMarshaller implements MarshallerManager<Game> {

	@Override
	public Writer marshall(Game game) {
		try {
			JAXBContext context = JAXBContext.newInstance(Game.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			marshaller.marshal(game, sw);
			return sw;
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
	
	public static void main(String[] args) {
		DefaultSetupService service = new DefaultSetupService();
		Game game = service.createGame(new String[] {"p1", "p2", "p3"});
		MarshallerManager<Game> mm = new JaxbGameMarshaller();
		Writer writer = mm.marshall(game);
		System.out.println(writer.toString());
		System.out.println("\n\n\n**********\n\n\n");
		Reader reader = new StringReader(writer.toString());
		Game readed = mm.unmarshall(reader);
		System.out.println(readed);
		System.out.println("total players: " + readed.getPlayers().length);
		System.out.println("3rd player name: " + readed.getPlayers()[2].getName());
	}
}
