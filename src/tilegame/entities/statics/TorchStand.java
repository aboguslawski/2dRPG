package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.gfx.Assets;

import java.awt.*;

public class TorchStand extends StaticEntity {

    public TorchStand(Handler handler, float x, float y){
        super(handler, x, y, 20, 128);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.torchStand, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()) , width, height, null);
    }
}
