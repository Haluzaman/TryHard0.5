package gameState;

import entity.Player;
import graphics.Graphics;
import graphics.Screen;
import level.Map;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import graphics.*;

/**
 * Created by bubof on 09.07.2017.
 */
public class LevelState extends GameState {

    public final static double GRAVITY = 0.5;
    public final static double FRICTION = 0.7;

    private Map map;
    private Player player;

    public LevelState(GameStateManager gsm, Screen screen){
        super(gsm,screen);
        map = new Map(Map.LEVEL_PATH); //((screen.getRenderHeight()>>1)<<Game.TILE_BYTE_SIZE)+2*Game.TILESIZE --->> Y
        player = new Player(screen,map,5<<Game.TILE_BYTE_SIZE, 19<<Game.TILE_BYTE_SIZE, 16, 16, (5<<Game.TILE_BYTE_SIZE)+1, 19<<Game.TILE_BYTE_SIZE, 15, 16);
        screen.setBounds(map.getLevelWidth()<<Game.TILE_BYTE_SIZE,map.getLevelHeight()<<Game.TILE_BYTE_SIZE);
        screen.center(player.getRenderX(),player.getRenderY());
    }

    public void draw(){
        screen.renderMap(this.map);
        player.render();
//        screenX = (int)(rX - screen.getX());
//        screenY = (int)(rY - screen.getY());
          Graphics.drawRectangle(screen,player.getScreenX(),player.getScreenY(),player.getCollisionWidth(),player.getCollisionHeight(),Color.YELLOW);
//        screen.drawLine(50,50,70,25, Color.black);
    }

    public void update(double delta){
        screen.updateMap(this.map);
        player.update(delta);
        screen.center(player.getRenderX(),player.getRenderY());
    }

    public void keyPressed(int k){
        if(k == KeyEvent.VK_RIGHT){
            player.setDirection(player.RIGHT);
//            player.step(2,0);
            player.setVelX(1);
        }
        if(k == KeyEvent.VK_LEFT){
            player.setDirection(player.LEFT);
            player.setVelX(-1);
//            player.step(-2,0);
        }
        if(k == KeyEvent.VK_DOWN){
//            player.step(0,2);
        }
        if(k == KeyEvent.VK_UP){
//            player.step(0,-2);
        }if(k == KeyEvent.VK_SPACE){
            player.setJumping(true);
            player.setVelY(-12);
        }
    }

    public void keyReleased(int k){
        if(k == KeyEvent.VK_UP){

        }
        if(k == KeyEvent.VK_DOWN){

        }
        if(k == KeyEvent.VK_RIGHT){
            player.setVelX(0);
            player.setDirection(player.IDLE);
        }
        if(k == KeyEvent.VK_LEFT){
            player.setVelX(0);
            player.setDirection(player.IDLE);
        }
    }

    public void keyTyped(int k){}

    /**
     * Checks wether camera can move
     * */
    private void checkScreenBounds(int newX,int newY){
        int currentRightPositionX = screen.getX() + screen.getWidth() + newX;
        int currentRightPositionY = screen.getY() + screen.getHeight() + newY;
        int playerOnScreenX = player.getRenderX() - screen.getX();
        int playerOnScreenY = player.getRenderY() - screen.getY();
        boolean isCentered = false;
        int direction = player.getCurrentDirection();

        /**wether player is centered on map*/
//        if(direction == player.UP || direction == player.DOWN){
//            if(playerOnScreenY == screen.getScreenMiddleY() + newY)
//                isCentered = true;
//        }
//        else
        if(direction == player.RIGHT || direction == player.LEFT){
            if(playerOnScreenX == screen.getScreenMiddleX() + newX)
                isCentered = true;
        }

        /**wether map is not out of bounds on map -> we dont want to draw Black Screen*/
        if(((currentRightPositionX + newX) <= (map.getLevelWidth()<<Game.TILE_BYTE_SIZE)) && (screen.getX() + newX) >= 0 && isCentered)
            screen.setOffsetX(newX);

        if(((currentRightPositionY + newY) <= (map.getLevelHeight()<<Game.TILE_BYTE_SIZE)) && screen.getY() + newY >= 0 && isCentered)
            screen.setOffsetY(newY);

    }



}
