package graphics;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bubof on 05.07.2017.
 * SpriteSheet class for loading image of spriteSheet and spliting it into sprites
 */
public class SpriteSheet {

//    public static final SpriteSheet TILE_MAP_SPRITESHEET = new SpriteSheet("/tileSets/tileSet.png",16,16,new int[]{2});
    public static final SpriteSheet TILE_MAP_SPRITESHEET2 = new SpriteSheet("/tileSets/SpriteSheet.png",16,16,new int[]{3,3});
    public static final SpriteSheet PLAYER_SPRITESHEET = new SpriteSheet("/tileSets/gandalfman.png",16,16,new int[]{3,3,2,3,2,3});
    public static final SpriteSheet PLAYER1_SPRITESHEET = new SpriteSheet("/tileSets/player1.png",16,16,new int[]{2,2,2,2,2,2,2,2,2,2});

    private int tileWidth;
    private int tileHeight;

    private String path;
    private BufferedImage spriteSheet;
    private int[] pixels;
    private Sprite[][] sprites;
    private int[] numSpritesInRows;
    private int numSpritesInRow;

    /**
     * @param path path to the spriteSheet
     * @param width Width of the sprite
     * @param height Height pf the sprite
     * @param numSpritesInRows number of sprites in every row
     * */
    public SpriteSheet(String path, int width, int height, int[] numSpritesInRows){
        this.path = path;
        this.tileWidth = width;
        this.tileHeight = height;
        this.numSpritesInRows = numSpritesInRows;
        loadSpriteSheet();
        split();
    }


    /**
     * @param path path to the spriteSheet
     * @param width Width of the sprite
     * @param height Height pf the sprite
     * @param numSpritesInRows number of sprites in every row -> use when every row has same num of sprites
     * */
    public SpriteSheet(String path, int width, int height, int numSpritesInRows){
        this.path = path;
        this.tileWidth = width;
        this.tileHeight = height;
        this.numSpritesInRows = null;
        this.numSpritesInRow = numSpritesInRows;
        loadSpriteSheet();
        split();
    }
    /**
     * Loads spriteSheet
     * */
    public void loadSpriteSheet(){
        try{
            spriteSheet = ImageIO.read(SpriteSheet.class.getResource(path));
            int width = spriteSheet.getWidth();
            int height = spriteSheet.getHeight();
            pixels = new int[height*width];
            pixels = spriteSheet.getRGB(0,0,width,height,pixels,0,width);


            if(numSpritesInRows == null){
                int numRows = spriteSheet.getHeight()/tileHeight;
                int numCols = spriteSheet.getWidth()/tileWidth;
                sprites = new Sprite[numRows][numCols];
            }else {
                int numRows = spriteSheet.getHeight() / tileWidth;
                sprites = new Sprite[numRows][];
                for (int i = 0; i < numRows; i++) {
                    sprites[i] = new Sprite[numSpritesInRows[i]];
                }
            }
            for(int i = 0; i < sprites.length;i++){
                for(int j = 0; j < sprites[i].length;j++){
                    sprites[i][j] = new Sprite(tileWidth,tileHeight);
                }
            }
        }
        catch(IOException e) {
            System.out.println("Dojebkalo sa nahravanie spriteSheetu");
            e.printStackTrace();
        }
    }

    /**
     * Splits SpriteSheet into Sprites
     * */
    public void split(){
        if(numSpritesInRows == null){

            int numRows = spriteSheet.getHeight()/ tileHeight;
            int numCols = spriteSheet.getWidth() / tileWidth;

            for(int i = 0; i < numRows;i++){
                for(int j = 0; j < numCols;j++){
                    int[] pix = new int[tileWidth*tileHeight];
                    for(int y = 0; y < tileHeight;y++){
                        for(int x = 0; x < tileWidth;x++){
                            pix[x + y * tileHeight] = pixels[x + (j * tileHeight) + y + (i * tileWidth)];
                        }
                    }
                    sprites[i][j].setPixels(pix);
                }
            }
        }else {
            int numRows = spriteSheet.getHeight() /tileHeight;
            for (int currentRow = 0; currentRow < numRows; currentRow++) {
                int numColumns = numSpritesInRows[currentRow];
                for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
                    int[] pix = new int[tileWidth * tileHeight];
                    for (int y = 0; y < tileHeight; y++) {
                        for (int x = 0; x < tileWidth; x++) {
                            pix[x + y * tileWidth] = pixels[currentRow*tileHeight*spriteSheet.getWidth()+y*spriteSheet.getWidth()+currentColumn*tileWidth+x];
                        }
                    }
                    sprites[currentRow][currentColumn].setPixels(pix);
                }
            }

        }
    }

    public Sprite getSprite(int indexRow, int indexColumn){
        return this.sprites[indexRow][indexColumn];
    }
    public Sprite[][] getSprites(){ return this.sprites;}


    public Sprite[] getSpritesInRow(){
        Sprite[] spritesNew;
        int pocet=0;
        for(int i = 0; i < sprites.length;i++){
            pocet+=sprites[i].length;
        }
        spritesNew = new Sprite[pocet];
        int k = 0;
        for(int i = 0; i < sprites.length;i++){
            for(int j = 0; j < sprites[i].length;j++){
                spritesNew[k++] = sprites[i][j];
            }
        }
        return spritesNew;
    }
}
