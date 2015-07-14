package ca.concordia.lanterns.services.impl;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import ca.concordia.lanterns.entities.Game;

public class SaveToXML {
	
	public void saveGame(Game game){
		
		JAXBContext jaxbContext;
				
		try {
			jaxbContext = JAXBContext.newInstance( Game.class );
		
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
			jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			
			jaxbMarshaller.marshal( game, new File( "src\\gameState.xml" ) );
			
			jaxbMarshaller.marshal( game, System.out );
			System.out.print("Game saved successfully");
		
		} catch (JAXBException e) {
			System.out.print("JAXBException in GameSave");
		}

	}
}
