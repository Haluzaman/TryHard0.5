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

    public Player(Screen screen, Map map,int x, int y, int width, int height, int cX, int cY, int cWidth, int cHeight){
        super(screen,map,x,y,width,height,cX,cY,cWidth-1,cHeight-1);
        setAnimation(new Animation(screen,SpriteSheet.PLAYER_SPRITESHEET));
        animation.setCurrentAnimation(2);
        animation.setDelay(100);
        initDirections();
    }

    /**
     * Overwrites stuff
     * */
    public void initDirections(){
        IDLE = 2;
        LEFT = 1;
        RIGHT = 0;
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

         //moving update
//        calculateNextPosition();
//        if(!collisionOccurs()) {
//            setPosition(nextPosition);
//        }
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
