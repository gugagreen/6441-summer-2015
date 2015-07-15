package ca.concordia.lanterns.services.impl;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ca.concordia.lanterns.entities.Game;

/** This class is used to load game information/state in an XML file using JAXB */
public class LoadXML {
	
	private Game loadXMLContent(){
	
		JAXBContext jaxbContext;
		Game game = null;
	
		try {
		File file = new File("src//gameState.xml");
		
		jaxbContext = JAXBContext.newInstance(Game.class);
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	
		game = (Game) jaxbUnmarshaller.unmarshal(file);
		
		System.out.print("Game loaded successfully.");
			
		} 
		catch (JAXBException e) {
				System.out.print("JAXBException in GameLoading");
		}
		
		return game;
	}
}