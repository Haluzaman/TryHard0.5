package entity;

import gameState.LevelState;
import graphics.Screen;
import graphics.SpriteSheet;
import level.Map;
import level.Tile;
import main.Game;

import java.awt.*;

public class MovingEntity extends Entity{

    /** Direction of moving **/
    public int IDLE  = 4;
    public int RIGHT = 0;
    public int LEFT  = 1;
    public int UP;
    public int DOWN;
    public int JUMPING  = 5;
    public int FALLING  = 6;

    protected boolean isSwimming = false;


    protected int currentDirection = -1;
    protected int currentFrame = 0;

    /** corners ****/
    protected int rightUpX;
    protected int rightUpY;
    protected int leftUpX;
    protected int rightDownY;

    protected boolean isMoving = false;

    protected boolean facingRight = false;
    protected boolean facingLeft = false;
    protected boolean idle = true;


    protected double velX = 0;
    protected double velY = 0;

    protected double currentVelX = 0;
    protected double currentVelY = 0;

    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    public MovingEntity(Screen screen,Map map,int x, int y, int width, int height, int cX, int cY, int cWidth, int cHeight){
        super(screen,map,x,y,width,height,cX,cY,cWidth,cHeight);
    }

    public MovingEntity(Screen screen,Map map,Rectangle render, Rectangle collision){
        super(screen,map,render,collision);
    }

    public void render(){

    }

    public void update(){

    }

    protected void move(double delta){
            this.currentVelX = MovingEntity.approach(velX,currentVelX,delta);
            this.currentVelY = MovingEntity.approach(velY,currentVelY,delta);
                collisionOccurs(cX + this.currentVelX * delta,cY);
                if(topLeft || bottomLeft){
                    System.out.println("left ");
                    this.velX = 0;
                    this.currentVelX = 0;
                }else if(topRight || bottomRight){
                    System.out.println("right ");
                    this.velX = 0;
                    this.currentVelX = 0;
                }
                collisionOccurs(cX,cY + this.velY * delta);
                if(bottomLeft || bottomRight){
                    System.out.println("bottom ");
                    this.currentVelY = 0;
                }else if(topLeft || topRight){
                    System.out.println("top ");
                    this.currentVelY = 0;
                }

                this.rX += this.currentVelX * delta;
                this.rY += this.currentVelY *delta;
                this.cY = rY;
                this.cX = rX+1;

            this.currentVelX += LevelState.FRICTION*delta;
            this.currentVelY += LevelState.FRICTION*delta;

            if(this.map.getTile(((int)this.cX + this.cWidth/2)>>Game.TILE_BYTE_SIZE,((int)this.cY + this.cHeight/2)>>Game.TILE_BYTE_SIZE).getType() == Tile.SWIMMABLE)
                this.isSwimming = true;
            else
                this.isSwimming = false;

//            if(this.rY < 0)
//                this.rY = 0;
    }

    public boolean isMoving(){
        return this.isMoving;
    }

    public void setDirection(int direction){
        if(currentDirection == direction) {
            return;
        }
        this.currentDirection = direction;
        animation.setCurrentAnimation(currentDirection);
    }

    //TODO: check wether all is needed
        protected void calculateCorners(double x,double y){
            leftUpX = (int)(x);
            rightUpX = (int)(x + cWidth);
            rightUpY = (int)(y);
            rightDownY = (int)(y + cHeight);
        }
    /**
     * Method checks wether collision occurs in our next step
     * where we want to go
     * @params map ->map of our world
     * TODO: amybe it should be reference in this object?
     *
     * */

    protected void collisionOccurs(double nextX,double nextY){
        calculateCorners(nextX,nextY);
        /** Corners of the object*/
        int entityRight = rightUpX;
        int entityLeft =  leftUpX;
        int entityTop = rightUpY;
        int entityDown = rightDownY;

        /** Tiles where we want to go*/
        int leftTileIndex = entityLeft >>Game.TILE_BYTE_SIZE;
        int rightTileIndex = entityRight >> Game.TILE_BYTE_SIZE;
        int downTileIndex = entityDown >> Game.TILE_BYTE_SIZE;
        int topTileIndex = entityTop >> Game.TILE_BYTE_SIZE;

        topLeft     = map.getTile(leftTileIndex,topTileIndex).getType()   == Tile.BLOCKED || map.getTile(leftTileIndex,topTileIndex).getType()   == Tile.VOID;
        topRight    = map.getTile(rightTileIndex,topTileIndex).getType()  == Tile.BLOCKED || map.getTile(rightTileIndex,topTileIndex).getType()  == Tile.BLOCKED;
        bottomLeft  = map.getTile(leftTileIndex,downTileIndex).getType()  == Tile.BLOCKED || map.getTile(leftTileIndex,downTileIndex).getType()  == Tile.BLOCKED;
        bottomRight = map.getTile(rightTileIndex,downTileIndex).getType() == Tile.BLOCKED || map.getTile(rightTileIndex,downTileIndex).getType() == Tile.BLOCKED;
    }

    public int getCurrentDirection(){
        return this.currentDirection;
    }

    public boolean isFacingRight(){
        return this.facingRight;
    }

    public boolean isFacingLeft(){
        return this.facingLeft;
    }


    public boolean isIdle(){
        return this.idle;
    }

    public double getVelX(){
        return this.velX;
    }

    public double getVelY(){
        return this.velY;
    }

    public double getCurrentVelX(){
        return this.currentVelX;
    }

    public double getCurrentVelY(){
        return this.currentVelY;
    }

    public void setFacingRight(boolean b){
        this.facingRight = b;
    }

    public void setFacingLeft(boolean b){
        this.facingLeft = b;
    }

    public void setIdle(boolean b){
        this.idle = b;
    }

    public void setVelX(double velX){
        this.velX = velX;
    }

    public void setVelY(double velY){
        this.velY = velY;
    }
    public void setCurrentVelX(double velx){
        this.currentVelX = velx;
    }

    public void setCurrentVelY(double vely){
        this.currentVelY = vely;
    }

    public static double approach(double goal, double current, double delta){
        double difference = goal - current;

        if(difference > delta){
            return current + delta;
        }
        if(difference < -delta){
            return current - delta;
        }

        return goal;
    }

    public boolean isSwimming(){ return this.isSwimming; }

}
