package level;

import graphics.Animation;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;

/**
 * Created by bubof on 29.08.2017.
 */
public class AnimatedTile extends Tile{

    private Animation animation;
    private Screen screen;
    public AnimatedTile(Sprite image, int x, int y, int type){
        super(image,x,y,type);
    }

    public AnimatedTile(Tile tile,int x, int y){
        super(tile,x,y);
    }

    public void setAnimation(Animation animation){
        this.animation = animation;
    }

    public void update(){
        animation.update();
    }

    public void createAnimation(Screen screen, SpriteSheet spriteSheet){
        animation = new Animation(screen,spriteSheet);
    }

    public Sprite getSprite(){
        return this.animation.getCurrentSprite();
    }

    public Animation getAnimation(){return this.animation;}

}
