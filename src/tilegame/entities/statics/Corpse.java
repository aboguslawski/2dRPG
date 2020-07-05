package tilegame.entities.statics;

import tilegame.Handler;
import tilegame.items.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Corpse extends Chest {

    public Corpse(Handler handler, float x, float y, int width, int height, BufferedImage texture, ArrayList<Item> content) {
        super(handler, x, y, width, height, texture);

        this.content = content;

        // po zwlokach mozna domyslnie chodzic
        bounds.width = 0;
        bounds.height = 0;
    }

    // zachowuja sie dokladnie tak jak zwykla skrzynia
    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void die() {
    }
}
