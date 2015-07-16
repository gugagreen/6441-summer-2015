package ca.concordia.lanterns.Controller;

import java.util.Scanner;

import ca.concordia.lanterns.dao.impl.FileGameDao;
import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.services.impl.DefaultSetupService;

public class GameController {
	
public static void main(String[] args) {
		
		DefaultSetupService setupService = new DefaultSetupService();
		Game game;
		
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
	
		System.out.println("GROUP A SOEN 6441 LANTERNS BUILD 1");
		System.out.println("Select the following: ");
		System.out.println("1) Start a new game");
		System.out.println("2) Load game from file");
		System.out.println("3) Quit");
		
		int selectedOption = keyboard.nextInt();
		
		switch (selectedOption) {
		case 1:
			System.out.println("Specify the number of players (2-4)");
			int numberOfPlayers = keyboard.nextInt();
			if(numberOfPlayers > 4){
				System.out.println("Too many players");	
			}
			String[] playerNames = new String[numberOfPlayers];

			for (int i = 0; i < numberOfPlayers; i++) {
				playerNames[i] = Integer.toString(i);
			}
			
			game =  setupService.createGame(playerNames);
			game.displayCurrentGameState(); 
			
			System.out.println("Successfully Initialized game");
			System.out.println("1)Save Game");
			System.out.println("2)Quit");
			int newGameSaveOption = keyboard.nextInt();
			
			switch (newGameSaveOption) {
			case 1:
				if(game != null){
					System.out.println("Specify the name of the save file with the extension (.xml)");
					String saveFileName = keyboard.next();
				
					FileGameDao write = new FileGameDao () ;
					write.saveGame(saveFileName, game);
					System.out.println("Saved game, quitting.");
				}	
				
				break;
				
			case 2:
				System.out.println("Quitting");
				return;
				
			default:
				System.out.println("Unsuported Option, now exiting.");
				return;
			}
			
			break;
			
		case 2:
			System.out.println("Specify the name of the load file with the extension (.xml)");
			String loadFileName = keyboard.next();
			
			FileGameDao read = new FileGameDao () ;
			game = read.loadGame(loadFileName);
			
			game.displayCurrentGameState(); 
			
			
			System.out.println("Successfully loaded game");
			System.out.println("1)Save Game");
			System.out.println("2)Quit");
			int saveOption = keyboard.nextInt();
			
			switch (saveOption) {
			case 1:
				System.out.println("Specify the name of the save file with the extension (.xml)");
				String saveFileName = keyboard.next();

				FileGameDao write = new FileGameDao () ;
				write.saveGame(saveFileName, game);
				System.out.println("Saved game, quitting.");
			
				break;
				
			case 2:
				System.out.println("Quitting");
				return;
				
			default:
				System.out.println("Unsuported Option, now exiting.");
				return;
			}

			
			break;
			

			


			
		case 3:
			System.out.println("Quitting");
			return;

		default: 
			System.out.println("Unsuported Option, now exiting.");
			return;
		}
		
	}
}
