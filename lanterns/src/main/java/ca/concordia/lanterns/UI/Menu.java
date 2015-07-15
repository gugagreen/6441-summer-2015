package ca.concordia.lanterns.UI;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.*;

/**
 * Created by Ruixiang on 7/14/2015.
 */

public class Menu extends BasicGameState {

    Image backGround = null;
    Image playNow = null;
    Image resumeGame = null;
    Image exitGame = null;

//    String userDir = System.getProperty("user.dir");
//    String imagePath = userDir;

    public Menu(int menu) {
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        backGround = new Image("lanterns/images/BackGround.png", false, Image.FILTER_NEAREST);
        playNow = new Image("lanterns/images/New Game.jpg");
        resumeGame = new Image("lanterns/images/Resume.jpg");
        exitGame = new Image("lanterns/images/Quit.jpg");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
//        System.out.println("Working Directory = " +
//                          System.getProperty("user.dir"));
        backGround.draw(0, 0, 800, 600);
        playNow.draw(325, 430);
        resumeGame.draw(325, 480);
        exitGame.draw(325, 530);
        //graphics.fillOval(3,3,3,3);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        int posX = Mouse.getX();
        int posY = Mouse.getY();

        if ((posX > 325 && posX < 457) && (posY > 135 && posY < 170))
        {
            if(Mouse.isButtonDown(0)){
                stateBasedGame.enterState(1);
            }
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void controllerLeftPressed(int i) {

    }

    @Override
    public void controllerLeftReleased(int i) {

    }

    @Override
    public void controllerRightPressed(int i) {

    }

    @Override
    public void controllerRightReleased(int i) {

    }

    @Override
    public void controllerUpPressed(int i) {

    }

    @Override
    public void controllerUpReleased(int i) {

    }

    @Override
    public void controllerDownPressed(int i) {

    }

    @Override
    public void controllerDownReleased(int i) {

    }

    @Override
    public void controllerButtonPressed(int i, int i1) {

    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int i, char c) {

    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return false;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
