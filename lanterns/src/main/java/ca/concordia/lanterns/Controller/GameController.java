package ca.concordia.lanterns.Controller;

import java.util.Scanner;

import ca.concordia.lanterns.dao.impl.FileGameDao;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.services.impl.DefaultSetupService;

public class GameController {
	
	public static void main (String[] args) {
		
		Scanner scanner = new Scanner(System.in); 
		System.out.println( "Enter the number of players: " ) ;
		int playerCount = scanner.nextInt() ;
		String[] playerNames = new String[playerCount] ;
		for ( int i = 0; i != playerCount; ++i ) {
			System.out.println ( "Enter the name of player " + i + 1+ " :"  ) ;
			playerNames[i] = scanner.next() ;
		}
		
		DefaultSetupService service = new DefaultSetupService() ;
		Game game =  service.createGame(playerNames);
		game.displayCurrentGameState(); 
		
		FileGameDao write = new FileGameDao () ;
		write.saveGame("Controller/savedGame.xml", game);
	}
}
