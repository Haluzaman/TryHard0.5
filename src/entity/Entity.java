package entity;

import graphics.Animation;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;
import level.Map;
import main.Game;

import java.awt.*;

public abstract class Entity {

    /** Map coordinates*/
    protected int screenX;
    protected int screenY;

    /** Render coordinates in window**/
    protected double rX, rY;
    protected int rWidth,rHeight;

    /** Collision coordinates**/
    protected double cX, cY;
    protected int cWidth,cHeight;

    protected Screen screen;

    // TODO: create SpriteSheet and stuff maybe better to be constants
    protected SpriteSheet spriteSheet;
    protected Sprite[][] sprites;
    protected Animation animation;
    protected Map map;
    public Entity(Screen screen,Map map,int x, int y, int width, int height, int cX, int cY, int cWidth, int cHeight){
        this.screen = screen;
        this.map = map;
        this.rX = x;
        this.rY = y;
        this.rWidth = width;
        this.rHeight = height;
        this.cX = cX;
        this.cY = cY;
        this.cWidth = cWidth;
        this.cHeight = cHeight;
    }

    public Entity(Screen screen,Map map,Rectangle render,Rectangle collision){
        this.screen = screen;
        this.map = map;
        this.rX = render.getX();
        this.rY = render.getY();
        this.rWidth = (int)render.getWidth();
        this.rHeight =(int) render.getHeight();
        this.rX = collision.getX();
        this.cY = collision.getY();
        this.cWidth = (int)collision.getWidth();
        this.cHeight = (int)collision.getHeight();
    }

    /**
     * This function checks wether collision occured
     * @param another Rectangle of another Entity
     * */
    public boolean intersects(Rectangle another){
        Rectangle entityCollisionRectangle = new Rectangle((int)this.cX,(int)this.cY,(int)this.cWidth,(int)this.cHeight);
        return entityCollisionRectangle.intersects(another);
    }

    /**
     * Function returns rectangle
     * @param isCollisionRectangle wether returns collision rect or render rect
     * */
    public Rectangle getRectangle(boolean isCollisionRectangle){
        if(isCollisionRectangle)
            return new Rectangle((int)this.cX,(int)this.cY,(int)this.cWidth,(int)this.cHeight);
        else
            return new Rectangle((int)this.rX,(int)this.rX,(int)this.rWidth,(int)this.rHeight);
    }

    /**
     * Function which renders Entity
     * */
    public abstract void render();
    /**
     * Function which updates Entity
     * */
    public abstract void update();

    /** Setts spritesheet properties and creates sprite array*/
    public void setSpriteSheetProperties(String path, int width,int height, int[] numSpritesInRow){
        this.spriteSheet = new SpriteSheet(path,width,height,numSpritesInRow);
        this.sprites = spriteSheet.getSprites();
    }

    protected void setSpriteSheet(SpriteSheet spriteSheet){
        this.spriteSheet = spriteSheet;
        this.sprites = spriteSheet.getSprites();
    }

    public int getRenderX(){
        return (int)rX;
    }

    public int getRenderY(){
        return (int)rY;
    }

    public int getCurrentColumn(){
        return ((int)rX >> Game.TILE_BYTE_SIZE);
    }

    public int getCurrentRow(){
        return ((int)rY >> Game.TILE_BYTE_SIZE);
    }

    public boolean isOnScreen(){
        if(((rY + rHeight) >= screen.getY()) && ((rX + rWidth) >= screen.getX()))
            return true;
        return false;
    }

    public void setAnimation(Animation animation){
        this.animation = animation;
    }
    public int getCollisionWidth(){return this.cWidth;}
    public int getCollisionHeight(){return this.cHeight;}
    public int getScreenX(){ return this.screenX;}
    public int getScreenY(){ return this.screenY;}
}
