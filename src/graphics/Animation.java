package graphics;

/**
 * Created by bubof on 27.08.2017.
 */
public class Animation {


    private SpriteSheet spriteSheet;
    private Sprite[][] sprites;
    private Screen screen;
    private int delay;
    private int currentFrame;
    private int currentRow;
    private long lastRenderTime;
    private boolean playedOnce = false;
    public Animation(Screen screen,SpriteSheet spriteSheet){
        this.spriteSheet = spriteSheet;
        this.screen = screen;
        sprites = spriteSheet.getSprites();
        lastRenderTime = System.currentTimeMillis();
    }

    public void update(){
        long currentRenderTime = System.currentTimeMillis() - lastRenderTime;
        if(currentRenderTime > delay){
            currentFrame++;
            lastRenderTime = System.currentTimeMillis();
        }
    }

    public void render(int x,int y){
        checkRenderSpriteBounds();
        screen.renderSprite(sprites[currentRow][currentFrame],x,y);
    }

    public void setDelay(int delay){
        this.delay = delay;
    }

    private void checkRenderSpriteBounds(){
        if(currentRow < 0)
            return;
        if(currentFrame >= sprites[currentRow].length) {
            playedOnce = true;
            currentFrame = 0;
        }
    }

    public void setCurrentAnimation(int row){
        playedOnce = false;
        this.currentRow = row;
    }

    public boolean hasPlayedOnce(){
        return this.playedOnce;
    }
    public Sprite getCurrentSprite(){return  this.sprites[currentRow][currentFrame];}
}
