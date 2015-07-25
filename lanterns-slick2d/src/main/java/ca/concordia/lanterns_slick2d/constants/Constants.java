package ca.concordia.lanterns_slick2d.constants;

import java.io.File;

public interface Constants {
	String GAME_TITLE = "Lanterns - Team A";
	String IMG_FOLDER = "img" + File.separator;
	String TILE_IMG_FOLDER = IMG_FOLDER + "tiles" + File.separator;
	String TILE_BACK_IMG = TILE_IMG_FOLDER + "back-tile.png";
	String TILE_PREFIX = "tiles_";
	String JPG = ".jpg";
	
	int CARDS_X =10;
	int CARDS_Y = 10;
	int FAVORS_X = 100;
	int FAVORS_Y = 350;
	int TILE_STACK_X = 10;
	int TILE_STACK_Y = 670;
	int INIT_TILE_X = 500;
	int INIT_TILE_Y = 350;
}
