package tilegame.gfx;

import java.awt.*;

public class DayNightCycle {

    // animacja kazdej fazy
    private Animation dnc;

    // godzina
    private int time;

    // konstruktor
    public DayNightCycle(Animation dnc) {
        this.dnc = dnc;
        this.dnc.setIndex(10); // zacznij od 10 tej
    }

    public void tick() {
        time = dnc.getIndex();
        dnc.tick();
    }

    public void render(Graphics g) {
        g.drawImage(dnc.getCurrentFrame(), 0, 0, null);
    }

    // GETTERS SETTERS

    public int getTime() {
        return time;
    }
}
