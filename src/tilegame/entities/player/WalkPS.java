package tilegame.entities.player;

import tilegame.Handler;
import tilegame.entities.Creature;
import tilegame.gfx.MobAnimation;

import java.awt.*;

// stan swobodnego chodzenia

public class WalkPS extends PState {

    public WalkPS(Handler handler, Player player) {
        super(handler, player);

        // animacje
        walk = new MobAnimation("/res/textures/playerWalk.png",250, 4, 64, 128);
        idle = new MobAnimation("/res/textures/playerIdle.png",250, 4, 64, 128);

        // stan inicjowany z animacja idle
        animation = idle;

        // szybkosci poruszania sie w stanie
        this.walkSpeed = 3.0f;
        this.runSpeed = 4.4f;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    protected void getInput() {
        animation = idle;

        if (handler.getKeyManager().up
        || handler.getKeyManager().down
        || handler.getKeyManager().left
        || handler.getKeyManager().right) {
            animation = walk;
        }
    }
}
