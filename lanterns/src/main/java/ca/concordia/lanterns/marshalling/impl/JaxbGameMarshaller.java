package ca.concordia.lanterns.marshalling.impl;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.entities.LanternCard;
import ca.concordia.lanterns.services.impl.DefaultSetupService;


public class JaxbGameMarshaller {

	public static String marshall(Game game) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Game.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		marshaller.marshal(game, sw);
		return sw.toString();
	}
	
	public static String marshall(LanternCard card) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(LanternCard.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		marshaller.marshal(card, sw);
		return sw.toString();
	}
	
	public static void main(String[] args) {
		DefaultSetupService service = new DefaultSetupService();
		Game game = service.createGame(new String[] {"p1", "p2", "p3"});
		LanternCard card = game.getCards()[0].elementAt(0);
		try {
			System.out.println(marshall(game));
			System.out.println(marshall(card));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
