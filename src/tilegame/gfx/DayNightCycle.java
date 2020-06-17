package tilegame.gfx;

import java.awt.*;

public class DayNightCycle {

    private Animation dnc;

    public DayNightCycle(Animation dnc){
        this.dnc = dnc;
    }

    public void tick(){
        dnc.tick();
    }

    public void render(Graphics g){
        g.drawImage(dnc.getCurrentFrame(),0,0,null);
    }
}
