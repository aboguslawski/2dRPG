package tilegame.gfx;

import java.awt.image.BufferedImage;

public class Animation {

    // szybkosc zmiany klatek i aktualna klatka
    private int speed, index;

    // kontrola uplynietego czasu
    private long lastTime, timer;

    // tablica wszystkich klatek dla danej animacji
    private BufferedImage[] frames;

    // konstruktor
    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        lastTime = System.currentTimeMillis();
        timer = 0;
    }

    // tickuj w wyznaczonym czasie
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        // po uplycieciu ustalonego czasu nastepna klatka
        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length) {
                index = 0;
            }
        }
    }

    // GETTERS SETTERS

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
