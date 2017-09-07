package gameState;

import graphics.Screen;

/**
 * Created by bubof on 05.07.2017.
 * Abstract parent class for all GameStates
 */
public abstract class GameState{

    protected GameStateManager gsm;
    protected Screen screen;
    public GameState(GameStateManager gsm,Screen screen){
        this.gsm = gsm;
        this.screen = screen;
    }

    public abstract void draw();
    public abstract void update(double delta);

    public abstract void keyPressed(int key);
    public abstract void keyReleased(int key);
    public abstract void keyTyped(int key);
}
