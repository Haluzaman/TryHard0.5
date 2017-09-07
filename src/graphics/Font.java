package graphics;

import java.awt.*;

/**
 * Created by bubof on 05.07.2017.
 *
 * This class creates font and allows draw strings on screen with this font
 *
 */

public class Font {

    /**
     * available fonts
     * */
    public static  Font ARIAL_FONT = new Font("arialFont", "/fonts/arialFont.png", 16, 16, new int[]{13, 13, 13, 13, 13, 13});


    /************/

    /**This color is ALPHA CHANNEL**/
    public static final Color ALPHA_COLOR = new Color(255,0,255);
    private SpriteSheet font; /**SpriteSheet of the font**/
    private Sprite[] chars; /**Letters of the Font**/
    private String charIndex; /**Array for better looking for propriate letter**/
    private Color color; /**color of the font**/
    private String name; /**name of the font**/
    private int width,height; /**width and height of the sprite in SpriteSheet**/
    private boolean resize; /**Whether the font will be resized**/

    /**
     * Creates object of Font - for drawing strings on screen
     *
     * @param name - name of the sprite
     * @param path - relative path to the spritesheet of font
     * @param width - width of letter in font
     * @param height - height of letter in font
     * @param sizesInRow - array of number of sprites in row(letters)
     * @param resize - other size of letter then in spriteSheet
     * */
    public Font(String name,String path, int width,int height, int[] sizesInRow,boolean resize){
        this.resize = resize;
        this.name = name;
        this.width = width;
        this.height = height;
        font = new SpriteSheet(path,width,height,sizesInRow);
        chars = font.getSpritesInRow();
        if(name.equalsIgnoreCase("arialFont")){
            charIndex = "ABCDEFGHIJKLM"+ "NOPQRSTUVWXYZ" + "abcdefghijklm" + "nopqrstuvwxyz" + "0123456789.‚‘'“”;:!@$%()-+";
        }
        //TODO: make resize of the image
    }

    /**
     * Creates object of Font - for drawing strings on screen
     *
     * @param name - name of the sprite
     * @param path - relative path to the spritesheet of font
     * @param width - width of letter in font
     * @param height - height of letter in font
     * @param sizesInRow - array of number of sprites in row(letters)
     * */
    public Font(String name,String path, int width,int height, int[] sizesInRow){
        this.name = name;
        this.resize = false;
        this.width = width;
        this.height = height;
        font = new SpriteSheet(path,width,height,sizesInRow);
        chars = font.getSpritesInRow();
        if(name.equalsIgnoreCase("arialFont")){
            charIndex = "ABCDEFGHIJKLM"+ "NOPQRSTUVWXYZ" + "abcdefghijklm" + "nopqrstuvwxyz" + "0123456789.‚‘'“”;:!@$%()-+";
        }
    }


    /**
     * Creates object of Font - for drawing strings on screen
     *
     * @param name - name of the sprite
     * @param path - relative path to the spritesheet of font
     * @param width - width of letter in font
     * @param height - height of letter in font
     * @param sizesInRow - number of sprites in row(letters)
     * */
    public Font(String name,String path, int width,int height, int sizesInRow){
        font = new SpriteSheet(path,width,height,sizesInRow);
        this.resize = false;
        chars = font.getSpritesInRow();
        if(name.equalsIgnoreCase("arialFont")){
            charIndex = "ABCDEFGHIJKLM"+ "NOPQRSTUVWXYZ" + "abcdefghijklam" + "npoqrstuvwxyz" + "0123456789.‚‘'“”;:!@$%()-+";
        }
    }

    /**
     * sets color
     * */
    public void setColor(Color c){
        color = c;
    }

    /**
     * Draws String on Screen
     * @param text String you want to draw
     * @param x X coordinate where you want to draw
     * @param y Y coordinate where you want to draw
     * @param offset room between each character in string
     * */
    public void drawString(Screen screen,String text,int x,int y,int offset){
        int yoffset = 0;
        int firstX = x;
        int firstY = y;
        for(int k = 0; k < text.length();k++) {
            if(text.charAt(k) == '\n') {
                yoffset++;
                x=firstX;
            }
            if(text.charAt(k) == ' '){
                x+=this.width + offset;
                continue;
            }
            else {
                char currentChar = text.charAt(k);
                if(currentChar == 'j' || currentChar == 'q' || currentChar == 'g' || currentChar == 'y' || currentChar == 'p')
                    y+=3;
                else
                    y = firstY;
                chars[charIndex.indexOf(currentChar)].drawSprite(screen, x, y,this.color);
                x += this.width + offset;
            }
            y+=this.height*yoffset;
        }
    }

    /**
     * Handles overflows of text relative to component
     * */
    private void makeRelativeTo(Rectangle r){

    }

    public Font getInstance(){
        return this;
    }

    /**
     * returns font
     * */
    public static Font getFont(String name){
        if(name.equalsIgnoreCase("arialFont")) {
//            return new Font("arialFont", "/fonts/arialFont.png", 16, 16, new int[]{13, 13, 13, 13, 13, 13});
            return ARIAL_FONT;
        }
        else  return new Font("arialFont", "/fonts/arialFont.png", 16, 16, new int[]{13, 13, 13, 13, 13, 13});
    }

    public SpriteSheet getSpriteSheet(){
        return this.getSpriteSheet();
    }

    public Sprite getCharacters(int i){
        return this.chars[i];
    }

    public Sprite[] getCharacters(){
        return this.chars;
    }

    public String getCharIndex(){
        return this.charIndex;
    }

    public Color getColor(){
        return this.color;
    }
}
