package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

// stojak z pochodnia
// interakcja :
//

public class TorchStand extends StaticEntity {

    private BufferedImage texture;

    // czy interakcja obiektu zostala ztriggerowana
    private boolean interacted;

    public TorchStand(Handler handler, float x, float y) {
        super(handler, x, y, 20, 120);
        this.interaction = true;
        this.texture = Assets.torchStand;
        this.interacted = false;

        // prostokat kolizji
        bounds.y = 100;
        bounds.height = 20;
    }

    @Override
    public void tick() {

        // jesli zostal aktywowany wyswietl grafike zapalonej pochodni
        if (this.interacted) texture = Assets.torchStand2;

            // w przeciwnym wypadku grafika zgaszonej
        else texture = Assets.torchStand;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void interact() {

        // jesli obiekt jest nieaktywny, aktywuj go
        // jesli obiekt jest aktywny, dezaktywuj go
        this.interacted = !this.interacted;
    }
}
