package level;

import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;
import main.Game;

import java.io.*;

/**
 * Created by bubof on 09.07.2017.
 */
public class Map {

    public  int LEVEL_WIDTH;
    public  int LEVEL_HEIGHT;
    public  int LEVEL_SIZE;

    private Tile[] tiles;
    private SpriteSheet spriteSheet;

    public static final String LEVEL_PATH = "/levels/spritesheet.txt";

    public Map(int levelWidth,int levelHeight){
        this.LEVEL_WIDTH = levelWidth;
        this.LEVEL_HEIGHT = levelHeight;
        this.LEVEL_SIZE = LEVEL_WIDTH*LEVEL_HEIGHT;
        loadTiles();
    }

    public Map(String path){
        loadLevel(path);
    }

    private void loadTiles(){
        tiles = new Tile[LEVEL_HEIGHT*LEVEL_WIDTH];
        for(int y = 0; y < LEVEL_HEIGHT;y++){
            for(int x = 0; x < LEVEL_WIDTH;x++){
                if((x&1) == 0){
                    tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.GRASS_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                }else{
                    tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.GRASS_TILE_2,x*Game.TILESIZE,y*Game.TILESIZE);
                }
            }
        }
        return;
    }

    private void loadLevel(String path){
        try{

            InputStreamReader stream = new InputStreamReader(getClass().getResourceAsStream(path));
            BufferedReader br = new BufferedReader(stream);
            String currentLine;
            currentLine = br.readLine();
            LEVEL_WIDTH = Integer.parseInt(currentLine);
            currentLine = br.readLine();
            LEVEL_HEIGHT = Integer.parseInt(currentLine);
            spriteSheet = SpriteSheet.TILE_MAP_SPRITESHEET2;
            LEVEL_SIZE = LEVEL_HEIGHT*LEVEL_WIDTH;
            tiles = new Tile[LEVEL_SIZE];

            for(int y = 0; y < LEVEL_HEIGHT;y++){
                currentLine = br.readLine();
                String[] chars = currentLine.split(",");
                for(int x = 0; x < LEVEL_WIDTH;x++){
                    if(chars[x].equals("0")){
                        tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.SWAMP_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                    }else if(chars[x].equals("1")){
                        tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.SWAMP_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                    }else if(chars[x].equals("2")){
                        tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.COAST_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                    }else if(chars[x].equals("3")){
                        tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.TREE_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                    }else if(chars[x].equals("4")){
                        tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.WATER_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                    }else if(chars[x].equals("5")){
                        tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.CUT_TREE_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                    }else if(chars[x].equals("6")){
                        tiles[x+y*LEVEL_WIDTH] = new Tile(Tile.GRASS_TILE,x*Game.TILESIZE,y*Game.TILESIZE);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= LEVEL_WIDTH || y >= LEVEL_HEIGHT) {
            return Tile.VOID_TILE;
        }
        int row = y;
        int column = x;
        for(int i = 0; i < tiles.length;i++){
            if(row < 0 || row >= LEVEL_HEIGHT)
                return Tile.VOID_TILE;
            int currentPosition = column+row*LEVEL_WIDTH;
            if(column < 0 || column >= LEVEL_WIDTH)
                return Tile.VOID_TILE;
            return tiles[currentPosition];
        }
        return Tile.VOID_TILE;
    }

    public Tile[] getTiles(){
        return this.tiles;
    }

    public int getLevelWidth(){
        return this.LEVEL_WIDTH;
    }

    public int getLevelHeight(){
        return this.LEVEL_HEIGHT;
    }
}
