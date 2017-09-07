package graphics;


import level.Map;
import level.Tile;
import main.Game;

import java.awt.*;
import java.util.Random;

/**
 * Created by bubof on 05.07.2017.
 * this class represents screen, here we will draw our images
 * then this class will render it on screen
 */
public class Screen {

    private int x,y;
    private int width;
    private int height;
    private int[] pixels;
    private int Xoffset = 0;
    private int Yoffset = 0;
    private int renderWidth;
    private int renderHeight;
    private Dimension bounds;
    private Random r = new Random();

    private int middleX;
    private int middleY;
    /**
     * @param width
     * @param height
     * */
    public Screen(int width, int height){
        this.x = 5;
        this.y = 5;
        this.width = width;
        this.height = height;
        this.pixels = new int[width*height];
        this.renderWidth = (width >> Game.TILE_BYTE_SIZE) + 1;
        this.renderHeight = (height >> Game.TILE_BYTE_SIZE) + 1;
        this.middleX = this.width>>1;
        this.middleY = this.height>>1;
    }

    /**
     * sets our screen black
     * */
    public void clear(){
        for(int i = 0; i < this.pixels.length;i++){
            pixels[i] = 0;
        }
    }


    /**Now does nothing**/
    public void render(){
        for(int y = 0; y < height;y++){
            for(int x = 0; x < width;x++){
                pixels[x+y*width] = r.nextInt(0xffffff);
            }
        }
    }

    /**
     * Renders String on the screen
     * @param text Text to be rendered
     * @param font Font of the text
     * @param x X-coordinate where to render
     * @param y Y-coordinate where to render
     * @param Xoffset offset between letters of the string
     * */
    public void renderString(String text,Font  font,int x, int y, int Xoffset){
        Sprite[] chars = font.getCharacters();
        String charIndex = font.getCharIndex();
        int yoffset = 0;
        int firstX = x;
        int firstY = y;
        int width = chars[0].getWidth();
        int height = chars[0].getHeight();
        Color color = font.getColor();
        for(int k = 0; k < text.length();k++) {
            if(text.charAt(k) == '\n') {
                yoffset++;
                x=firstX;
            }
            if(text.charAt(k) == ' '){
                x+=width + Xoffset;
                continue;
            }
            else {
                char currentChar = text.charAt(k);
                if(currentChar == 'j' || currentChar == 'q' || currentChar == 'g' || currentChar == 'y' || currentChar == 'p')
                    y+=3;
                else
                    y = firstY;
                renderSprite(chars[charIndex.indexOf(currentChar)],x, y,color);
                x += width + Xoffset;
            }
            y+=height*yoffset;
        }
    }

    /**
     * Draws resized string
     * @param text text to be rendered
     * @param font font for the text
     * @param x X-coordinate for drawing
     * @param y Y-coordinate for drawing
     * @param Xoffset offset between the letters in string
     * @param width Width of the resized letter
     * @param height Height of the resized letter
     * */
    public void renderString(String text,Font font,int x,int y,int Xoffset, int width, int height){
        Sprite[] chars = font.getCharacters();
        String charIndex = font.getCharIndex();
        int yoffset = 0;
        int firstX = x;
        int firstY = y;
        int w = chars[0].getWidth();
        int h = chars[0].getHeight();
        Color color = font.getColor();
        for(int k = 0; k < text.length();k++) {
            if(text.charAt(k) == '\n') {
                yoffset++;
                x=firstX;
            }
            if(text.charAt(k) == ' '){
                x+=width + Xoffset;
                continue;
            }
            else {
                char currentChar = text.charAt(k);
                if(currentChar == 'j' || currentChar == 'q' || currentChar == 'g' || currentChar == 'y' || currentChar == 'p')
                    y+=3;
                else
                    y = firstY;
                int[] spr = Graphics.scaleImage(chars[charIndex.indexOf(currentChar)].getPixels(),w,h,width,height);
                renderSprite(spr,x, y,width,height,color);
                x += width + Xoffset;
            }
            y+=h*yoffset;
        }
    }


    /**
     * Renders Sprite on the screen
     * @param sprite Sprite to be rendered on the screen
     * @param x X-coordinate where to start rendering
     * @param y Y- coordinate where to start rendering
     * */
    public void renderSprite(Sprite sprite, int x, int y){
        int[] pix = sprite.getPixels();
        int width = sprite.getWidth();
        int height = sprite.getHeight();
        for(int i = 0; i < height;i++){
            int yy = i + y;
            for(int j = 0; j < width;j++){
                int currentPosition = j+i*width;
                int xx = j+x;
                if(xx >= this.width || xx < 0 || yy >= this.height || yy < 0)
                    continue;
                if(pix[currentPosition] == Font.ALPHA_COLOR.getRGB())
                    continue;
                this.pixels[xx+yy*this.width] = pix[currentPosition];
            }
        }
    }


    public void renderTile(Tile sprite, int x, int y){
        x -= this.x;
        y -= this.y;
        int[] pix = sprite.getSprite().getPixels();
        int width = sprite.getSprite().getWidth();
        int height = sprite.getSprite().getHeight();
        for(int i = 0; i < height;i++){
            int yy = i + y;
            for(int j = 0; j < width;j++){
                int currentPosition = j+i*width;
                int xx = j+x;
                if(xx >= this.width || xx < 0 || yy >= this.height || yy < 0)
                    continue;
//                if((x+j)+(i+y)*this.width > ((this.width + i+y)*this.width))
//                    break;
                if(pix[currentPosition] == Font.ALPHA_COLOR.getRGB())
                    continue;
                this.pixels[xx+yy*this.width] = pix[currentPosition];
            }
        }
    }


    /**
     * Renders Sprite on the screen -> used for coloring string
     * @param sprite Sprite to be rendered on the screen
     * @param x X-coordinate where to start rendering
     * @param y Y- coordinate where to start rendering
     * @param c Color of the sprite to be rendered
     * */
    public void renderSprite(Sprite sprite, int x, int y,Color c){
        int[] pix = sprite.getPixels();
        int width = sprite.getWidth();
        int height = sprite.getHeight();
        for(int i = 0; i < height;i++){
            for(int j = 0; j < width;j++){
                int currentPosition = j+i*width;
                if(pix[currentPosition] == Font.ALPHA_COLOR.getRGB())
                    continue;
                if(pix[currentPosition] == Color.BLACK.getRGB())
                    this.pixels[(x + j) + (i + y) * this.width] = c.getRGB();
                else
                    this.pixels[(x+j)+(i+y)*this.width] = pix[currentPosition];
            }
        }
    }

    /**
     * Renders Sprite on the screen -> used for coloring string
     * @param pix Array of the pixels to be rendered on the screen
     * @param x X-coordinate where to start rendering
     * @param y Y- coordinate where to start rendering
     * @param width Width of the sprite to be rendered
     * @param height of the sprite to be rendered
     * @param c Color of the sprite
     * */
    public void renderSprite(int[] pix,int x, int y, int width,int height,Color c){
        for(int i = 0; i < height;i++){
            for(int j = 0; j < width;j++){
                int currentPosition = j+i*width;
                if(pix[currentPosition] == Font.ALPHA_COLOR.getRGB())
                    continue;
                if(pix[currentPosition] == Color.BLACK.getRGB())
                    this.pixels[(x + j) + (i + y) * this.width] = c.getRGB();
                else
                    this.pixels[(x+j)+(i+y)*this.width] = pix[currentPosition];
            }
        }
    }

    public void renderMap(Map tileMap) {
        int row = (this.y>>Game.TILE_BYTE_SIZE);
        int column = (this.x>>Game.TILE_BYTE_SIZE);
        this.renderHeight =( (this.y + this.height) >> Game.TILE_BYTE_SIZE)+1;
        this.renderWidth = ((this.x + this.width) >> Game.TILE_BYTE_SIZE)+1;
        for(int y = row; y < (renderHeight);y++){
            for(int x = column; x < (renderWidth);x++){
                Tile tile = tileMap.getTile(x,y);
                renderTile(tile,tile.getX(),tile.getY());
            }
        }
    }

    public void updateMap(Map tileMap) {
        int row = (this.y>>Game.TILE_BYTE_SIZE);
        int column = (this.x>>Game.TILE_BYTE_SIZE);
        this.renderHeight =( (this.y + this.height) >> Game.TILE_BYTE_SIZE)+1;
        this.renderWidth = ((this.x + this.width) >> Game.TILE_BYTE_SIZE)+1;
        for(int y = row; y < (renderHeight);y++){
            for(int x = column; x < (renderWidth);x++){
                tileMap.getTile(x,y).update();
            }
        }
    }

    /** Drawing line on screen */
    private int sign(int x){
        if(x > 0){
            return 1;
        }else if(x < 0){
            return -1;
        }else{
            return 0;
        }

    }

    public void drawLine(int x0,int y0, int x1,int y1, Color c){
        int x,y,dx,dy,swap,temp,s1,s2,p,i;

        x=x1;
        y=y1;
        dx=Math.abs(x1-x0);
        dy=Math.abs(y1-y0);
        s1=sign(x1-x0);
        s2=sign(y1-y0);
        swap=0;
//        pixels[x0 + y0*this.width] = c.getRGB();
        if(dy>dx)
        {
            temp=dx;
            dx=dy;
            dy=temp;
            swap=1;
        }
        p=2*dy-dx;
        for(i=0;i<dx;i++)
        {
            pixels[x + y*this.width] = c.getRGB();
            while(p>=0)
            {
                p=p-2*dx;
                if(swap != 0)
                    x+=s1;
                else
                    y+=s2;
            }
            p=p+2*dy;
            if(swap != 0)
                y+=s2;
            else
                x+=s1;
        }
        pixels[x1 + y1*this.width] = c.getRGB();
    }

    /**
     * getter for pixel array
     * */
    public int[] getPixels(){
        return this.pixels;
    }

    public void setPixels(int[] pix){
        for(int i = 0; i < pix.length;i++){
            pixels[i] = pix[i];
        }
    }

    public void setOffsets(int x, int y){
        if(this.x + x > 0) this.x += x;
        if(this.y + y > 0) this.y += y;
        if(this.x + x + this.width <= bounds.getWidth()) this.x += x;
        if(this.y + y  + this.height <= bounds.getHeight()) this.y += y;
    }

    public void setOffsetX(int x){
        this.x += x;
    }

    public void setOffsetY(int y){
        this.y += y;
    }
    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Centers screen, player is in the middle of the screen, if it is possible
     * */
    public void center(int x, int y){
        this.x = x - (this.width>>1);
        this.y = y - (this.height>>1);
        if(this.x < 0) this.x = 0;
        if(this.y < 0) this.y = 0;
        if(this.x + width > bounds.getWidth()) this.x = (int)bounds.getWidth() - width;
        if(this.y + height > bounds.getHeight()) this.y = (int)bounds.getHeight() - height;
    }

    public int getWidth(){ return this.width;}
    public int getHeight(){return this.height;}
    public int getRenderWidth(){return this.renderWidth;}
    public int getRenderHeight(){return this.renderHeight;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getScreenMiddleX(){
        return this.middleX;
    }
    public int getScreenMiddleY(){
        return this.middleY;
    }

    public void setBounds(int maxWidth,int maxHeight){
        bounds = new Dimension(maxWidth,maxHeight);
    }

    public Dimension getBounds(){
        return this.bounds;
    }
}
