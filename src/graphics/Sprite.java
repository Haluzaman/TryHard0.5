package graphics;

import main.Game;

import java.awt.*;

/**
 * Created by bubof on 05.07.2017.
 *
 * Sprite
 *
 */
public class Sprite {

    /**dimensions of the sprite**/
    private int width;
    private int height;
    private int[] pixels; /**array of pixels of the current sprite image**/

    public static final Sprite GRASS_TILE = new Sprite(SpriteSheet.TILE_MAP_SPRITESHEET2,0,0);
    public static final Sprite GRASS_TILE_2 = new Sprite(SpriteSheet.TILE_MAP_SPRITESHEET2,0,1);
    public static final Sprite SWAMP_TILE = new Sprite(SpriteSheet.TILE_MAP_SPRITESHEET2,0,0);
    public static final Sprite COAST_TILE = new Sprite(SpriteSheet.TILE_MAP_SPRITESHEET2,0,1);
    public static final Sprite WATER_TILE = new Sprite(SpriteSheet.TILE_MAP_SPRITESHEET2,1,0);
    public static final Sprite TREE_TILE = new Sprite(SpriteSheet.TILE_MAP_SPRITESHEET2,1,1);
    public static final Sprite CUT_TREE_TILE = new Sprite(SpriteSheet.TILE_MAP_SPRITESHEET2,1,2);
    public static final Sprite VOID_TILE = new Sprite(Color.BLACK);

    public Sprite(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width*height];
    }

    public Sprite(SpriteSheet spriteSheet,int row, int column){
        pixels = spriteSheet.getSprite(row,column).getPixels();
        this.width = spriteSheet.getSprite(row,column).getWidth();
        this.height = spriteSheet.getSprite(row,column).getHeight();
    }

    public Sprite(Color c){
       this.pixels = new int[Game.TILESIZE*Game.TILESIZE];
       for(int i = 0; i < pixels.length;i++)
           pixels[i] = c.getRGB();
    }

    public void draw(Screen screen){

    }

    public void update(){

    }

    public void setPixels(int[][] pixels){

        for(int i = 0; i < pixels.length;i++)
            for(int x = 0; x < pixels[i].length;x++)
                this.pixels[x+i*width] = pixels[i][x];
    }

    /**
     * Draws sprite on screen
     * @param screen screen where we draw
     * @param x xOffset X coordinate where on screen we will draw
     * @param y xOffset Y coordinate where on screen we will draw
     * **/
    public void drawSprite(Screen screen, int x, int y){
        int[] scPixels = screen.getPixels();
        for(int i = 0; i < this.width;i++){
            for(int j = 0; j < this.height;j++){
                int currenPos = j+i*this.width;
                if(pixels[currenPos] == Font.ALPHA_COLOR.getRGB())
                    continue;
                scPixels[(x+j)+(i+y)*screen.getWidth()] = this.pixels[currenPos];
            }
        }
        screen.setPixels(scPixels);
    }

    /**
     * Draws sprite of the letter on screen in specified color
     * @param screen screen where we draw
     * @param x xOffset X coordinate where on screen we will draw
     * @param y xOffset Y coordinate where on screen we will draw
     * @param c Color of the letter
     * **/
    public void drawSprite(Screen screen, int x, int y, Color c){
        for(int i = 0; i < this.width;i++){
            for(int j = 0; j < this.height;j++){
                int currentPos = j+i*this.width;
                if(pixels[currentPos] == Color.black.getRGB())
                    screen.setPixel((x+j),(i+y),c.getRGB());
                else
                    screen.setPixel((x+j),(i+y),this.pixels[currentPos]);
            }
        }
    }

    /**
     * Sets pixels
     * **/
    public void setPixels(int[] pixels){
        //this.pixels = new int[pixels.length];
//        this.pixels = pixels;
        for(int i = 0;i < pixels.length;i++)
            this.pixels[i] = pixels[i];

    }

    public int[] getPixels(){
        return this.pixels;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}
