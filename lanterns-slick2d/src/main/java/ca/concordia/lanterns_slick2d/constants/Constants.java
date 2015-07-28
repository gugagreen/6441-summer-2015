package ca.concordia.lanterns_slick2d.constants;

import java.io.File;

/**
 * An interface with all the constants used in our game view.
 * 
 */
public interface Constants {
	String GAME_TITLE = "Lanterns - Team A";
	String IMG_FOLDER = "img" + File.separator;
	String CONFIGFILEPATH = "properties/config.properties";
    String BACKGROUND_IMG = IMG_FOLDER + "background.jpg";
	String TILE_IMG_FOLDER = IMG_FOLDER + "tiles" + File.separator;
	String TILE_BACK_IMG = TILE_IMG_FOLDER + "back-tile.png";
	String TILE_PREFIX = "tiles_";
	String CARD_IMG_FOLDER = IMG_FOLDER + "cards" + File.separator;
	String JPG = ".jpg";
	
	int CARDS_X =10;
	int CARDS_Y = 10;
	int FAVORS_X = 100;
	int FAVORS_Y = 350;
	int TILE_STACK_X = 10;
	int TILE_STACK_Y = 670;
	int INIT_TILE_X = 500;
	int INIT_TILE_Y = 350;

	int PLAYER1_X = 100;
    int PLAYER1_Y = 200;

    int PLAYER2_X = 100;
    int PLAYER2_Y = 250;

    int PLAYER3_X = 100;
    int PLAYER3_Y = 300;

    int PLAYER4_X = 100;
    int PLAYER4_Y = 350;

    int PLAYER_WIDTH = 100;
    int PLAYER_HEIGHT = 25;

    int START_BUTTON_X = 500;
    int START_BUTTON_Y = 300;
}
