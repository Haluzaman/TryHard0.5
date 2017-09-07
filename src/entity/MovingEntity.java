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
    public int JUMPING  = 5;
    public int FALLING  = 6;

    protected int currentDirection = -1;
    protected int currentFrame = 0;

    /** corners */
    protected int rightUpX;
    protected int rightUpY;
    protected int leftUpX;
    protected int rightDownY;

    protected boolean isMoving = false;
    protected boolean isOnGround;
    protected boolean jumping;
    protected boolean falling;

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
        isOnGround = false;
        jumping = false;
    }

    public MovingEntity(Screen screen,Map map,Rectangle render, Rectangle collision){
        super(screen,map,render,collision);
    }

    public void render(){

    }

    public void update(){

    }

    protected void move(double delta){
//            delta /= 2;
            this.currentVelX = MovingEntity.approach(velX,currentVelX,delta);

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
                    this.velY = 0;
                    setJumping(false);
                    setIsOnGround(true);
                }else if(topLeft || topRight){
                    System.out.println("top ");
                    this.velY = 0;
                }
                System.out.println("falling: " + falling + " jumping: " + jumping);
                this.rX += this.currentVelX * delta;
                this.rY += this.velY *(delta/2);
                this.cY = rY;
                this.cX = rX+1;

            this.currentVelX += LevelState.FRICTION*delta;
            this.velY += LevelState.GRAVITY*delta;

            if(this.rY < 0)
                this.rY = 0;
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

    /** checks collision and if none occured we make step
     * */

    public void step(int x,int y){
//        calculateCorners(x,y);
//        if(!collisionOccurs(x,y)) {
//            this.rX = (this.rX + x);
//            this.rY = (this.rY + y);
//        }
    }

    //TODO: check wether all is needed
//    protected void calculateCorners(double x,double y){
//        leftUpX = (int)(this.rX + x);
//        rightUpX = (int)(this.rX + x + cWidth);
//        rightUpY = (int)(this.rY + y);
//        rightDownY = (int)(this.rY + y + cHeight);
//     }
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

        /** loop checks if collision occurs in tiles around our object*/
        boolean collisionOccured = false;
//        for(int i = topTileIndex;i <= downTileIndex;i++){
//            for(int j = leftTileIndex; j <= rightTileIndex;j++){
//                int type = map.getTile(j,i).getType();
//                if(type == Tile.VOID || type == Tile.BLOCKED){
//                    collisionOccured = true;
//                }
//            }
//        }
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

    public boolean isJumping(){
        return this.jumping;
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

    public boolean getIsOnGround(){return this.isOnGround;}

    public boolean getFalling(){return this.falling;}

    public void setFacingRight(boolean b){
        this.facingRight = b;
    }

    public void setFacingLeft(boolean b){
        this.facingLeft = b;
    }

    public void setJumping(boolean b){
        this.jumping = b;
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

    public void setIsOnGround(boolean b){this.isOnGround = b;}

    public void setFalling(boolean b){this.falling = b;}


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

}
