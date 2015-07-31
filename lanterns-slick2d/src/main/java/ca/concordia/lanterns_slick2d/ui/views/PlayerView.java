package ca.concordia.lanterns_slick2d.ui.views;

import ca.concordia.lanterns_slick2d.constants.Constants;
import ca.concordia.lanterns_slick2d.ui.buttons.Tile;
import ca.concordia.lanternsentities.Player;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * A UI entity that implements {@link Game} and controls the player view.
 */
public class PlayerView implements Game {

    private Player player;
    private CardStacksView cardStacks;
    private Tile tileStack;
    private boolean vertical;
    private int x;
    private int y;

    public PlayerView(boolean vertical, int x, int y) {
        super();
        this.vertical = vertical;
        this.x = x;
        this.y = y;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.cardStacks.setCards(player.getCards());
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        if (vertical) {
            initCardStacks(x, (y + 100), container);
            initTileStack(x, (y + 20), container);
        } else {
            initCardStacks((x + 80), (y + 20), container);
            initTileStack(x, (y + 20), container);
        }
    }

    private void initCardStacks(int xStack, int yStack, GameContainer container) throws SlickException {
        cardStacks = new CardStacksView(vertical, xStack, yStack);
        cardStacks.init(container);
    }

    private void initTileStack(int xTile, int yTile, GameContainer container) throws SlickException {
        tileStack = new Tile(container, Constants.TILE_BACK_IMG, xTile, yTile);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        // nothing by now
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        cardStacks.render(container, g);
        tileStack.render(container, g);
        if (player != null) {
            g.drawString(player.getName(), x, y);
            String tileQty = player.getTiles().size() + "x";
            g.drawString(tileQty, tileStack.getX() + 10, tileStack.getY() + 10);
        }
    }

    @Override
    public boolean closeRequested() {
        return true;
    }

    @Override
    public String getTitle() {
        return null;
    }

}
