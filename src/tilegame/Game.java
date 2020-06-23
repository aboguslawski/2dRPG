package tilegame;

import tilegame.display.Display;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;
import tilegame.states.GameState;
import tilegame.states.MenuState;
import tilegame.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

// klasa calej gry jej wszystkich stanow i kazdego elementu z nia zwiazanego
public class Game implements Runnable {

    // <<<<<<<<<<<<<<<<<<< ZMIENNE <<<<<<<<<<<<<<<<<<<
    // kolory
    Color backgroundColor = new Color(170, 70, 170);

    // input
    private MouseManager mouseManager;
    private KeyManager keyManager;

    // camera
    private GameCamera gameCamera;

    // handler
    private Handler handler;

    // okno
    private Display display;
    public String title;

    // thread
    private boolean running = false;
    private Thread thread;

    // grafiki
    private BufferStrategy bs;
    private Graphics g;

    // states
    public State gameState, menuState;

    // pozostale
    private int i;

    // <<<<<<<<<<<<<<< KONSTRUKTOR <<<<<<<<<<<<<<<<<<<<<<<


    public Game(String title) {
        this.title = title;

        //input
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        this.i = 0;
    }

    // <<<<<<<<<<<<<<<<<<<<<< FUNKCJE <<<<<<<<<<<<<<<<<<<

    // inicjalizacja okna
    private void init() {
        display = new Display(title);
        display.getFrame().addKeyListener(keyManager); //input przechwytywany przez glowne okno
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Assets.init();

        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0); // wszystko jest na swojej pozycji

        gameState = new GameState(handler);
        menuState = new MenuState(handler);

        State.setState(menuState);
    }


    // update
    private void tick() {
        keyManager.tick();


        if (State.getState() != null) {
            State.getState().tick();
        }
    }

    // wyswietlanie grafik na ekran
    private void render() {
        //>>> BUFFERY >>>
        bs = display.getCanvas().getBufferStrategy(); // rysowanie w oknie
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        //>>> GRAFIKA >>>
        g = bs.getDrawGraphics();

        //>>> CLEAR >>>
        g.clearRect(0, 0, Display.SCREEN_WIDTH, Display.SCREEN_HEIGHT);

        //>>> OBRAZY >>>
        // tlo
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Display.SCREEN_WIDTH, Display.SCREEN_HEIGHT);

        //

        if (State.getState() != null) {
            State.getState().render(g);
        }

        //>>> RENDER >>>
        bs.show();
        g.dispose();

    }

    // gameloop
    public void run() {
        init();

        int fps = 60, ticks = 0;
        long now, timer = 0, lastTime = System.nanoTime(), second = 1000000000;
        double delta = 0, timePerTick = second / fps;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }
            if(timer >= second){
                System.out.println("ticks and frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        // jesli running == false
        stop();

    }


    // <<<<<<<<<<<<<<<<< THREAD <<<<<<<<<<<<<<<<<<<<<<<<

    public synchronized void start() {
        if (running) return; //jesli juz gra dziala - wyjdz

        running = true; //zacznij
        thread = new Thread(this);
        thread.start(); // wywoluje run();
    }

    public synchronized void stop() {
        if (!running) return; //jesli gra jest juz zatrzymana - wyjdz

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    // GETTERS SETTERS

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager(){
        return mouseManager;
    }

}
