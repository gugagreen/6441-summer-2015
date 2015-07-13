package ca.concordia.lanterns.services.impl;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;



public final class SaveFile {
	
		
	public static void saveGameState(ArrayList<String> contentToWrite){

		
		Scanner scanner=new Scanner(System.in);
		System.out.println("Please save the game :");
		String game = scanner.next();
		
			try {
					File file = new File(game);
					FileOutputStream fos = new FileOutputStream(file);
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
	 				BufferedWriter bw = new BufferedWriter(outputStreamWriter);
	 
	 					for (int i = 0; i < contentToWrite.size(); i++) {
	 							bw.write(contentToWrite.get(i));
	 							if(i!=contentToWrite.size()-1)
	 							bw.newLine();
	 					}
	 
	 					bw.close();
				} 		
			
						catch (FileNotFoundException ex) {
								System.out.println("File Not Found.");
            
						} catch (IOException ex) {            
							System.out.println("IO Exception.");
						} 
		
				System.out.println("The game has been saved.");
	}


}
