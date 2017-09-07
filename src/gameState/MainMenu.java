package gameState;

import graphics.*;
import graphics.Font;
import graphics.Graphics;
import level.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by bubof on 05.07.2017.
 *
 * One of the gameStates
 */
public class MainMenu extends GameState{

    //font we will be working with
    private graphics.Font font;
    private int currentOption = 0;
    private String[] options = {"Play!","Exit"};


    private Tile testTile = Tile.GRASS_TILE_2;
//    private Tile tesTile2 = Tile.GRASS_TILE_2;

    /**
     * @param gsm GameStateManager
     * @param screen screen where everything from object will be drawn
     * */
    public MainMenu(GameStateManager gsm, Screen screen){
        super(gsm,screen);
        font = new Font("arialFont", "/fonts/arialFont.png", 16, 16, new int[]{13, 13, 13, 13, 13, 13});
        font.setColor(Color.ORANGE);
    }

    /**
     * Main method responsible for drawing on screen
     * */
    public void draw(){

        font.setColor(Color.MAGENTA);
        screen.renderString("Lubos Game",this.font,100,10,0,20,20);
        font.setColor(Color.WHITE);
        for(int i = 0; i < options.length;i++){
            screen.renderString(options[i],font,145,40+25*i,0);
        }
        Graphics.drawFillRectangle(screen,139,46 + currentOption*25,4,4,Color.YELLOW);
        Graphics.drawFillRectangle(screen,225,46 + currentOption*25,4,4,Color.YELLOW);
//        Graphics.drawRectangle(screen,300,100,7,5,Color.CYAN);
    }

    /**
     * Main method responsible for updateing
     * */
    public void update(double delta){
    }

    /**
     * these methods handles key events
     * */
    public void keyPressed(int k){
        if(k == KeyEvent.VK_DOWN){
            currentOption++;
            checkBounds();
        }else if(k == KeyEvent.VK_UP){
            currentOption--;
            checkBounds();
        }else if(k == KeyEvent.VK_ENTER){
            confirm();
        }

    }

    public void keyReleased(int k){

    }

    public void keyTyped(int k){

    }

    /**
     * Handles bound exception when pressing arrows and choosing menu options
     * */
    private void checkBounds(){
        if(currentOption < 0)
            currentOption = options.length-1;
        else if(currentOption > (options.length-1))
            currentOption = 0;
    }


    /**
     * Handles event after pressing Enter button
     * */
    private void confirm(){
        if(currentOption == 0){
            // switch to next game state
            gsm.setState(GameStateManager.LEVEL_STATE);
        }else if(currentOption == 1){
            // Application exit
            System.exit(0);
        }
    }

}
