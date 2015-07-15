package ca.concordia.lanterns.UI;
/**
 * Created by Ruixiang on 7/14/2015.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.*;

public class LanternGame extends StateBasedGame
{
    public static final String gameName = "Lanterns: The Harvest Festival";
    public static final int menu = 0;
    public static final int play = 1;

    public LanternGame(String gameName)
    {
        super(gameName);
        this.addState(new Menu(menu));
        this.addState(new Play (play));
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(menu).init(gameContainer, this);
        this.getState(play).init(gameContainer, this);
        this.enterState(menu);
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new LanternGame(gameName));
            appgc.setDisplayMode(800, 600, false);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(LanternGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}