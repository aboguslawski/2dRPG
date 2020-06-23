package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.gfx.Assets;

import java.awt.*;

// statyczny obiekt domu
public class House1 extends StaticEntity {

    public House1(Handler handler, float x, float y) {
        super(handler, x, y, 667, 635);

        // prostokat kolizji
        bounds.y = 430;
        bounds.height = 140;
        bounds.x = 50;
        bounds.width = 550;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
//        drawBounds(g);

        g.drawImage(Assets.house1, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void interact() {

    }

    @Override
    public void die() {

    }
}
