package entity;

import gameState.LevelState;
import graphics.Animation;
import graphics.Screen;
import graphics.SpriteSheet;
import level.Map;

import java.awt.*;

/**
 * Created by bubof on 05.08.2017.
 */
public class Player extends MovingEntity  {

    public static int SWIMMING_UP = 5;
    public static int SWIMMING_LEFT = 7;
    public static int SWIMMING_RIGHT = 8;
    public static int SWIMMING_DOWN = 6;

    public Player(Screen screen, Map map,int x, int y, int width, int height, int cX, int cY, int cWidth, int cHeight){
        super(screen,map,x,y,width,height,cX,cY,cWidth-1,cHeight-1);
        setAnimation(new Animation(screen,SpriteSheet.PLAYER1_SPRITESHEET));
        animation.setCurrentAnimation(0);
        animation.setDelay(400);
        initDirections();
    }

    /**
     * Overwrites stuff
     * */
    public void initDirections(){
//        IDLE = 2;
//        LEFT = 1;
//        RIGHT = 0;
//        UP = 5;
//        DOWN = 3;
        IDLE = 0;
        RIGHT = 1;
        LEFT = 3;
        DOWN = 4;
    }

    public void setDirection(int direction){
//        if(currentDirection == direction) {
//            return;
//        }
        this.currentDirection = direction;
        if(direction == IDLE && isSwimming || direction == DOWN && isSwimming) {
            animation.setCurrentAnimation(SWIMMING_DOWN);
        }
        else if(direction == UP && isSwimming){
            animation.setCurrentAnimation(SWIMMING_UP);
        }
        else if(direction == LEFT && isSwimming){
            animation.setCurrentAnimation(SWIMMING_LEFT);
        }
        else if(direction == RIGHT && isSwimming){
            animation.setCurrentAnimation(SWIMMING_RIGHT);
        }
        else{
            animation.setCurrentAnimation(currentDirection);
        }
    }

    public Player(Screen screen,Map map, Rectangle render, Rectangle collision){
        super(screen,map,render,collision);
    }

    public void render(){
        //checkRenderSpriteBounds();
        /** coordinates on the screen */
        screenX = (int)(rX - screen.getX());
        screenY = (int)(rY - screen.getY());
        animation.render(screenX,screenY);
    }

    public void update(double delta){
         move(delta);
         animation.update();
    }


    public void setRenderX(int x){
        this.rX = x;
    }

    public void setRenderY(int y){
        this.rY = y;
    }

    public boolean isMoving(){
        return this.isMoving;
    }

}
