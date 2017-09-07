package gameState;

import graphics.Screen;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by bubof on 05.07.2017.
 *
 * Handles Game states management
 */
public class GameStateManager{

    /**Game states**/
    public static final int MAIN_MENU_STATE = 0;
    public static final int LEVEL_STATE = 1;

    /**ArrayList of the gameStates**/
    private ArrayList<GameState> gameStates;
    private Screen screen;

    private int currentGameState;
    /**
     * @param screen screen for drawing
     * **/
    public GameStateManager(Screen screen){
        this.screen = screen;
        this.gameStates = new ArrayList<GameState>();
        this.currentGameState = MAIN_MENU_STATE;
        this.gameStates.add(new MainMenu(this,screen));
        this.gameStates.add(new LevelState(this,screen));
    }

    public void draw(){
        gameStates.get(currentGameState).draw();
    }

    public void update(double delta){
        gameStates.get(currentGameState).update(delta);
    }

    public void keyPressed(int e){
        if(e == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        gameStates.get(currentGameState).keyPressed(e);
    }

    public void keyReleased(int e){
        gameStates.get(currentGameState).keyReleased(e);
    }

    public void keyTyped(int e){
        gameStates.get(currentGameState).keyTyped(e);
    }

    public void setState(int state){
        currentGameState = state;
    }
}
