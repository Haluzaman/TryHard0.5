package level;

import graphics.Sprite;
import graphics.SpriteSheet;

/**
 * Created by bubof on 09.07.2017.
 */
public class Tile {

    public static final int VOID = 0;
    public static final int NORMAL = 1;
    public static final int BLOCKED = 2;
    public static final int SWIMMABLE = 3;

    public static final Tile GRASS_TILE = new Tile(Sprite.GRASS_TILE,Tile.NORMAL);
    public static final Tile GRASS_TILE_2 = new Tile(Sprite.GRASS_TILE_2,Tile.NORMAL);
    public static final Tile VOID_TILE = new Tile(Sprite.VOID_TILE,Tile.VOID);
    public static final Tile SWAMP_TILE = new Tile(Sprite.SWAMP_TILE,Tile.NORMAL);
    public static final Tile COAST_TILE = new Tile(Sprite.COAST_TILE,Tile.NORMAL);
    public static final Tile WATER_TILE = new Tile(Sprite.WATER_TILE,Tile.SWIMMABLE);
    public static final Tile TREE_TILE = new Tile(Sprite.TREE_TILE,Tile.BLOCKED);
    public static final Tile CUT_TREE_TILE = new Tile(Sprite.CUT_TREE_TILE,Tile.BLOCKED);

    private int type;
    private Sprite image;
    private int x,y;

    public Tile(Sprite image,int x, int y, int type){
        this.image = image;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Tile(Tile tile,int x,int y){
        this.image = tile.getSprite();
        this.type = tile.getType();
        this.x = x;
        this.y = y;
    }

    public Tile(Sprite image,int type){
        this.image = image;
        this.type = type;
    }

    public  void update(){}

    public int[] getImage(){
        return image.getPixels();
    }

    public int getType(){
        return this.type;
    }

    public Sprite getSprite(){
        return this.image;
    }

    public void setX(int x){ this.x = x;}
    public void setY(int y){ this.y= y;}
    public int getX(){ return this.x;}
    public int getY(){ return this.y;}
}
