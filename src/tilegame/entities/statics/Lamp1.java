package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.gfx.Assets;

import java.awt.*;

// statyczny obiekt lampy
public class Lamp1 extends StaticEntity {

    public Lamp1(Handler handler, float x, float y) {
        super(handler, x, y, 111, 272);
        // prostokat kolizji
        bounds.x = 40;
        bounds.width = 40;
        bounds.y = 200;
        bounds.height = 40;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
//        drawBounds(g);
        // od 21 do 5 nocny model lampy
        if (handler.getWorld().getCycle().getTime() > 5 && handler.getWorld().getCycle().getTime() <= 21)
            g.drawImage(Assets.lamp1, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        else
            g.drawImage(Assets.lamp1Night, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void interact() {

    }

    @Override
    public void die() {

    }

}
