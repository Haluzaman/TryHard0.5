package graphics;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by bubof on 06.07.2017.
 */
public class Graphics {


    /**
     * Loads Image
     * @param path path to file
     * */
    public static BufferedImage loadImage(String path){
        BufferedImage spriteSheet;
        try{
            spriteSheet = ImageIO.read(Graphics.class.getResource(path));
            return spriteSheet;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * loads spriteSheet image and returns SpriteSheet array
     * @param image -> image with spriteSheet -> use loadImage(String path)
     * @param tileWidth -> width of one tile in spritesheet
     * @param tileHeight ->height of one tile in spritesheet
     * @param numSpritesInRows -> if size of sprites in sheet are not qual in every row(leave numTilesInRow null)
     * @param numSpritesInRow -> if size of sprites in sheet are equal in every row(leave numTilesInRows null)
     * */
    @Deprecated
    public static Sprite[][] createSpriteSheet(BufferedImage image,int tileWidth,int tileHeight,int[] numSpritesInRows,int numSpritesInRow){

        /**
         * Load part
         * */
        Sprite[][] sprites;
        int[] pixels;
        BufferedImage spriteSheet;
            spriteSheet = image;
            int width = spriteSheet.getWidth();
            int height = spriteSheet.getHeight();
            pixels = new int[spriteSheet.getHeight()*spriteSheet.getWidth()];
            spriteSheet.getRGB(0,0,width,height,pixels,0,width);

            if(numSpritesInRows == null){
                int numRows = spriteSheet.getHeight()/tileHeight;
                int numCols = spriteSheet.getWidth()/tileWidth;
                sprites = new Sprite[numRows][numCols];
            }else {
                int numRows = spriteSheet.getHeight() >> Game.TILE_BYTE_SIZE;
                sprites = new Sprite[numRows][];
                for (int i = 0; i < spriteSheet.getHeight() >> Game.TILE_BYTE_SIZE; i++) {
                    sprites = new Sprite[i][numSpritesInRows[i]];
                }
            }

        /**
         * Split part
         * */
        if(numSpritesInRows == null){

            int numRows = spriteSheet.getHeight()>> tileHeight;
            int numCols = spriteSheet.getWidth() >> tileWidth;

            for(int i = 0; i < numRows;i++){
                for(int j = 0; j < numCols;j++){
                    int[] pix = new int[tileWidth*tileHeight];
                    for(int y = 0; y < tileHeight;y++){
                        for(int x = 0; x < tileWidth;x++){
                            pix[x + y * tileHeight] = pixels[x + (j * Game.TILESIZE) + y * Game.TILESIZE];
                        }
                    }
                    sprites[i][j].setPixels(pix);
                }
            }
        }else {
            int numRows = spriteSheet.getHeight() >> Game.TILESIZE;
            for (int currentRow = 0; currentRow < numRows; currentRow++) {
                int numColumns = numSpritesInRows[currentRow];
                for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
                    int[] pix = new int[Game.TILESIZE * Game.TILESIZE];
                    for (int y = 0; y < Game.TILESIZE; y++) {
                        for (int x = 0; x < Game.TILESIZE; x++) {
                            pix[x + y * Game.TILESIZE] = pixels[x + (currentColumn * Game.TILESIZE) + y * Game.TILESIZE];
                        }
                    }
                    sprites[currentRow][currentColumn].setPixels(pix);
                }
            }

        }
        return sprites;
    }

    /**
     * Resize the Image
     * @param pixels -> pixel array of the image
     * @param w1 -> native width of image
     * @param h1 -> native height of image
     * @param w2 -> wanted width of a image
     * @param h2 -> wanted height of a image
     * @return pixel array of scaled image
     * */
    public static int[] scaleImage(int[] pixels,int w1,int h1,int w2,int h2){
        int[] p = new int[w2*h2];
        int x_ratio = (int) ((w1<<16)/w2) +1;
        int y_ratio = (int) ((h1<<16)/h2) +1;

        int x2,y2;
        for (int i=0;i<h2;i++) {
            for (int j=0;j<w2;j++) {
                x2 = ((j*x_ratio)>>16) ;
                y2 = ((i*y_ratio)>>16) ;
                p[(i*w2)+j] = pixels[(y2*w1)+x2] ;
            }
        }
        return p;
    }

    public static void drawFillRectangle(Screen screen,int x,int y,int width,int height,Color c){
        int[] p = screen.getPixels();
        for(int yy = 0; yy < height;yy++){
            for(int xx = 0;xx < width;xx++){
                p[(xx + x)+(yy+y)*screen.getWidth()] = c.getRGB();
            }
        }

        screen.setPixels(p);
    }

    public static void drawRectangle(Screen screen, int x, int y, int width, int height,Color c){
        if(x + width < 0 || y + height < 0)
            return;

        if(x > screen.getWidth() || y > screen.getHeight())
            return;

        if(x < 0)
            x = -1;
        if(y < 0)
            y = -1;

        if(x+width > screen.getWidth())
            width = screen.getWidth() - x;

        if(y+height > screen.getHeight())
            height = screen.getHeight() - y;

        for(int yy = 1;yy < height;yy++){
            screen.setPixel(x,y+yy,c.getRGB());
            screen.setPixel(x+width-1,y+yy,c.getRGB());
        }

        for(int xx = 0;xx < width;xx++){
            screen.setPixel(x+xx,y,c.getRGB());
            screen.setPixel(x+xx,y+height,c.getRGB());

        }
    }

}
