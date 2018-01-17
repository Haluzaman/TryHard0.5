package main;

import gameState.GameStateManager;
import graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by bubof on 05.07.2017.
 *
 * Main objects
 * holds the frame and canvas of the game,
 * holds main thread
 * holds main drawing and updateing method
 */
public class Game extends JPanel implements Runnable,KeyListener,MouseListener,MouseMotionListener{

    public static final int WIDTH = 352;
    public static final int HEIGHT = 192;
    public static final int SCALE = 3;

    public static final int TILESIZE = 16;
    public static final int TILE_BYTE_SIZE = 4;


    private Thread thread;
    private final int FPS = 60;
    private boolean isRunning = false;

    private long targetTime = 1000/FPS;

    private GameStateManager gsm;
    //our "canvas"
    private BufferedImage image;
    private int[] pixels; //pixels from our "canvas"
    private Graphics2D g;

    //This object will draw To screen
    private Screen screen;

    public Game(){
        initFrame();
        initGraphics();
        initThread();
    }

    private void initFrame(){
        Dimension d = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setFocusable(true);
        this.requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void initGraphics(){
        screen = new Screen(WIDTH,HEIGHT);
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        //gaining access to pixels
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        gsm = new GameStateManager(screen);
    }

    private void initThread(){
        if(this.thread == null){
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    private void destroyThread(){
        try{
            isRunning = false;
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();

        }
    }

    private void update(double delta){
        gsm.update(delta);
    }

    private void draw(){
        screen.clear();
        gsm.draw();
        int pix[] = screen.getPixels();
        //drawing screen pixels actually on our screen
        for(int i = 0; i < pix.length;i++)
            this.pixels[i] = pix[i];
    }

    private void drawToScreen(){
        Graphics g2 = getGraphics();
        if(g2 == null)
            return;
        g2.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
        //releases memory
        g2.dispose();
    }

    @Override
    public void run() {
        long start;
        long wait;
        long elapsed;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0/60.0;
        double delta = 0;
        double dt = 0;
        int frames = 0;
        int updates = 0;
        while(isRunning){
            //when the loop starts
            start = System.nanoTime();
            delta +=(start - lastTime)/ns;
//            lastTime = start;
            while(delta >= 1) {
//                update(delta);
                updates++;
                delta--;
            }
            dt = (System.nanoTime() - lastTime);
            if(dt > 0.15f)
                dt = 0.15f;
            lastTime = start;
            update(dt);
            draw();
            drawToScreen();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("ups : " + updates + "FPS: " + frames);
                updates = frames =  0;
            }
            //how long the update, draw, drawToScreen lasted
//            elapsed = System.nanoTime() - start;
//            wait = targetTime - elapsed / 1000000;
//            if(wait < 0) wait = 5;
//            try{
//                thread.sleep(wait);
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
        }
    }

    public void keyPressed(KeyEvent e){
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e){
        gsm.keyReleased(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e){
        gsm.keyReleased(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("X moouse: " + ((e.getX()/SCALE)>>Game.TILE_BYTE_SIZE) + " Y mouse: " + ((e.getY()/3)>>Game.TILE_BYTE_SIZE));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse coordinates: " + ((e.getX()/SCALE)>>Game.TILE_BYTE_SIZE) + " " + ((e.getY()/SCALE)>>Game.TILE_BYTE_SIZE));
    }
}
