package tilegame.entities.mobs.hostile;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.items.Item;

import java.awt.*;
import java.util.Random;


// testowy przeciwnik

public class Enemy1 extends HostileMob {

    public Enemy1(Handler handler, float x, float y) {
        super(handler, x, y, 68, 126);

        bounds.x = 0;
        bounds.y = 90;
        bounds.width = 40;
        bounds.height = 40;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
//        super.render(g);
        g.drawImage(Assets.enemy1, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void interact() {

    }

    @Override
    public void die() {

        Random rn = new Random();

        if (rn.nextInt() % 2 == 0)
            handler.getWorld().getItemManager().addItem(Item.mushroomItem.createNew((int) x, (int) y));
        else
            handler.getWorld().getItemManager().addItem(Item.coinItem.createNew((int) x, (int) y));
    }
}
