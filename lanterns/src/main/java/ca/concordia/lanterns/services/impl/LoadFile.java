package ca.concordia.lanterns.services.impl;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;



public final class LoadFile {
	
	
	public static ArrayList<String> loadGameState(String fileName){
			     
       ArrayList<String> contentToRead = new ArrayList<String>();
       FileInputStream	fis = null;
       BufferedReader	bufferedReader = null;
        
           try {
        	   		fis = new FileInputStream(fileName);
        	   		InputStreamReader inputStreamReader = new InputStreamReader(fis);
        	   		bufferedReader = new BufferedReader(inputStreamReader);             		                 
             		String line = bufferedReader.readLine();
             		contentToRead.add(line);
             		while(line != null){
                    	line = bufferedReader.readLine();
                    	if(line!=null)
                    		contentToRead.add(line);
             		}           
          
        	}
        	catch (FileNotFoundException ex) {
        	System.out.println("File Not Found.");
            
        	} catch (IOException ex) {
            
        	System.out.println("IO Exception.");
        	} 
        	
        	finally {
        			try {
        			bufferedReader.close(); 
        			fis.close();
        			} 
        			catch (IOException ex){
        				System.out.println("IO Exception(While closing)."); 
        			}
        	}
           System.out.println("Game read successfully !");
        return 	contentToRead;
	}


}
